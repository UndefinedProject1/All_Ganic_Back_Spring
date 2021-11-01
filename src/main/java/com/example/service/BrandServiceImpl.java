package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;

import com.example.entity.Brand;
import com.example.entity.BrandProjection;
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
    @Override
    public void insertBrand(Brand brand){
        bRepository.save(brand);
    }

    //브랜드 id 호출
    @Override
    public Brand selectBrand(Long no) {
        Optional<Brand> brand = bRepository.findById(no);
        return brand.orElse(null);
    }

    //브랜드 전체 조회
    @Override
    public List<BrandProjection> selectBrandList(){
        return bRepository.findAllByOrderByBrandcodeAsc();
    }

    //브랜드 중복 체크
    @Override
    public int checkBrandCode(Long brandcode) {
        return bRepository.queryCheckBrandcode(brandcode);
    }
}
