package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.Question;
import com.example.jwt.JwtUtil;
import com.example.service.MemberService;
import com.example.service.ProductService;
import com.example.service.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class QuestionController {

    @Autowired
    MemberService mService;

    @Autowired
    ProductService pService;

    @Autowired
    QuestionService qService;

    @Autowired
    JwtUtil jwtUtil;
    
    // 문의글 작성
    // 127.0.0.1:8080/REST/api/question/insert?no=14
    // 여기서 넘어오는 no는 물품 정보
    // {"questiontitle":"냄새가", "questioncontent":"좋아요", "questionkind":1}
    @RequestMapping(value = "/question/insert", method = {
        RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> productInsertPOST(@RequestBody Question question,
            @RequestParam(name = "no", defaultValue = "0") long no,@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
            if (jwtUtil.extractUsername(token.substring(7)).equals(useremail)) {

                question.setMember(mService.getMemberOne(useremail)); // 회원정보를 통해 member를 찾아줌
                question.setProduct(pService.selectProduct(no)); // 파라미터로 넘어온 물품코드를 통해 물품정보 찾기
                qService.insertQuestion(question);
                map.put("result", 1L);
            }
            else{
                map.put("result", 0L);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 문의 삭제
    // 127.0.0.1:8080/REST/api/question/delete?no=13
    // 여기서 넘어오는 no는 문의 코드
    @RequestMapping(value = "/question/delete", method = RequestMethod.DELETE)
    public String delete(@RequestParam(name = "no", defaultValue = "0") long no) {
        try {
            if (no == 0) { // 파라미터가 전달되지 못했을 경우
                return "0";
            } else {
                qService.deleteQuestion(no);
                return "1";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Integer.toString(e.hashCode());
        }
    }

    // 문의글 수정
    // 127.0.0.1:8080/REST/api/question/update?no=13
    @RequestMapping(value = "/question/update", method = {
        RequestMethod.PUT }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> questionUpdate(@RequestParam(name = "no", defaultValue = "0") long no ,@RequestBody Question question,
            @RequestHeader("token") String token) {

        Map<String, Object> map = new HashMap<>();
        try {
            String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
            if (jwtUtil.extractUsername(token.substring(7)).equals(useremail)) {
                Question question1 = qService.selectQuestion(no);
                question1.setQuestiontitle(question.getQuestiontitle());
                question1.setQuestioncontent(question.getQuestioncontent());
                question1.setQuestionkind(question.getQuestionkind());
                qService.updateQuestion(question1);
                map.put("result", 1L);
            }
            else{
                map.put("result", 0L);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 문의글 찾기
    // 127.0.0.1:8080/REST/api/question/select?no=13
    // 여기서 넘어오는 no는 문의 코드
    @RequestMapping(value = "/question/select", method = RequestMethod.GET)
    public Map<String, Object> questionSelectGET(@RequestParam(name = "no", defaultValue = "0") long no) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Question question = qService.selectQuestion(no);
            if(question != null){
                map.put("result", 1L);
                map.put("question", question);
            }
            else{
                map.put("result", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 회원별 문의글 리스트 찾기(날짜 기준으로 정렬)
    // 127.0.0.1:8080/REST/api/question/member/selectlist
    @RequestMapping(value = "/question/member/selectlist", method = RequestMethod.GET)
    public Map<String, Object> MemberSelectListGET(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
            if (jwtUtil.extractUsername(token.substring(7)).equals(useremail)) {
                List<Map<String, Object>> list = qService.selectMemberQuestionList(useremail);
                map.put("list", list);
                map.put("result", 1L);
            }
            else{
                map.put("result", 0L);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 문의글 답글여부, 종류별 조회(날짜 기준 정렬)
    // 127.0.0.1:8080/REST/api/question/all/selectlist?reply=false&kind=2
    @RequestMapping(value = "/question/all/selectlist", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> AllSelectListGET(@RequestParam(name = "reply") Boolean reply, 
    @RequestParam(name = "kind", defaultValue = "0") long kind) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Map<String, Object>> list = qService.selectQuestionDTOList(reply, kind);
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 물품상세페이지에서 문의글 종류별 조회(날짜 기준 정렬)
    // 127.0.0.1:8080/REST/api/question/product/selectlist?no=4&kind=1
    @RequestMapping(value = "/question/product/selectlist", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> productSelectListGET(@RequestParam(name = "no", defaultValue = "0") Long no, 
    @RequestParam(name = "kind", defaultValue = "0") long kind) {
        Map<String, Object> map = new HashMap<>();
        try {
            if(no == 0){
                map.put("result", 0); // 0이면 물품코드가 제대로 안넘어왔다는 의미
            }
            else{
                List<Map<String, Object>> list = qService.selectProductQuestionList(no, kind);
                map.put("list", list);
                map.put("result", 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    
    // 문의글에 대한 답변 나타내기
    // 127.0.0.1:8080/REST/api/member/question/answer?code=1
    // 여기서 코드는 문의코드
    @GetMapping(value = "member/question/answer")
    public Map<String, Object> memberQuestionAnswerGET(@RequestHeader("token") String token, @RequestParam(name = "code") long code) {
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            Map<String, Object> answer = qService.selectQuestionAnswer(code);
            map.put("answer", answer);
            map.put("result", 1L);
            map.put("result", 0L);
        }
        catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

}