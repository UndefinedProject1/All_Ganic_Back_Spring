package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.entity.Question;
import com.example.repository.QuestionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    QuestionRepository qRepository;

    // 문의글 추가
    @Override
    public void insertQuestion(Question question) {
        qRepository.save(question);
    }

    @Override
    public Question selectQuestion(Long no) {
        Optional<Question> question = qRepository.findById(no);
        return question.orElse(null);
    }

    @Override
    public void deleteQuestion(Long no) {
        qRepository.deleteById(no);
    }

    @Override
    public void updateQuestion(Question question) {
        qRepository.save(question);
    }

    @Override
    public List<Question> selectQuestionList() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int checkQuestionCode(Long questioncode) {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
