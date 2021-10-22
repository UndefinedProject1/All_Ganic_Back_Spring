package com.example.service;

import java.util.Optional;

import javax.persistence.EntityManagerFactory;

import com.example.entity.Brand;
import com.example.repository.BrandRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService{
    
    @Autowired
    EntityManagerFactory emf;
    @Autowired
    BrandRepository bRepository;

    //브랜드 추가
    public void insertBrand(Brand brand){
        bRepository.save(brand);
    }

    //브랜드 찾기
    @Override
    public Brand selectBrand(long no) {
        Optional<Brand> brand = bRepository.findById(no);
        return brand.orElse(null);
    }
}
