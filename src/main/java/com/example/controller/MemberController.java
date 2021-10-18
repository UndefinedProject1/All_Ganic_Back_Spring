package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.Member;
import com.example.jwt.JwtUtil;
import com.example.serviece.MemberServiece;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping(value = "/api")
public class MemberController {

    @Autowired
    MemberServiece mServiece;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    // 회원가입
    // 127.0.0.1:8080/ROOT/api/member/join
    // {"email":"", "passwd":"","name":"","role":"" }
    @RequestMapping(value = "/member/join", method = {
            RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> memberJoinPOST(@RequestBody Member member) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            member.setUserpw(bcpe.encode(member.getUserpw()));
            mServiece.joinMember(member);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 로그인
    // 127.0.0.1.8080/ROOT/api/member/login
    @RequestMapping(value = "/member/login", method = {
            RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> memberLoginPOST(@RequestBody Member member) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(member.getUseremail(), member.getUserpw()));
            map.put("status", 200);
            map.put("token", jwtUtil);
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

}
