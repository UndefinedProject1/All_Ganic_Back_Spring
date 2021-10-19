package com.example.service;

import com.example.entity.Member;

// 여기선 정의만 할 뿐 실질적인 기능구현은 Impl
public interface MemberServiece {
    // 회원가입
    public void joinMember(Member member);

    // 회원정보 가져오기
    public Member getMemberOne(String email);

    // 비밀번호 변경
    public void updatePassword(Member member);

    // 회원탈퇴
    public void deleteMember(String email);
}
