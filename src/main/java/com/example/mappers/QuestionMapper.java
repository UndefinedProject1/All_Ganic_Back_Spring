package com.example.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface QuestionMapper {
//SELECT QUESTIONCODE, QUESTIONTITLE, QUESTIONCONTENT, QUESTIONDATE, PRODUCTCODE FROM QUESTIONLIST  
// WHERE QUESTIONREPLY =:reply AND QUESTIONKIND =:kind ORDER BY QUESTIONDATE DESC
    @Select({
        "<script>",
            "SELECT QUESTIONCODE, QUESTIONTITLE, QUESTIONCONTENT, QUESTIONDATE, PRODUCTCODE ",
            " FROM QUESTIONLIST  WHERE QUESTIONREPLY=#{reply}",
            " <if test='kind != 0'> AND QUESTIONKIND=#{kind}  </if>",
            "ORDER BY QUESTIONDATE DESC",
        "</script>"    
    })
	public List<Map<String, Object>> selectQuestionDTO(Boolean reply, Long kind);
    
}
