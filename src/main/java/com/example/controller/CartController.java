package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.Cart;
import com.example.entity.CartItem;
import com.example.jwt.JwtUtil;
import com.example.service.CartItemService;
import com.example.service.CartService;
import com.example.service.MemberServiece;
import com.example.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class CartController {
    @Autowired
    CartService cService;

    @Autowired
    CartItemService ciService;

    @Autowired
    ProductService pService;

    @Autowired
    MemberServiece mService;

    @Autowired
    JwtUtil jwtUtil;
    
    // 장바구니 생성 및 물품추가
    // 127.0.0.1:8080/REST/api/cart/create/insert?no=14
    // 여기서 넘어오는 no는 물품 정보
    @RequestMapping(value = "cart/create/insert", method = {
        RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> productInsertPOST(@RequestParam(name = "cnt") long cnt,
            @RequestParam(name = "no", defaultValue = "0") long no, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
            if (jwtUtil.extractUsername(token.substring(7)).equals(useremail)) {
                Cart cart1 = cService.findCart(useremail);
                if(cart1 != null){ // 장바구니가 생성되어 있으면
                    int check = ciService.checkProduct(no, cart1.getCartcode());
                    System.out.println(check);
                    if(check != 0){ // 이미 같은 항목의 물품이 장바구니에 있으니 찾아서 수량을 더해주기
                        CartItem cartitem1 = ciService.selectCartProductOne(no, cart1.getCartcode());
                        ciService.updateQuantity(cnt + cartitem1.getQuantity(), cartitem1.getCartitemcode());
                        map.put("state", "장바구니 물품 수량이 변경되었습니다");
                        map.put("result", 1L);
                    }else{ // 넣으려는 물품이 장바구니에 없으니 insert
                        CartItem cartitem = new CartItem();
                        cartitem.setCart(cart1);
                        cartitem.setProduct(pService.selectProduct(no));
                        cartitem.setQuantity(cnt);
                        ciService.insertCartItem(cartitem);
                        map.put("state", "장바구니 물품이 추가되었습니다");
                        map.put("result", 1L);
                    }
                }
                else{ // 장바구니가 생성되어 있지 않으면
                    Cart cart = new Cart();
                    cart.setMember(mService.getMemberOne(useremail));
                    cService.insertCart(cart);

                    CartItem cartitem = new CartItem();
                    cartitem.setCart(cart);
                    cartitem.setProduct(pService.selectProduct(no));
                    cartitem.setQuantity(no);
                    ciService.insertCartItem(cartitem);
                    map.put("state", "장바구니 생성 및 물품이 추가되었습니다");
                    map.put("result", 1L);
                }
            }
            else{
                map.put("state", "회원정보 불러오는걸 실패했습니다.");
                map.put("result", 0L);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 장바구니 물품수량 수정
    // 127.0.0.1:8080/REST/api/cartitem/quantity/update?cnt=4&no=14
    // no는 장바구니아이템 코드
    @RequestMapping(value = "/cartitem/quantity/update", method = {
        RequestMethod.PUT }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> questionUpdate(@RequestParam(name = "cnt", defaultValue = "0") long cnt, 
    @RequestParam(name = "no", defaultValue = "0") long no) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (cnt !=0 && no!=0) {
                ciService.updateQuantity(cnt, no);
                map.put("state", "물품 수량 변경 완료");
                map.put("result", 1L);
            }
            else{
                map.put("state", "물품코드 및 수량이 없습니다.");
                map.put("result", 0L);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 회원별 장바구니 아이템 리스트 출력(물품이름 순 정렬)
    // 127.0.0.1:8080/REST/api/cartitem/member/list
    @RequestMapping(value = "/cartitem/member/list", method = RequestMethod.GET)
    public Map<String, Object> MemberSelectListGET(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            System.out.println(token);
            String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
            if (jwtUtil.extractUsername(token.substring(7)).equals(useremail)) {
                Cart cart1 = cService.findCart(useremail);
                if(cart1 != null){ // 장바구니가 있다면
                    List<Map<String, Object>> list = ciService.selectMemberCartList(useremail);
                    map.put("list", list);
                    map.put("cart", cart1.getCartcode()); // 장바구니 코드
                    map.put("result", 1L);
                }
                else{
                    map.put("state", "관심있는 물품을 장바구니에 넣어보세요");
                    map.put("result", 1L);
                }
            }
            else{
                map.put("result", 0L);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 회원별 장바구니 아이템 전체삭제
    // 127.0.0.1:8080/REST/api/cartitem/delete/all
    // code는 장바구니코드
    @RequestMapping(value = "/cartitem/delete/all", method = RequestMethod.DELETE)
    public Map<String, Object> cartItemAllDELTE(@RequestParam(name = "code") long code) {
        Map<String, Object> map = new HashMap<String, Object>();
        int i = ciService.deleteCartItemAll(code);
        try{
            if(i == 1){
                map.put("result", 1L);
            }
            else{
                map.put("result", 0L);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }


    // 회원별 장바구니 아이템 일부분 삭제
    // 127.0.0.1:8080/REST/api/cartitem/delete/check
    // chks는 장바구니아이템코드
    @RequestMapping(value = "/cartitem/delete/check", method = RequestMethod.DELETE)
    public Map<String, Object> cartItemSomeDELTE(@RequestParam(name = "chks") List<Long> chks) {
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            for (Long chk : chks) {
                System.out.println(chk);
            }
            ciService.deleteCartItemSome(chks);
            map.put("result", 1L);
        }
        catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }
}
