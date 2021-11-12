package com.example.service;

import com.example.entity.Pay;
import com.example.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{
    
    @Autowired
    OrderRepository pRepository;

    @Override
    public void insertPayment(Pay pay) {
        pRepository.save(pay);
    }
}
