package com.example.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.Review;
import com.example.jwt.JwtUtil;
import com.example.service.MemberService;
import com.example.service.PayHistoryService;
import com.example.service.ProductService;
import com.example.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    MemberService mService;

    @Autowired
    ProductService pService;

    @Autowired
    PayHistoryService phService;

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
                review.setProduct(pService.selectProduct(no)); // 파라미터로 넘어온 물품코드를 통해 물품정보 찾기
                review.setReviewimg(file.getBytes());
                review.setReviewimgname(file.getOriginalFilename());
                review.setReviewimgtype(file.getContentType());
                rService.insertReview(review);

                phService.updateReview(no, useremail); // 리뷰 작성 시 결제내역의 리뷰확인에 true로 변경해주기
                map.put("result", 1L);
            }else{
                map.put("result", 0L);
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

    // 리뷰 이미지 찾기
    // 127.0.0.1:8080/REST/api/review_image?no=
    // <img src="/admin/select_image?no=12" />
    // 여기서 no는 리뷰코드
    @RequestMapping(value = "/review_image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> selectImage(@RequestParam("no") long no) throws Exception {
        try {
            Review review = rService.getReviewOne(no);
            if (review.getReviewimg() != null) {
                HttpHeaders headers = new HttpHeaders();
                if (review.getReviewimgtype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (review.getReviewimgtype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (review.getReviewimgtype().equals("image/git")) {
                    headers.setContentType(MediaType.IMAGE_GIF);
                }
                ResponseEntity<byte[]> response = new ResponseEntity<>(review.getReviewimg(), headers, HttpStatus.OK);
                return response;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 물품별 리뷰들 들고오기
    // 127.0.0.1:8080/REST/api/review/list/product?code=14&page=1
    // 여기서 code는 물품코드
    @RequestMapping(value="/review/list/product", method=RequestMethod.GET)
    public Map<String, Object> productReviewListGET(@RequestParam(name = "code") long code, 
        @RequestParam(name = "page", defaultValue = "1") long page) {
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            long start, end = 1;
            if(page == 1){
                start = 1;
                end = 1*5;
                List<Map<String, Object>> list = rService.selectProductList(code, start, end);
                map.put("list", list);
                map.put("result", 1L);
            }
            else{
                start = (page-1)*5+1;
                end = page*5; 
                List<Map<String, Object>> list = rService.selectProductList(code, start, end);
                map.put("list", list);
                map.put("result", 1L);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }
    
    // 회원별 리뷰들 들고오기
    // 127.0.0.1:8080/REST/api/member/review/list
    // 여기서 회원확인 token값으로 확인
    @RequestMapping(value="member/review/list", method=RequestMethod.GET)
    public Map<String, Object> memberReviewListGET(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            String useremail = jwtUtil.extractUsername(token.substring(7));
            if (jwtUtil.extractUsername(token.substring(7)).equals(useremail)) {
                List<Map<String, Object>> list = rService.selectMemberList(useremail);
                map.put("list", list);
                map.put("result", 1L);
            }
            map.put("result", 0L);
        }
        catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }
    
}
