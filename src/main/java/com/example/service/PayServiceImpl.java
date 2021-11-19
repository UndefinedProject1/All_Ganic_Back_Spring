package com.example.service;

import com.example.entity.Pay;
import com.example.mappers.PayMapper;
import com.example.repository.PayRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService{
    
    @Autowired
    PayRepository pRepository;

    @Autowired
    PayMapper pMapper;

    @Override
    public void insertPayment(Pay pay) {
        pRepository.save(pay);
    }

    @Override
    public Pay selectPay(String id, long code) {
        Pay pay = pMapper.selectImpUid(id, code);
        return pay;
    }
}
