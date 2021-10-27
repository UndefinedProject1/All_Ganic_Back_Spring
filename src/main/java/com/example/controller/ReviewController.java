package com.example.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.entity.Review;
import com.example.jwt.JwtUtil;
import com.example.service.MemberServiece;
import com.example.service.ProductService;
import com.example.service.ReviewService;

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
public class ReviewController {

    @Autowired
    ReviewService rService;

    @Autowired
    MemberServiece mService;

    @Autowired
    ProductService pService;

    @Autowired
    JwtUtil jwtUtil;

    // 리뷰 작성
    // 127.0.0.1:8080/REST/api/review/insert?no=14
    // 여기서 넘어오는 no는 물품 정보
    @RequestMapping(value = "/review/insert", method = {
            RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> productInsertPOST(@ModelAttribute Review review,
            @RequestParam(name = "no", defaultValue = "0") long no, @RequestParam("file") MultipartFile file,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
            if (jwtUtil.extractUsername(token.substring(7)).equals(useremail)) {

                review.setMember(mService.getMemberOne(useremail)); // 회원정보를 통해 member를 찾아줌
                review.setProduct(pService.getProductOne(no)); // 파라미터로 넘어온 물품코드를 통해 물품정보 찾기
                review.setReviewimg(file.getBytes());
                review.setReviewimgname(file.getOriginalFilename());
                review.setReviewimgtype(file.getContentType());
                rService.insertReview(review);
                map.put("result", 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 후기 삭제
    // 127.0.0.1:8080/REST/api/review/delete?no=13
    // 여기서 넘어오는 no는 리뷰 코드
    @RequestMapping(value = "/review/delete", method = RequestMethod.DELETE)
    public String delete(@RequestParam(name = "no", defaultValue = "0") long no) {
        try {
            System.out.println(no);
            if (no == 0) { // 파라미터가 전달되지 못했을 경우
                return "0";
            } else {
                rService.deleteReview(no);
                return "1";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Integer.toString(e.hashCode());
        }
    }

    // 후기 수정
    // 127.0.0.1:8080/REST/api/review/update?no=14
    // 여기서 넘어오는 no는 리뷰 코드
    @RequestMapping(value = "/review/update", method = {
            RequestMethod.PUT }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Integer> insertAllPost(@RequestParam("file") MultipartFile file,
            @RequestParam(name = "no", defaultValue = "0") long no, @ModelAttribute Review review) throws IOException {

        Map<String, Integer> map = new HashMap<>();
        try {
            if (no == 0) {
                map.put("result", 0);
            } else {
                Review review1 = rService.getReviewOne(no);
                System.out.println(review1);
                review1.setReviewcontent(review.getReviewcontent());
                review1.setReviewrating(review.getReviewrating());
                review1.setReviewimg(file.getBytes());
                review1.setReviewimgname(file.getOriginalFilename());
                review1.setReviewimgtype(file.getContentType());
                rService.updteReview(review);
                map.put("result", 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

}
