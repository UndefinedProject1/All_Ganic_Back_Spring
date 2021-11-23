package com.example.service;

import java.util.List;
import java.util.Map;

import com.example.entity.CancelHistory;

import org.springframework.stereotype.Service;

@Service
public interface CancelService {
    
    // 취소내역 추가
    public void insertCancel(CancelHistory Cancel);

    // 회원별 환불내역
    public List<Map<String, Object>> selectMemberCancelList(String email);
}
