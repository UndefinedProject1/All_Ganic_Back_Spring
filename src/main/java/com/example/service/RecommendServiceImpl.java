package com.example.service;

import java.util.Map;
import java.util.Optional;

import com.example.entity.Recommend;
import com.example.mappers.RecommendMapper;
import com.example.repository.RecommendRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendServiceImpl implements RecommendService{

    @Autowired
    RecommendRepository rRepository;
    
    @Autowired
    RecommendMapper rMapper;

    @Override
    public void insertRecommned(Recommend recommend) {   
        rRepository.save(recommend);
    }

    @Override
    public Recommend findRecommend(Long code) {
        Optional<Recommend> recommend = rRepository.findByProduct_Productcode(code);
        return recommend.orElse(null);
    }
    
    @Override
    public void updateKeyValue(String key, String count, long no) {
        rMapper.updateReportDate(key, count, no);        
    }

    @Override
    public Map<String, Object> checkRecommend(Long code) {
        return rMapper.checkRecommend(code);
    }

}
