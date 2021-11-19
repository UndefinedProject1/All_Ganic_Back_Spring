package com.example.service;

import com.example.entity.Pay;

import org.springframework.stereotype.Service;

@Service
public interface PayService {
    
    // 결제아이디, 주문번호 저장
    public void insertPayment(Pay pay);

    // 취소내역을 위한 Pay반환
    public Pay selectPay(String id, long code);

}
