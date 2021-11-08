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

    // 문의글 답글여부, kind에 따라 리스트 조회
    @Query(value = "SELECT QUESTIONCODE, QUESTIONTITLE, QUESTIONCONTENT, QUESTIONDATE, PRODUCTCODE FROM QUESTIONLIST  WHERE QUESTIONREPLY =:code AND QUESTIONKIND =:kind ORDER BY QUESTIONDATE ASC", nativeQuery = true)
    public List<QuestionProjection> queryQuestionList(@Param("code") Boolean code, @Param("kind")Long kind);

    // //문의글 전체 조회(답글 여부, kind종류)
    // List<Question> findAllByOrderByQuestiondateDesc();
    
    // 회원별 문의글 출력
    List<Question> findByMember_UseremailOrderByQuestiondateDesc(String useremail);
}