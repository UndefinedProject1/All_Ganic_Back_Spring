package com.example.repository;

import com.example.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    @Query(value = "SELECT COUNT(USEREMAIL) FROM MEMBER WHERE USEREMAIL= #{email}", nativeQuery = true)
    public Long queryCheckEmail(String email);
}
