package com.example.service;

import com.example.entity.Pay;
import com.example.repository.PayRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService{
    
    @Autowired
    PayRepository pRepository;

    @Override
    public void insertPayment(Pay pay) {
        pRepository.save(pay);
    }
}
