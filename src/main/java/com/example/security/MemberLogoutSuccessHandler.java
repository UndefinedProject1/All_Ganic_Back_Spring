package com.example.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public class MemberLogoutSuccessHandler implements LogoutSuccessHandler{

    @Override
    public void onLogoutSuccess(
        HttpServletRequest request, 
        HttpServletResponse response, 
        Authentication authentication)
            throws IOException, ServletException {
                //어느 role 이든 로그아웃 성공 시 home으로 이동.
                response.sendRedirect(request.getContextPath()+"/");
    }
}
