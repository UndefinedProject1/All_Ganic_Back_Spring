package com.example.repository;

import java.util.List;

import com.example.entity.Question;
import com.example.entity.QuestionProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{

    // 물품별 문의글 출력
    List<Question> findByProduct_ProductcodeOrderByQuestiondateDesc(Long Productcode);
    
    // 회원별 문의글 출력
    List<Question> findByMember_UseremailOrderByQuestiondateDesc(String useremail);
}