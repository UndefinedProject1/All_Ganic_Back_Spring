package com.example.repository;

import java.util.List;

import com.example.entity.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{
    
    // 회원별 문의글 출력
    List<Question> findByMember_UseremailOrderByQuestiondateDesc(String useremail);
}