package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;

import com.example.entity.Product;
import com.example.entity.ProductProjection;
import com.example.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{
    
    @Autowired
    EntityManagerFactory emf;

    @Autowired
    ProductRepository pRepository;

    //제품 추가
    public void insertProduct(Product product){
        pRepository.save(product);
    }

    //제품 삭제
    @Override
    public void deleteProduct(Long product) {
        pRepository.deleteById(product);
    }

    //제품 수정
    @Override
    public void updteProduct(Product product) {
        pRepository.save(product);
    }

    //제품정보 가져오기, 제품 이미지 찾고 변환하기
    @Override
    public Product selectProduct(long code) {
        Optional<Product> product = pRepository.findById(code);
        return product.orElse(null);
    }

    //해당 브랜드 제품들 가져오기
    @Override
    public List<Product> getBrandProduct(long code) {
        return null;
    }

    //해당 브랜드 제품들 가져오기
    @Override
    public List<ProductProjection> getListProduct() {
        return pRepository.queryListProduct();
    }

    //제품 전체 조회
    @Override
    public List<ProductProjection> selectProductList() {
        return pRepository.findAllByOrderByProductcodeAsc();
    }

    //브랜드 코드 별 제품 조회(jpa)
    @Override
    public List<ProductProjection> selectBProductList(long code) {
        return pRepository.findByBrand_Brandcode(code);
    }

    //브랜드 코드 별 제품 조회(sql)
    @Override
    public List<ProductProjection> selectBProductLsit2(Long code) {
        return pRepository.queryListBProduct(code);
    }

    //카테고리 코드 별 제품 조회(sql)
    @Override
    public List<ProductProjection> selectCProductLsit(Long code) {
        return pRepository.queryListCProduct(code);
    }

    //카테고리 코드 별 제품 조회(jpa)
    @Override
    public List<ProductProjection> selectCProductLsit2(String code) {
        return pRepository.findByCategory_CategorycodeStartingWith(code);
    }
}
