package com.example.service;

import javax.persistence.EntityManagerFactory;

import com.example.entity.Product;
import com.example.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl {
    
    @Autowired
    EntityManagerFactory emf;

    @Autowired
    ProductRepository pRepository;

    //물품등록
    public void joinProduct(Product product){
        pRepository.save(product);
    }
}
