package com.example.service;

import com.example.entity.CancelHistory;

import org.springframework.stereotype.Service;

@Service
public interface CancelService {
    
    // 취소내역 추가
    public void insertCancel(CancelHistory Cancel);
}
