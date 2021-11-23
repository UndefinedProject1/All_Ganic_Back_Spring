package com.example.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface QuestionMapper {
    // 답글여부, 문의종류에 따른 리스트 출력(admin)
    @Select({
        "<script>",
            "SELECT QUESTIONCODE, QUESTIONTITLE, QUESTIONCONTENT, PRODUCTCODE, ",
            "to_char(QUESTIONDATE,'YYYY-MM-DD') AS QUESTIONDATE ",
            " FROM QUESTIONLIST  WHERE QUESTIONREPLY=#{reply}",
            " <if test='kind != 0'> AND QUESTIONKIND=#{kind}  </if>",
            "ORDER BY QUESTIONDATE ",
            " <if test='reply == true'> DESC  </if>",
            " <if test='reply == false'> ASC  </if>",
        "</script>"    
    })
	public List<Map<String, Object>> selectQuestionDTO(@Param("reply") Boolean reply, @Param("king") Long kind);
    
    // 물품코드와 문의종류에 따른 리스트 출력(물품 상세)
    @Select({
        "<script>",
            "SELECT QUESTIONTITLE, QUESTIONCONTENT, to_char(QUESTIONDATE,'YYYY-MM-DD') AS QUESTIONDATE, QUESTIONKIND, QUESTIONREPLY, MEMBER ",
            " FROM QUESTION WHERE PRODUCT=#{no} ",
            " <if test='kind != 0'> AND QUESTIONKIND=#{kind}  </if>",
            "ORDER BY QUESTIONDATE DESC",
        "</script>"
    })
    public List<Map<String, Object>> selectProductList(@Param("no") Long no, @Param("kind") Long kind);

    // 멤버아이디에 따른 문의리스트 출력(마이페이지)
    @Select({
        "<script>",
            "SELECT QUESTION.QUESTIONCODE, QUESTION.QUESTIONTITLE, QUESTION.QUESTIONCONTENT, ",
            "to_char(QUESTION.QUESTIONDATE, 'YYYY-MM-DD') AS QUESTIONDATE, QUESTION.QUESTIONKIND, QUESTION.QUESTIONREPLY, ",
            " PRODUCT.PRODUCTNAME, BRAND.BRANDNAME",
            " FROM QUESTION ",
            " INNER JOIN PRODUCT ON QUESTION.PRODUCT = PRODUCT.PRODUCTCODE",
            " INNER JOIN BRAND ON PRODUCT.BRAND = BRAND.BRANDCODE",
            "WHERE MEMBER=#{email} ",
            "ORDER BY QUESTIONDATE DESC",
        "</script>"
    })
    public List<Map<String, Object>> selectMemberList(@Param("email") String email);

    // 문의글 답변 나타내기
    @Select({
        "SELECT ANSWERDATE, ANSWERCONTENT ",
        " FROM QUESTION WHERE QUESTIONCODE=#{code}",
    })
    public Map<String, Object> selectMemberAnswer(@Param("code") Long code);
}
