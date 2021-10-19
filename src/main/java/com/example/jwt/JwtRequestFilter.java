package com.example.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.security.MemberDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//nodejs checkToken
//인증서가 유효하면
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MemberDetailService mService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // const headers = {"Content-Type":"application/json", "token":'123456_aaa'}
        String headerToken = request.getHeader("token");
        String token = null;
        String username = null;

        if (headerToken != null && headerToken.startsWith("123456_")) { // 토큰 시작 문구 확인!
            // 실제 토큰
            token = headerToken.substring(7);
            username = jwtUtil.extractUsername(token);
        }

        // username과 시큐리티 세션에 있는 사용자 아이디와 비교
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // 토큰은 전달 되었고 로그인이 있어야 되는 것만
        if (username != null && auth == null) {
            UserDetails userDetails = mService.loadUserByUsername(username);
            if (jwtUtil.validateToken(token, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(upat);
            }
        }
        // 컨트롤러로 넘어가는 시점
        filterChain.doFilter(request, response);
    }
}
