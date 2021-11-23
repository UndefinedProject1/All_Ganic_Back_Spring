package com.example.service;

import java.util.List;
import java.util.Map;

import com.example.entity.CancelHistory;
import com.example.mappers.CancelHistoryMapper;
import com.example.repository.CancelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancelServiceImpl implements CancelService{

    @Autowired
    CancelRepository cRepository;

    @Autowired
    CancelHistoryMapper cMapper;

    @Override
    public void insertCancel(CancelHistory Cancel) {
        cRepository.save(Cancel);        
    }

    @Override
    public List<Map<String, Object>> selectMemberCancelList(String email) {
        return cMapper.selectCancelMemberList(email);
    }
    
}
