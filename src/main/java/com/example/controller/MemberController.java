package com.example.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.Member;
import com.example.jwt.JwtUtil;
import com.example.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping(value = "/api")
public class MemberController {

    @Autowired
    MemberService mServiece;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping(value = "/validtoken")
    public int validTokenGET(@RequestHeader("token") String token) {
        int ret;
        try {
            String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
            if(jwtUtil.validateToken(token.substring(7), useremail)){
                ret = 1;
            }
            else{
                ret = 0;
            }
        } catch (Exception e) {
            ret = e.hashCode();
        }
        return ret;
    }

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

    // 이메일 중복 체크(dto)
    // {"useremail":"a@gmail.com"} 있으면 1리턴, 없으면 0리턴
    @RequestMapping(value = "/member/checkemail", method = {
        RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> CheckEmailPOST(@RequestBody Member member) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            int count = mServiece.checkEmailDTO(member.getUseremail());
            System.out.println(count);
            if (count == 0) {
                map.put("result", 0L);
            } else {
                map.put("result", 1L);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 토큰을 통한 권한 확인
    // token값이 있으면 이를 통해 권한 찾기
    // 127.0.0.1:8080/REST/api/member/role
    @RequestMapping(value = "/member/role", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> CheckRoleGET(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
            Member member = mServiece.getMemberOne(useremail);
            System.out.println("==============");
            System.out.println(member);
            if(member != null){
                String role = member.getUserrole();
                map.put("role", role);
                map.put("result", 1L);
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

    // 회원정보 찾기
    // 127.0.0.1:8080/REST/api/member/find
    @RequestMapping(value = "/member/find", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> memberFind(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String useremail = jwtUtil.extractUsername(token.substring(7));
            Map<String, Object> member = mServiece.selectMemberOne(useremail);
            map.put("member", member);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 회원정보 수정(이름, 전화번호, 우편번호, 주소, 상세주소)
    // 127.0.0.1:8080/REST/api/member/update
    @RequestMapping(value = "/member/update", method = {
            RequestMethod.PUT }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> memberUpdate(@RequestBody Member member,
            @RequestHeader("token") String token) {

        Map<String, Object> map = new HashMap<>();
        try {
            String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기

            // 토큰과 사용자 아이디 일치 시점
            if (jwtUtil.extractUsername(token.substring(7)).equals(useremail)) {
                // 아이디를 이용해 기존 정보 가져오기
                Member member1 = mServiece.getMemberOne(useremail);
                member1.setUsername(member.getUsername());
                member1.setUsertel(member.getUsertel());
                member1.setPost(member.getPost());
                member1.setAddress(member.getAddress());
                member1.setDetaileaddress(member.getDetaileaddress());
                mServiece.updateMember(member1);
                map.put("result", 1L);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 우편주소 수정
    // 127.0.0.1:8080/REST/api/member/address/update
    @PutMapping(value = "/member/address/update", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> addressUpdate(@RequestBody Map<String, Object> body,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
            String address = (String) body.get("address"); 
            Long post = (Long) body.get("post");
            String detaileaddress = (String) body.get("detailaddress");

            // 토큰과 사용자 아이디 일치 시점
            if (jwtUtil.extractUsername(token.substring(7)).equals(useremail)) {
                // 아이디를 이용해 기존 정보 가져오기
                Member member1 = mServiece.getMemberOne(useremail);
                member1.setPost(post);
                member1.setAddress(address);
                member1.setDetaileaddress(detaileaddress);
                mServiece.updateMember(member1);
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
            RequestMethod.PUT }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
                else{
                    map.put("result", 0L);
                }
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
            RequestMethod.DELETE }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public int memberLeave(@RequestBody Map<String, Object> mapobj, @RequestHeader("token") String token) {
        int i = 0;
        try {
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            // @RequestBody Map<>으로 데이터 받는부분
            String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
            String userpw = (String) mapobj.get("userpw");

            // 토큰과 사용자 아이디 일치 시점
            if (jwtUtil.extractUsername(token.substring(7)).equals(useremail)) {
                // 아이디를 이용해 기존 정보 가져오기
                Member member = mServiece.getMemberOne(useremail);
                // 기존암호와 전달된 암호가 같으면 삭제한다
                if (bcpe.matches(userpw, member.getUserpw())) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date now1 = new Date();

                    Calendar cal = Calendar.getInstance(); 
                    cal.setTime(now1);
                    cal.add(Calendar.YEAR, 1);
                    Date date = df.parse(df.format(cal.getTime()));
                    int result = mServiece.updateLeaveMember(date, useremail);
                    if(result == 1){
                        i = 1;
                    }
                    else{
                        i = 3;
                    }
                }
                // 기존암호와 전달된 암호가 같지않을 시
                else{
                    i = 4;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            i = e.hashCode();
        }
        return i;
    }

}
