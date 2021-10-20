package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.Brand;
import com.example.entity.Category;
import com.example.entity.Member;
import com.example.jwt.JwtUtil;
import com.example.service.BrandService;
import com.example.service.CategoryService;
import com.example.service.MemberServiece;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api")
public class AdminController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    BrandService bService;

    @Autowired
    CategoryService cService;

    @Autowired
    MemberServiece mServiece;
    
    //브랜드 추가
    //127.0.0.1:8080/REST/api/admin/brand_join
    @RequestMapping(value = "/admin/brand_join", method = {
        RequestMethod.POST}, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public Map<String, Object> brandInsertPOST(
            @ModelAttribute Brand brand,
            @RequestParam("file")MultipartFile file,
            @RequestHeader("token") String token) { 
            Map<String, Object> map = new HashMap<String, Object>();
            try{
                // 관리자 이메일 가져오기
                String adminId = jwtUtil.extractUsername(token.substring(7));
                Member member = mServiece.getMemberOne(adminId);
                brand.setImage(file.getBytes());
                brand.setImagename(file.getOriginalFilename());
                brand.setImagetype(file.getContentType());
                brand.setMember(member);
                bService.joinBrand(brand);
                
                map.put("result",1);
            }
            catch(Exception e){
                map.put("result",e.hashCode());
            }
            return map;
        }

    //카테고리 추가
    //127.0.0.1:8080/REST/api/admin/category_join
    @RequestMapping(value = "/admin/category_join", method = {
        RequestMethod.POST}, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public Map<String, Object> brandInsertPOST(@RequestBody Category category,
            @RequestHeader("token") String token) { 
            Map<String, Object> map = new HashMap<String, Object>();
            try{
                // 관리자 이메일 가져오기
                String adminId = jwtUtil.extractUsername(token.substring(7));
                Member member = mServiece.getMemberOne(adminId);
                category.setMember(member);
                cService.insertCategory(category);
                
                map.put("result",1);
            }
            catch(Exception e){
                map.put("result",e.hashCode());
            }
            return map;
        }

    //제품 추가
    //127.0.0.1:8080/REST/api/admin/category_join
}
