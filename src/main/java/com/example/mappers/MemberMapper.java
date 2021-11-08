package com.example.mappers;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {
    // 이메일 중복 체크
    @Select({"SELECT COUNT(USEREMAIL) FROM MEMBER WHERE USEREMAIL=#{email}"})
	public int selectCount(String email);

    // 회원정보 찾기
    @Select({"SELECT USEREMAIL, USERNAME, USERTEL, USERROLE, POST, ADDRESS, DETAILEADDRESS, ",
    " to_char(USERDATE,'YYYY-MM-DD') AS USERDATE",
    " FROM MEMBER WHERE USEREMAIL=#{email}"})
    public Map<String, Object> selectMember(String email);
}
