package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityConfig extends WebSecurityConfigurerAdapter{
    //db 연동을 위해 만든 service
    @Autowired
    private MemberDetailService mService;

    //환경설정 파일에서 객체 만들기(회원가입 시 암호화 방법)
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(mService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //권한 설정(등급)
        http.authorizeRequests()
            //관리자 로그인
            .antMatchers("/admin","/admin/*").hasAuthority("ADMIN")
            //회원 로그인
            .antMatchers("/member","/member/*").hasAnyAuthority("ADMIN","MEMBER").anyRequest().permitAll().and();
        
        //로그인 페이지
        http.formLogin()
            //form action="/member/login">
            .loginPage("/member/login").loginProcessingUrl("/member/login")
            // <input type="text" name="userid">
            .usernameParameter("userid")
            // <input type="text" name="userid">
            .passwordParameter("userpw").permitAll().successHandler(new MemberLoginSuccessHandler()).and()
            //로그인 성공 시 home 으로 이동한다.
            
            //로그아웃
            .logout().logoutUrl("/member/logout").logoutSuccessHandler(new MemberLogoutSuccessHandler())//로그아웃 성공 시 이동 페이지
            .invalidateHttpSession(true).clearAuthentication(true).permitAll().and()

            //접근 불가 보여질 페이지 주소 설정
            .exceptionHandling().accessDeniedPage("/page403").and();
    }
}
