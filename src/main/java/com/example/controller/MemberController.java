package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.Member;
import com.example.jwt.JwtUtil;
import com.example.serviece.MemberServiece;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/api")
public class MemberController {

    @Autowired
    MemberServiece mServiece;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @RequestMapping(value="/member/join", method = {
        RequestMethod.POST}, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> memberJoinPOST(@RequestBody Member member) {
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            member.setUserpw(bcpe.encode(member.getUserpw()));
            mServiece.joinMember()
        }
    }

}
