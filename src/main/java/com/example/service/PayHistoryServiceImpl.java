package com.example.service;

import java.util.List;
import java.util.Map;

import com.example.entity.Pay;
import com.example.entity.PayHistory;
import com.example.mappers.PayHistoryMapper;
import com.example.repository.PayHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayHistoryServiceImpl implements PayHistoryService{

    @Autowired
    PayHistoryRepository phRepository;

    @Autowired
    PayHistoryMapper phMapper;

    // 결제 정보 저장
    @Override
    public void insertPayHistory(PayHistory payhistory) {
        phRepository.save(payhistory);
    }

    // 결제내역여부에 따라 리뷰작성가능
    @Override
    public Map<String, Object> checkPayHistory(Long no, String email) {
        return phMapper.selectPayHistoryCheck(no, email);
    }

    // 회원별 주문내역
    @Override
    public List<Map<String, Object>> selectMemberPayList(String email) {
        return phMapper.selectPayMemberList(email);
    }

    // 리뷰 작성 시 reviewcheck 다 true로 변경
    @Override
    public void updateReview(Long no, String email) {
        phMapper.updateReview(no, email);
    }

    // 환불 시 결제내역 삭제
    @Override
    public void deletePayHistory(String id, Long code) {
        phMapper.deletePayHistory(id, code);
    }
    
}
