package com.example.service;

import java.util.List;

import com.example.entity.Question;

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
    public List<Question> selectQuestionList();

    //문의글 중복 체크
    public int checkQuestionCode(Long questioncode);

}
