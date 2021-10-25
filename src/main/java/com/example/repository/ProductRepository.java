package com.example.repository;

import java.util.List;

import com.example.entity.Product;
import com.example.entity.ProductProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{
    
    // 브랜드코드가 정확하게 일치하는 + 글번호기준 내림차순 정렬
    //List<Product> findByBrandOrderByNoDesc(String brand);

    @Query(value = "SELECT PRODUCTNAME, PRODUCTPRICE FROM PRODUCT", nativeQuery = true)
    public List<Product> queryListProduct();
}
