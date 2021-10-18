package com.example.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class MemberLoginSuccessHandler implements AuthenticationSuccessHandler{

    //로그인 성공 시 자동으로 호출되는 메소드
    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request, 
        HttpServletResponse response, 
        Authentication authentication) throws IOException, ServletException {
              //1. 세션에서 사용자 정보 얻기
            User user = (User) authentication.getPrincipal();
            if (user != null) {
                Collection<GrantedAuthority> roles = user.getAuthorities();
                //반복 횟수 : 1회 회전
                for(GrantedAuthority role : roles) {    //로그인 성공 시 role 별 자동 이동 페이지 설정
                    if(role.getAuthority().equals("ADMIN")){
                        response.sendRedirect(request.getContextPath() + "/admin/home");//관리자 로그인 성공 시 이동할 페이지 설정할 것!
                    }
                    else if(role.getAuthority().equals("MEMBER")){
                        response.sendRedirect(request.getContextPath() + "/member/home");//회원 로그인 성공 시 이동할 페이지 설정할 것!
                    }
                }
            }       
    }
}
