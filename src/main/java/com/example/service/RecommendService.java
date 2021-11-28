package com.example.service;

import java.util.List;
import java.util.Map;

import com.example.entity.Recommend;

import org.springframework.stereotype.Service;

@Service
public interface RecommendService {

    // 결제아이디, 주문번호 저장
    public void insertRecommned(Recommend recommend);

    // 추천물품 데이터 리턴
    public Recommend findRecommend(Long code);

    // 추천물품 key와 value업데이트
    public void updateKeyValue(String key, String count, long no);

    // 추천물품 있는지 확인
    public List<Map<String, Object>> checkRecommend(Long code);
}
