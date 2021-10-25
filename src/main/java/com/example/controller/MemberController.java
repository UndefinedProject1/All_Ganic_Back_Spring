package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.Member;
import com.example.jwt.JwtUtil;
import com.example.service.MemberServiece;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    // 127.0.0.1:8080/REST/api/member/join
    // {"useremail":"a@gmail.com", "userpw":"a","username":"a1","userrole":"MEMBER",
    // "post": 4112, "address":"부산진구", "usertel":"010-111-2222"}
    @PostMapping(value = "/member/join", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> memberJoinPOST(@RequestBody Member member) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            member.setUserpw(bcpe.encode(member.getUserpw()));
            member.setPost(member.getPost());
            mServiece.joinMember(member);
            map.put("result", 1);
        } catch (Exception e) {
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 로그인
    // 127.0.0.1:8080/REST/api/member/login
    // {"useremail":"a@gmail.com", "userpw":"a"}
    @RequestMapping(value = "/member/login", method = {
            RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> memberLoginPOST(@RequestBody Member member) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(member.getUseremail(), member.getUserpw()));
            map.put("result", 1L);
            map.put("token", jwtUtil.generateToken(member.getUseremail()));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    @RequestMapping(value = "/member/checkemail", method = {
        RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> CheckEmailPOST(@RequestBody Member member) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Long count = mServiece.checkMemberEmail(member.getUseremail());
            if(count > 0){
                map.put("result", 0L);
            }
            map.put("result", 1L);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 회원정보 수정(이름, 전화번호, 우편번호, 주소, 상세주소)
    // 127.0.0.1:8080/REST/api/member/update
    @RequestMapping(value = "/member/update", method = {
            RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> memberUpdate(@RequestBody Map<String, Object> mapobj,
            @RequestHeader("token") String token) {

        Map<String, Object> map = new HashMap<>();
        try {
            // @RequestBody Map<>으로 데이터 받는부분
            String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
            String username = (String) mapobj.get("username"); // 이름
            String usertel = (String) mapobj.get("usertel"); // 전화번호
            Long post = Long.parseLong(String.valueOf(mapobj.get("post"))); // 우편번호
            String address = (String) mapobj.get("address"); // 주소

            // 토큰과 사용자 아이디 일치 시점
            if (jwtUtil.extractUsername(token.substring(7)).equals(useremail)) {
                // 아이디를 이용해 기존 정보 가져오기
                Member member = mServiece.getMemberOne(useremail);
                member.setUsername(username);
                member.setUsertel(usertel);
                member.setPost(post);
                member.setAddress(address);
                mServiece.updateMember(member);
                map.put("result", 1L);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 비밀번호 변경
    // 127.0.0.1:8080/REST/api/member/passwd
    @RequestMapping(value = "/member/passwd", method = {
            RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> memberPasswd(@RequestBody Map<String, Object> mapobj,
            @RequestHeader("token") String token) {

        Map<String, Object> map = new HashMap<>();
        try {
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            // @RequestBody Map<>으로 데이터 받는부분
            String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
            String userpw = (String) mapobj.get("userpw"); // 원래 비밀번호
            String usernewpw = (String) mapobj.get("usernewpw"); // 새비밀번호

            // 토큰과 사용자 아이디 일치 시점
            if (jwtUtil.extractUsername(token.substring(7)).equals(useremail)) {
                // 아이디를 이용해 기존 정보 가져오기
                Member member = mServiece.getMemberOne(useremail);
                // 기존암호와 전달된 암호가 같으면 새로운 암호로 변경
                if (bcpe.matches(userpw, member.getUserpw())) {
                    member.setUserpw(bcpe.encode(usernewpw));
                    // 아이디, 암호를 새로운 기본값으로 대체
                    mServiece.updatePassword(member);
                    map.put("result", 1L);
                }
                // 기존암호와 전달된 암호가 같지않을 시
                map.put("result", 0L);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 회원탈퇴
    // 127.0.0.1:8080/REST/api/member/leave
    @RequestMapping(value = "/member/leave", method = {
            RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> memberLeave(@RequestBody Map<String, Object> mapobj,
            @RequestHeader("token") String token) {

        Map<String, Object> map = new HashMap<>();
        try {
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            // @RequestBody Map<>으로 데이터 받는부분
            String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
            String userpw = (String) mapobj.get("userpw");

            // 토큰과 사용자 아이디 일치 시점
            if (jwtUtil.extractUsername(token.substring(7)).equals(useremail)) {
                // 아이디를 이용해 기존 정보 가져오기
                Member member = mServiece.getMemberOne(useremail);
                // 기존암호와 전달된 암호가 같으면 새로운 암호로 변경
                if (bcpe.matches(userpw, member.getUserpw())) {
                    mServiece.deleteMember(useremail);
                    map.put("result", 1L);
                }
                // 기존암호와 전달된 암호가 같지않을 시
                map.put("result", 0L);
            }
        } catch (Exception e) {
            map.put("result", e.hashCode());
        }
        return map;
    }

}
