package com.example.controller;

import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.math.BigDecimal;

import com.example.entity.Pay;
import com.example.entity.Product;
import com.example.jwt.JwtUtil;
import com.example.request.AuthData;
import com.example.response.AccessToken;
import com.example.response.IamportResponse;
import com.example.response.Payment;
import com.example.service.CartItemService;
import com.example.service.MemberServiece;
import com.example.service.OrderService;
import com.example.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class OrderController {
    private static final String API_URL = "https://api.iamport.kr";
	private String api_key = "6255883224837328";
	private String api_secret = "dc31f716c19acc3805bb973e1a66dbc8a98047636f628621dc4b6397133db4930c424e2c087e4af9";
	private CloseableHttpClient client = HttpClientBuilder.create().build(); // 원래 http였음
	private Gson gson = new Gson();

    static String imp_uid = null;
    static String merchant_uid = null;

    @Autowired
    CartItemService ciService;

	@Autowired
    OrderService oService;

	@Autowired
    MemberServiece mService;

    @Autowired
    ProductService pService;

	@Autowired
    JwtUtil jwtUtil;
    
    // 토큰 발급을 위한 API연동
    private IamportResponse<AccessToken> getAuth() throws Exception{
		AuthData authData = new AuthData(api_key, api_secret);
				
		String authJsonData = gson.toJson(authData);
		
		try {
			StringEntity data = new StringEntity(authJsonData);
			
			HttpPost postRequest = new HttpPost(API_URL+"/users/getToken");
			postRequest.setHeader("Accept", "application/json");
			postRequest.setHeader("Connection","keep-alive");
			postRequest.setHeader("Content-Type", "application/json");
			
			postRequest.setEntity(data);
			HttpResponse response = client.execute(postRequest);
			
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatusLine().getStatusCode());
			}
			
			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);
			
			Type listType = new TypeToken<IamportResponse<AccessToken>>(){}.getType();
			IamportResponse<AccessToken> auth = gson.fromJson(body, listType);
			
			return auth;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	} 

    // 결제할 때 토큰 발급
    public String getToken() throws Exception {
		
		IamportResponse<AccessToken> auth = this.getAuth();
		
		if( auth != null) {
			String token = auth.getResponse().getToken();
			return token;
		}
		
		return null;		
	}

    // 결제 정보를 찾기 위한 API연동 작업
    private String getRequest(String path, String token) throws URISyntaxException {
		
		try {

			HttpGet getRequest = new HttpGet(API_URL+path);
			getRequest.addHeader("Accept", "application/json");
			getRequest.addHeader("Authorization", token);

			HttpResponse response = client.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatusLine().getStatusCode());
			}
			
			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);

			return body;
			
		  } catch (ClientProtocolException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();
		  }
		
		return null;
	}

    // 토큰을 통한 결제정보 찾기
    public IamportResponse<Payment> paymentByImpUid(String impUid) throws Exception {
		
		String token = this.getToken();
		
		if(token != null) {
			String path = "/payments/"+impUid;
			String response = this.getRequest(path, token);
			
			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();
			IamportResponse<Payment> payment = gson.fromJson(response, listType);
			
			return payment;
		}		
		return null;		
	}	
	
    // 결제 127.0.0.1:8080/REST/api/payments/complete
    // { imp_uid : 161616133(결제 번호), merchant_uid : k1234565(주문번호), chks : {1,2,3} }
    // 결제함과 동시에 결제한 아이템 장바구니에서 삭제
    @RequestMapping(value = "/payments/complete", method = {
        RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> paymentPost(@RequestBody Map<String, Object> body, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            System.out.println(body);
            imp_uid = (String) body.get("imp_uid"); // 결제번호
            merchant_uid = (String) body.get("merchant_uid"); // 주문번호
			String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기

            List<?> product = (List<?>) body.get("product"); // 물품 코드
            List<Product> list = new ArrayList<>(); // 물품코드를 다시 넣어주기 위한 새로운 변수선언
            for(int i=0; i<product.size(); i++){
                Long s = (Long) product.get(i);
                list.add(pService.selectProduct(s));
                System.out.println(s);
            }

			List<?> chks = (List<?>) body.get("chks"); // 결제한 장바구니아이템 코드
            List<Long> item = new ArrayList<>(); // 장바구니아이템코드를 다시 넣어주기 위한 새로운 변수선언
			String st = new String(); // 수량 저장
            for(int i=0; i<chks.size(); i++){
                Long s = (Long) chks.get(i);
                item.add(s);
				Long q = ciService.selectCartQuantity(s); // 코드를 통해 수량을 출력해 String으로 변환해서 입력
				st += String.valueOf(q);
				st += ",";
                System.out.println(s);
            }

            System.out.println("imp :" + imp_uid + "merchant :" + merchant_uid);
            if(imp_uid != null && merchant_uid != null && useremail != null){
                IamportResponse<Payment> pay = paymentByImpUid(imp_uid); // 토큰을 통해 아임포트 서버에 접속하여 결제 정보 추출
				Payment pay1 = pay.getResponse();
				Long payamount = pay1.getAmount();
				if(payamount == 1000){ // 고정된 금액이 아니라면 db에 접속해 진짜 금액과 비교해야함
					Pay order = new Pay();
					order.setImp_uid(imp_uid);
					order.setMerchant_uid(merchant_uid);
					order.setMember(mService.getMemberOne(useremail)); // 회원
					order.setProducts(list); // 물품
					order.setOrderquantity(st); // 수량
					oService.insertPayment(order); 

					ciService.deleteCartItemSome(item); // 장바구니아이템 삭제
					map.put("state", "결제가 완료되었습니다.");
					map.put("result", 1L);
				}
				else{
					map.put("state", "위조된 결제금액입니다.");
					map.put("result", 4L);
				}
            }
            else{
                map.put("state", "결제번호 및 주문번호가 제대로 넘어오지않았습니다.");
                map.put("result", 0L);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

	// // 주문내역 확인하기(아임포트 서버에 접속하여 물품정보등 출력)
    // // 127.0.0.1:8080/REST/api/payments/list
    // @RequestMapping(value="/payments/list", method=RequestMethod.GET)
    // public Map<String, Object> productReviewListGET(@RequestHeader("token") String token) {
    //     Map<String, Object> map = new HashMap<String, Object>();
    //     try{
	// 		IamportResponse<Payment> pay = paymentByImpUid(imp_uid);

    //         map.put("list", list);
    //         map.put("result", 1L);
    //     }
    //     catch (Exception e) {
    //         e.printStackTrace();
    //         map.put("result", e.hashCode());
    //     }
    //     return map;
    // }
}
