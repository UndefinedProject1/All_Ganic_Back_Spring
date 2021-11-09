package com.example.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface QuestionMapper {
    // 답글여부, 문의종류에 따른 리스트 출력(admin)
    @Select({
        "<script>",
            "SELECT QUESTIONCODE, QUESTIONTITLE, QUESTIONCONTENT, QUESTIONDATE, PRODUCTCODE ",
            " FROM QUESTIONLIST  WHERE QUESTIONREPLY=#{reply}",
            " <if test='kind != 0'> AND QUESTIONKIND=#{kind}  </if>",
            "ORDER BY QUESTIONDATE ",
            " <if test='reply == true'> DESC  </if>",
            " <if test='reply == false'> ASC  </if>",
        "</script>"    
    })
	public List<Map<String, Object>> selectQuestionDTO(Boolean reply, Long kind);
    
    // 물품코드와 문의종류에 따른 리스트 출력(물품 상세)
    @Select({
        "<script>",
            "SELECT QUESTIONTITLE, QUESTIONCONTENT, QUESTIONDATE, QUESTIONKIND, MEMBER ",
            " FROM QUESTION WHERE PRODUCT=#{no} ",
            " <if test='kind != 0'> AND QUESTIONKIND=#{kind}  </if>",
            "ORDER BY QUESTIONDATE DESC",
        "</script>"
    })
    public List<Map<String, Object>> selectProductList(Long no, Long kind);

    // 멤버아이디에 따른 문의리스트 출력(마이페이지)
    @Select({
        "<script>",
            "SELECT QUESTION.QUESTIONCODE, QUESTION.QUESTIONTITLE, QUESTION.QUESTIONCONTENT, ",
            "QUESTION.QUESTIONDATE, QUESTION.QUESTIONKIND, QUESTION.QUESTIONREPLY, ",
            " PRODUCT.PRODUCTNAME, BRAND.BRANDNAME",
            " FROM QUESTION ",
            " INNER JOIN PRODUCT ON QUESTION.PRODUCT = PRODUCT.PRODUCTCODE",
            " INNER JOIN BRAND ON PRODUCT.BRAND = BRAND.BRANDCODE",
            "WHERE MEMBER=#{email} ",
            "ORDER BY QUESTIONDATE DESC",
        "</script>"
    })
    public List<Map<String, Object>> selectMemberList(String email);
}
