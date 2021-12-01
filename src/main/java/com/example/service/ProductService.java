package com.example.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.dto.ProductDto;
import com.example.entity.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    //제품 추가
    public void insertProduct(Product product);

    //제품 삭제
    public void deleteProduct(Long product);
    
    //제품 수정
    public void updteProduct(Product product);

    //제품정보 가져오기, 물품 이미지 찾고 변환하기
    public Product selectProduct(long code);

    //브랜드 코드 별 제품 조회(jpa)
    public List<ProductDto> selectBProductList(long code);

    // 브랜드별 제품 개수
    public int selectBrandProductCount(Long code);

    //카테고리 코드 별 제품 조회(sql)
    public List<ProductDto> selectCProductLsit(String code);

    // 카테고리 코드 별 제품 개수
    public int selectCateProductCount(String code);

    //카테고리 코드 별 제품 이름순 조회(jpa)
    public List<ProductDto> selectCProductLsit3(String code, long start, long end);

    //제품 1개 조회 (상세 페이지)
    public ProductDto selectProductOne(long code);

    // 제품 검색을 위한 페이지네이션 개수
    public int serchProductCount(String name);

    //제품 전체 목록(이름에 단어가 포함하는 + 제품이름 오름차순 정렬 + 페이지 네이션)
    public List<ProductDto> selectProductList(String name, long start, long end);

    // 판매량조회를 위한 날짜테이블
    public int updateDate(long no, Date date);

    // 최근5일간의 판매량 조회
    public List<Map<String, Object>> selectSaleRate();

    // 브랜드별 점유율
    public List<Map<String, Object>> selectBrandShare();

    // 브랜드별 판매량
    public List<Map<String, Object>> selectBrandSell();

    // 카테고리별 판매량
    public List<Map<String, Object>> selectCateSell();

    // 해당 회원의 최근 구매 물품코드 리턴
    public Long latestOrder(String email);

    // 추천물품이 없으면 랜덤출력
    public Long randomProduct(Long code);

    // 판매가능한 상품인지 확인
    public int checkUnsalableProduct(Long code);
}
