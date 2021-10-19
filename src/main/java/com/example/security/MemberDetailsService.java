package com.example.security;

import java.util.Collection;
import java.util.Optional;

import com.example.entity.Member;
import com.example.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//security를 제공해주는
//UserDetailsService 인터페이스를 통해 구현되는 메소드
@Service
public class MemberDetailsService implements UserDetailsService{
    @Autowired
    private MemberRepository mRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        
         //이곳으로 아이디가 넘어오니
        //아이디를 이용해 정보를 가져와서 User 객체 타입으로 리턴해서 로그인 성공 실패 알림
        Optional<Member> obj = mRepository.findById(username); //저장소에 string인지 Long 인지 확인
        Member member = obj.get();

        //String의 권한을 Collection<GrantedAuthority>로 변환
        String[] userRoles = {member.getUserrole()};
        Collection<GrantedAuthority> roles = AuthorityUtils.createAuthorityList(userRoles);

        //아이디, 암호 권한
        User user = new User(member.getUseremail(), member.getUserpw(), roles);
        return user;
    }
}
