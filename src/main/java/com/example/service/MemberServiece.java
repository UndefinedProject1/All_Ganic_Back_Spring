package com.example.service;

import com.example.entity.Member;

// 여기선 정의만 할 뿐 실질적인 기능구현은 Impl
public interface MemberServiece {
    // 회원가입
    public void joinMember(Member member);

    // 로그인
    public Member getMemberOne(String email);

    // 아이디 중복체크
    public int checkMemberEmail(String email);

    // 회원정보 수정하기
    public void updateMember(Member member);

    // 비밀번호 변경
    public void updatePassword(Member member);

    // 회원탈퇴
    public void deleteMember(String email);
}
