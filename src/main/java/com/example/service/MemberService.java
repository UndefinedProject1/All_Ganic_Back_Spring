package com.example.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.entity.Member;

// 여기선 정의만 할 뿐 실질적인 기능구현은 Impl
public interface MemberService {
    // 회원가입
    public void joinMember(Member member);

    // 로그인
    public Member getMemberOne(String email);

    // 아이디 중복체크(dto)
    public int checkEmailDTO(String email);

    // 회원정보 들고오기
    public Map<String, Object> selectMemberOne(String email);

    // 회원정보 수정하기
    public void updateMember(Member member);

    // 비밀번호 변경
    public void updatePassword(Member member);

    // 회원탈퇴 시 탈퇴여부와 날짜 변경
    public int updateLeaveMember(Date date, String email);

    // 그 날 탈퇴시킬 회원리스트
    public List<String> deleteMemberList(Date date);
}
