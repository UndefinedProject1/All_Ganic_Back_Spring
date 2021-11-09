package com.example.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.dto.QuestionDTO;
import com.example.entity.Question;
import com.example.mappers.QuestionMapper;
import com.example.repository.QuestionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    QuestionRepository qRepository;

    @Autowired
	QuestionMapper qMapper;

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

    // 문의글 답글여부, kind에 따른 리스트 조회(dto)
    @Override
    public List<Map<String, Object>> selectQuestionDTOList(Boolean reply, Long kind) {
        return qMapper.selectQuestionDTO(reply, kind);
    }

    // 문의글 물품별, 종류별 리스트 조회
    @Override
    public List<Map<String, Object>> selectProductQuestionList(Long no, Long kind) {
        return qMapper.selectProductList(no, kind);
    }

    // 문의글 회원별리스트 조회
    @Override
    public List<Map<String, Object>> selectMemberQuestionList(String email) {
        return qMapper.selectMemberList(email);
    }

    // 문의글 중복 체크
    @Override
    public int checkQuestionCode(Long questioncode) {
        // TODO Auto-generated method stub
        return 0;
    }

    
}