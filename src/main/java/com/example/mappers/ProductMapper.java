package com.example.mappers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProductMapper {

    // 카테고리 별 물품 개수
    @Select({
        "SELECT COUNT(*) FROM PRODUCTLIST WHERE CATEGORY LIKE #{code} || '%'"
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
        "LEFT OUTER JOIN DATE1 ON DUAL.DUAL_DATE = DATE1.ORDERDATE"
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
}
