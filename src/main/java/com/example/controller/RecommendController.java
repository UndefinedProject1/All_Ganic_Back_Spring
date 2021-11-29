package com.example.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.example.entity.ProductListProjection;
import com.example.entity.Recommend;
import com.example.jwt.JwtUtil;
import com.example.service.ProductService;
import com.example.service.RecommendService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(value = "/api")
public class RecommendController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    RecommendService rService;

    @Autowired
    ProductService pService;
    
    @GetMapping(value="/random")
    public Map<String, Object> getRandom(@RequestParam Long code) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> list = rService.checkRecommend(code);
        try{
            if(list != null){

            }else{
                Long ret = pService.randomProduct(code);
                ProductListProjection product = pService.selectProductOne(ret);
                map.put("list", product);
            }
        }catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }
    
    // 물품 구매 시 추천물품에 추가
	// 127.0.0.1:8080/REST/api/add/recommended
    @PutMapping(value="/add/recommended")
    public Map<String, Object> addRecommendProduct(@RequestHeader("token") String token, @RequestParam("no") Long no) { // 결제한 물품코드번호
		Map<String, Object> map = new HashMap<String, Object>();
		Map<Long, Long> re = new HashMap<Long, Long>();
        try{
			String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
			Long product = pService.latestOrder(useremail); // 이전에 결제한 물품번호
			String key = "";
			String count = "";
			String[] st1;
			String[] st2;
			if(product != null && product != no){
				Map<String, Object> list = rService.checkRecommend(product);
				Recommend recommend = rService.findRecommend(product);
				if(list != null){ // 추천물품에 list가 있다면
					st1 = recommend.getRecommendkey().split(",");
					st2 = recommend.getRecommendvalue().split(",");
					for(int i=0; i<st1.length; i++){
						re.put(Long.parseLong(st1[i]), Long.parseLong(st2[i]));
					}
					if(re.containsKey(no)){ // list안에 구매 물품번호가 있는지
						re.put(no, re.get(no) + 1); // 확인 후 +1
						for(Entry<Long, Long> elem : re.entrySet()){ 
							key = elem.getKey() + ",";
							count = elem.getValue() + ","; 
						}
						rService.updateKeyValue(key, count, product);
					}else{
						re.put(no, 1L);
						for(Entry<Long, Long> elem : re.entrySet()){ 
							key += elem.getKey() + ",";
							count += elem.getValue() + ","; 
						}
						rService.updateKeyValue(key, count, product);
					}
				}else{ // 추천물품에 list가 없다면 새로 추가
					key = String.valueOf(no) + ",";
					count = String.valueOf(1) + ",";
					rService.updateKeyValue(key, count, product);
				}
				map.put("result", 1);
				map.put("state", "추천물품 추가완료");
			}
			else{
				map.put("result", 1);
				map.put("state", "이력 없음");
			}
        }
        catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
	}
}
