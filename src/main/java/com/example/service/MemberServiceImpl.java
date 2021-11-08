package com.example.service;

import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;

import com.example.entity.Member;
import com.example.mappers.MemberMapper;
import com.example.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberServiece {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    MemberMapper mMapper;

    @Autowired
    MemberRepository mRepository;

    // 회원가입
    public void joinMember(Member member) {
        mRepository.save(member);
    }

    // 로그인
    public Member getMemberOne(String email) {
        Optional<Member> member = mRepository.findById(email);
        return member.orElse(null); // 없으면 null리턴
    }
    
    // 아이디 중복체크 (dto)
    @Override
    public int checkEmailDTO(String email) {
        return mMapper.selectCount(email);
    }

    // 회원정보 들고오기
    @Override
    public Map<String, Object> selectMemberOne(String email) {
        return mMapper.selectMember(email);
    }

    // 회원정보 수정
    @Override
    public void updateMember(Member member) {
        mRepository.save(member);

    }

    // 비밀번호 변경
    public void updatePassword(Member member) {
        mRepository.save(member);
    }

    // 회원탈퇴
    @Override
    public void deleteMember(String email) {
        mRepository.deleteById(email);
    }

}
