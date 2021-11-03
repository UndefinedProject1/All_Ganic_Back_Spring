package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.Question;
import com.example.jwt.JwtUtil;
import com.example.service.MemberServiece;
import com.example.service.ProductService;
import com.example.service.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class QuestionController {

    @Autowired
    MemberServiece mService;

    @Autowired
    ProductService pService;

    @Autowired
    QuestionService qService;

    @Autowired
    JwtUtil jwtUtil;
    
    // 문의글 작성
    // 127.0.0.1:8080/REST/api/question/insert?no=14
    // 여기서 넘어오는 no는 물품 정보
    @RequestMapping(value = "/question/insert", method = {
        RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> productInsertPOST(@RequestBody Question question,
            @RequestParam(name = "no", defaultValue = "0") long no,@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
            if (jwtUtil.extractUsername(token.substring(7)).equals(useremail)) {

                question.setMember(mService.getMemberOne(useremail)); // 회원정보를 통해 member를 찾아줌
                question.setProduct(pService.selectProduct(no)); // 파라미터로 넘어온 물품코드를 통해 물품정보 찾기
                qService.insertQuestion(question);
                map.put("result", 1L);
            }
            map.put("result", 0L);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

}
