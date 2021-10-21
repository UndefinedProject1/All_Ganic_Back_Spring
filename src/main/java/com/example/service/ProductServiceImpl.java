package com.example.service;

import java.util.Optional;

import javax.persistence.EntityManagerFactory;

import com.example.entity.Product;
import com.example.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{
    
    @Autowired
    EntityManagerFactory emf;

    @Autowired
    ProductRepository pRepository;

    //물품등록
    public void insertProduct(Product product){
        pRepository.save(product);
    }

    //물품 삭제
    @Override
    public void deleteProduct(Long product) {
        pRepository.deleteById(product);
    }

    //물품 수정
    @Override
    public void updteProduct(Product product) {
        pRepository.save(product);
    }
<<<<<<< Updated upstream

    //제품정보 가져오기
    @Override
    public Product getProductOne(long code) {
        Optional<Product> product = pRepository.findById(code);
        return product.orElse(null);
    }
=======
>>>>>>> Stashed changes
    
}
