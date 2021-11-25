package com.example.mappers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MemberMapper {
    // 이메일 중복 체크
    @Select({"SELECT COUNT(USEREMAIL) FROM MEMBER WHERE USEREMAIL=#{email}"})
	public int selectCount(@Param("email") String email);

    // 회원정보 찾기
    @Select({"SELECT USEREMAIL, USERNAME, USERTEL, USERROLE, POST, ADDRESS, DETAILEADDRESS, ",
    " to_char(USERDATE,'YYYY-MM-DD') AS USERDATE",
    " FROM MEMBER WHERE USEREMAIL=#{email}"})
    public Map<String, Object> selectMember(@Param("email") String email);

    // 회원 탈퇴 시 날짜 및 여부 변경
    @Update({
        "UPDATE MEMBER SET LEAVECHECK=TRUE, LEAVEDATE=#{date} WHERE USEREMAIL=#{email}"
    })
    public int updateLeave(@Param("date") Date date, @Param("email") String email);

    // 탈퇴 회원 목록
    @Select({
        "SELECT USEREMAIL FROM MEMBER WHERE LEAVECHECK=TRUE AND LEAVEDATE=#{date}"
    })
    public List<String> deleteMemberList(@Param("date") Date date);

    // 관리자의 회원관리
    @Select({
        "SELECT * FROM MEMBERLIST ORDER BY ORDERCNT DESC"
    })
    public List<Map<String, Object>> adminMemberList();
}
