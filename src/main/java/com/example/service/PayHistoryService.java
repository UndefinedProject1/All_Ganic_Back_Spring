package com.example.service;

import java.util.List;
import java.util.Map;

import com.example.entity.PayHistory;

import org.springframework.stereotype.Service;

@Service
public interface PayHistoryService {
    
    // 결제아이디, 주문번호 저장
    public void insertPayHistory(PayHistory payhistory);

    // 물품페이지 리뷰작성할 수 있는지 확인(no는 물품코드, email은 회원정보)
    public Map<String, Object> checkPayHistory(Long no, String email);

    // 회원별 주문내역
    public List<Map<String, Object>> selectMemberPayList(String email);
}
