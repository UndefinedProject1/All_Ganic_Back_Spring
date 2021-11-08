package com.example.service;

import java.util.List;

import com.example.entity.Question;
import com.example.entity.QuestionProjection;

import org.springframework.stereotype.Service;

@Service
public interface QuestionService {
    
    //문의글 추가
    public void insertQuestion(Question question);
    
    //문의글 찾기
    public Question selectQuestion(Long no);

    //문의글 삭제
    public void deleteQuestion(Long no);

    //문의글 수정
    public void updateQuestion(Question question);

    //문의글 전체 조회
    public List<QuestionProjection> selectQuestionList(Boolean code, Long kind);

    // 문의글 회원별리스트 조회
    public List<Question> selectMemberQuestionList(String email);

    //문의글 중복 체크
    public int checkQuestionCode(Long questioncode);

}