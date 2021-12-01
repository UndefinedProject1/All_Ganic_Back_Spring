package com.example.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.dto.MailDto;
import com.example.entity.Member;
import com.example.entity.Report;
import com.example.jwt.JwtUtil;
import com.example.service.MemberService;
import com.example.service.ReportService;
import com.example.service.sendEmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class MemberController {

    @Autowired
    MemberService mServiece;

    @Autowired
    sendEmailService sendEmailService;

    @Autowired
    ReportService rServiece;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    // 토큰 유효성 검사
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

    // 신고하기(리뷰, 문의)
    // 127.0.0.1:8080/REST/api/member/report
    @PostMapping(value = "member/report", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> reportPOST(@RequestHeader("token") String token, @RequestBody Map<String, Object> mapobj){
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            String useremail = (String) mapobj.get("useremail");
            Report report = rServiece.findReport(useremail);
            String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if(report != null){
                rServiece.updateDate(report.getReprotdate()+formatDate+",", report.getReportcode());
            }
            else{
                Report report1 = new Report();
                Member member = mServiece.getMemberOne(useremail);
                report1.setMember(member);
                report1.setReprotdate(formatDate+",");
                rServiece.insertReport(report1);
            }
            map.put("result", 1);
            map.put("state", "신고접수가 완료되었습니다.");
        }
        catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
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
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 로그인
    // 127.0.0.1:8080/REST/api/member/login?sns=true
    // {"useremail":"a@gmail.com", "userpw":"a"}
    @PostMapping(value = "/member/login", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> memberLoginPOST(@RequestBody Member member, @RequestParam(name = "sns") Boolean sns) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            Member member1 = mServiece.getMemberOne(member.getUseremail());
            if(sns == true){ // 카카오로 로그인 시
                if (bcpe.matches("kakao_login_pw", member1.getUserpw())) {
                    map.put("result", 1L);
                    map.put("token", jwtUtil.generateToken(member.getUseremail()));
                }
                else{
                    map.put("result", 0L);
                    map.put("state", "카카오 유저가 아닙니다. 사이트 로그인에서 로그인을 시도하여 주십시오.");
                }
            }else{ // 그냥 로그인 시
                if(bcpe.matches("kakao_login_pw", member1.getUserpw())) {
                    map.put("result", 0L);
                    map.put("state", "카카오 유저입니다. 카카오 로그인에서 로그인을 시도하여 주십시오.");
                }
                else{
                    authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(member.getUseremail(), member.getUserpw()));
                    map.put("result", 1L);
                    map.put("token", jwtUtil.generateToken(member.getUseremail()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 비밀번호 변경 시 카카오유저인지 확인
    // 127.0.0.1:8080/REST/api/kakao/user/check
    @GetMapping(value = "/kakao/user/check")
    public int kakaoUserCheckGET(@RequestHeader("token") String token) {
        int result;
        try {
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
            // 아이디를 이용해 기존 정보 가져오기
            Member member = mServiece.getMemberOne(useremail);
            // 토큰과 사용자 아이디 일치 시점
            if (bcpe.matches("kakao_login_pw", member.getUserpw())) {
                result = 1;   
            }
            else{
                result = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = e.hashCode();
        }
        return result;
    }

    // 이메일 중복 체크(dto)
    // {"useremail":"a@gmail.com"} 있으면 1리턴, 없으면 0리턴
    @PostMapping(value = "/member/checkemail", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> CheckEmailPOST(@RequestBody Map<String, Object> body) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String mail = (String) body.get("useremail");
            int count = mServiece.checkEmailDTO(mail);
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

    //등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
    // 127.0.0.1:8080/check/findPw/sendEmail
    @PostMapping(value = "/check/findPw/sendEmail", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public int sendEmail(@RequestBody Map<String, Object> body){
        int result = 0;
        try{
            String userEmail = (String) body.get("useremail");
            String userName = (String) body.get("username");
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            // 아이디를 이용해 기존 정보 가져오기
            Member member = mServiece.getMemberOne(userEmail);
            // 토큰과 사용자 아이디 일치 시점
            if (bcpe.matches("kakao_login_pw", member.getUserpw())) {
                result = 0;  // 카카오유저이므로 비밀번호 찾기 불가
            }
            else{
                MailDto dto = sendEmailService.createMailAndChangePassword(userEmail, userName);
                sendEmailService.mailSend(dto);
                result = 1;
            }
        }catch (Exception e) {
            e.printStackTrace();
            result = e.hashCode();
        }
        return result;
    }

    // 토큰을 통한 권한 확인
    // token값이 있으면 이를 통해 권한 찾기
    // 127.0.0.1:8080/REST/api/member/role
    @GetMapping(value = "/member/role", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    @GetMapping(value = "/member/find", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    @PutMapping(value = "/member/update", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
            // Long post = (Long) body.get("post");
            String pst = (String) body.get("post");
            Long post = Long.valueOf(pst); 
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
    @PutMapping(value = "/member/passwd", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    @DeleteMapping(value = "/member/leave", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
