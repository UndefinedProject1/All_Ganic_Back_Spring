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
            map.put("result", 1L);
        } catch (Exception e) {
            map.put("result", e.hashCode());
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
            map.put("result", 1L);
            map.put("token", jwtUtil);
        } catch (Exception e) {
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 비밀번호 변경
    // 127.0.0.1.8080/ROOT/api/member/passwd
    @RequestMapping(value = "/member/passwd", method = {
            RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> memberPasswd(@RequestBody Map<String, Object> mapobj,
            @RequestHeader("token") String token) {

        Map<String, Object> map = new HashMap<>();
        try {
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            // @RequestBody Map<>으로 데이터 받는부분
            String useremail = (String) mapobj.get("useremail");
            String userpw = (String) mapobj.get("userpw"); // 원래 비밀번호
            String usernewpw = (String) mapobj.get("usernewpw"); // 새비밀번호

            // 토큰가ㅗ 사용자 아이디 일치 시점
            if (jwtUtil.extractUsername(token.substring(7)).equals(useremail)) {
                // 아이디를 이용해 기존 정보 가져오기
                Member member = mServiece.getMemberOne(useremail);
                // 기존암호와 전달된 암호가 같으면 새로운 암호로 변경
                if (bcpe.matches(userpw, member.getUserpw())) {
                    Member member1 = new Member();
                    member1.setUseremail(useremail);
                    member1.setUserpw(bcpe.encode(usernewpw));

                    // 아이디, 암호를 새로운 기본값으로 대체
                    mServiece.updatePassword(member1);
                    map.put("result", 1L);
                }
            }
        } catch (Exception e) {
            map.put("result", e.hashCode());
        }
        return map;
    }

}
