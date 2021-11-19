package com.example.service;

import com.example.entity.CancelHistory;
import com.example.repository.CancelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancelServiceImpl implements CancelService{

    @Autowired
    CancelRepository cRepository;

    @Override
    public void insertCancel(CancelHistory Cancel) {
        cRepository.save(Cancel);        
    }
    
}
