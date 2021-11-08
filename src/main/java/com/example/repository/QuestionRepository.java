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
    @Query(value = "SELECT QUESTIONCODE, QUESTIONTITLE, QUESTIONCONTENT, QUESTIONDATE, PRODUCTCODE FROM QUESTIONLIST  WHERE QUESTIONREPLY =:reply AND QUESTIONKIND =:kind ORDER BY QUESTIONDATE DESC", nativeQuery = true)
    public List<QuestionProjection> queryQuestionList(@Param("reply") Boolean reply, @Param("kind")Long kind);
    
    // 문의글 답글여부 리스트 조회(kind값 없을 때)
    @Query(value = "SELECT QUESTIONCODE, QUESTIONTITLE, QUESTIONCONTENT, QUESTIONDATE, PRODUCTCODE FROM QUESTIONLIST  WHERE QUESTIONREPLY =:reply ORDER BY QUESTIONDATE DESC", nativeQuery = true)
    public List<QuestionProjection> queryQuestionList1(@Param("reply") Boolean reply);

    // 물품별 문의글 출력
    List<Question> findByProduct_ProductcodeOrderByQuestiondateDesc(Long Productcode);
    
    // 회원별 문의글 출력
    List<Question> findByMember_UseremailOrderByQuestiondateDesc(String useremail);
}