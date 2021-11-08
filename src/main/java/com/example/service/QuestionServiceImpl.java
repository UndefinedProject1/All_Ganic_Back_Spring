package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.entity.Question;
import com.example.entity.QuestionProjection;
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
    
    // 문의글 찾기
    @Override
    public Question selectQuestion(Long no) {
        Optional<Question> question = qRepository.findById(no);
        return question.orElse(null);
    }

    // 문의글 삭제
    @Override
    public void deleteQuestion(Long no) {
        qRepository.deleteById(no);
    }

    // 문의글 수정
    @Override
    public void updateQuestion(Question question) {
        qRepository.save(question);
    }

    // 문의글 답글여부, kind종류에 따른 리스트 조회
    @Override
    public List<QuestionProjection> selectQuestionList(Boolean reply, Long kind) {
        return qRepository.queryQuestionList(reply, kind);
    }
    
    // 문의글 답글여부에 따른 리스트 조회
    @Override
    public List<QuestionProjection> selectQuestionList1(Boolean reply) {
        return qRepository.queryQuestionList1(reply);
    }

    // 문의글 회원별리스트 조회
    @Override
    public List<Question> selectMemberQuestionList(String email) {
        return qRepository.findByMember_UseremailOrderByQuestiondateDesc(email);
    }

    // 문의글 중복 체크
    @Override
    public int checkQuestionCode(Long questioncode) {
        // TODO Auto-generated method stub
        return 0;
    }


    
}