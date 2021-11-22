package com.example.service;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public interface MainService {
    
    // 물품 삭제 시 이루어지는 transaction
    public void deleteProductTransaction(Long no);

    // 회원삭제 시 이루어지는 transaction
    public void deleteMemberTransaction(String email, Date date);
}
