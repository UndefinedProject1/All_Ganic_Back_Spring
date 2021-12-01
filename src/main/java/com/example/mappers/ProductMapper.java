package com.example.mappers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.dto.ProductDto;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProductMapper {

    // 브랜드별 물품 조회
    @Select({
        "SELECT PRODUCTCODE, PRODUCTNAME, PRODUCTPRICE, BRANDNAME FROM PRODUCTLIST WHERE BRANDCODE=#{code}"
    })
    public List<ProductDto> selectBrandProductList(@Param("code") long code);

    // 검색 페이지네이션을 위한 개수조회
    @Select({
        "SELECT COUNT(*) FROM(SELECT PRODUCTCODE, PRODUCTNAME, PRODUCTPRICE, BRANDNAME ",
        "FROM PRODUCTLIST WHERE PRODUCTNAME LIKE '%' || #{name} || '%' ) PRODUCTLIST"
    })
    public int serchProductCount(@Param("name") String name);

    // 제품이름 검색 기능+페이지네이션
    @Select({
        "SELECT * FROM(SELECT PRODUCTCODE, PRODUCTNAME, PRODUCTPRICE, BRANDNAME, ",
        "ROW_NUMBER() OVER (ORDER BY PRODUCTNAME ASC) ROWN FROM PRODUCTLIST WHERE PRODUCTNAME LIKE '%'||#{name}||'%') ",
        "PRODUCTLIST WHERE ROWN BETWEEN #{start} AND #{end} "
    })
    public List<ProductDto> serchProductList(@Param("name") String name, @Param("start") long start, @Param("end") long end);

    //카테고리 코드 별 제품 조회
    @Select({
        "SELECT PRODUCTCODE, PRODUCTNAME, PRODUCTPRICE, BRANDNAME FROM PRODUCTLIST WHERE CATEGORYCODE LIKE #{code} || '%'"
    })
    public List<ProductDto> queryListCProduct(@Param("code") String code);

    // 카테고리 별 제품조회 및 페이지네이션
    @Select({
        "SELECT * FROM(SELECT PRODUCTCODE, PRODUCTNAME, PRODUCTPRICE, BRANDNAME, ",
        "ROW_NUMBER() OVER (ORDER BY PRODUCTNAME ASC) ROWN FROM PRODUCTLIST WHERE CATEGORYCODE LIKE #{code} || '%') ",
        "PRODUCTLIST WHERE ROWN BETWEEN #{start} AND #{end} "
    })
    public List<ProductDto> selectCateProductList(@Param("code") String code, @Param("start") long start, @Param("end") long end);

    //제품 1개 조회 (상세 페이지)
    @Select({
        "SELECT PRODUCTCODE, PRODUCTNAME, PRODUCTPRICE, BRANDNAME FROM PRODUCTLIST WHERE PRODUCTCODE =#{code}"
    })
    public ProductDto querySelectProduct(@Param("code") Long code);

    // 브랜드 별 물품 개수
    @Select({
        "SELECT COUNT(*) FROM PRODUCT WHERE BRAND=#{code}"
    })
    public int selectBrandProduct(@Param("code") Long code);

    // 카테고리 별 물품 개수
    @Select({
        "SELECT COUNT(*) FROM PRODUCTLIST WHERE CATEGORYCODE LIKE #{code} || '%'"
    })
    public int selectCateProduct(@Param("code") String code);

    // 판매량조회를 위한 날짜 테이블 
    @Update({
        "UPDATE DUAL SET DUAL_DATE=#{date} WHERE DUAL_ID=#{no}"
    })
	public int InsertDate(@Param("no") long no, @Param("date") Date date);

    // 해당일의 최근 5일간의 판매량 조회
    @Select({
        "SELECT DUAL.DUAL_DATE, (NVL(DATE1.CNT, 0)) AS CNT FROM DUAL ",
        "LEFT OUTER JOIN DATE1 ON DUAL.DUAL_DATE = DATE1.ORDERDATE ORDER BY DUAL.DUAL_DATE ASC"
    })
    public List<Map<String, Object>> selectSalesRate();

    // 브랜드별 점유율
    @Select({
        "SELECT ROUND(CAST((count(*) * 100.0 / select count(*) from PRODUCT) AS FLOAT), 2) AS Percentage, BRAND.BRANDNAME",
        "from PRODUCT ",
        "INNER JOIN BRAND ON PRODUCT.BRAND = BRAND.BRANDCODE",
        "group by PRODUCT.BRAND",
        "ORDER BY Percentage DESC"
    })
    public List<Map<String, Object>> selectBrandShare();

    // 브랜드별 누적 판매량
    @Select({
        "SELECT BRANDNAME, (COUNT(*)) AS CNT FROM PAYHISTORYLIST ",
        "GROUP BY PRODUCTCODE"
    })
    public List<Map<String, Object>> selectBrandSell();

    // 카테고리별 누적 판매량
    @Select({
        "SELECT CATEGORYNAME, (COUNT(*)) AS CNT FROM PAYHISTORYLIST ",
        "GROUP BY CATEGORYCODE"
    })
    public List<Map<String, Object>> selectCateSell();

    // 최근 구매한 물품 코드 리턴
    @Select({
        "SELECT PRODUCT FROM PAYHISTORY WHERE MEMBER=#{email} ORDER BY ORDERDATE ASC LIMIT 1"
    })
    public Long latestOrder(@Param("email") String email);

    // 랜덤으로 물품 출력
    @Select({
        "SELECT PRODUCTCODE FROM PRODUCT ",
        "WHERE CATEGORY LIKE (SELECT SUBSTR(CATEGORY, 0, 3) FROM PRODUCT WHERE PRODUCTCODE=#{code}) || '%'",
        "ORDER BY RAND() LIMIT 1"
    })
    public Long randomProduct(@Param("code") Long code);

    // 판매가능한 상품인지 확인
    @Select({
        "SELECT COUNT(*) FROM PRODUCT WHERE PRODUCTCODE=#{code} AND  PRODUCTIMAGE=null"
    })
    public int checkUnsalableProduct(@Param("code") Long code);
}
