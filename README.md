# 🌏 All_Ganic (Back End-Spring)

![header](https://capsule-render.vercel.app/api?type=soft&color=auto&height=300&section=header&text=효율적이고%20일관성있는%20코드로&desc=깔끔한%20데이터%20전달에%20집중&fontSize=50&descSize=20&descAlign=75)

## 목차
1. [요약](#summary--요약)
2. [서버구조](#server-structure--서버구조)
3. [ERD 설계](#erd-설계)
4. [기능](#fuction--기능)
5. [특징](#features--특징)
6. [개발환경](#tech--개발환경)
7. [API](#Open-API--오픈-api)

## Summary / 요약
- __프로젝트 기간__ : 2021.10.04 - 2021.12.03
- __백엔드 구성__ : 정지희
- __메인 개발 환경__ : Spring Boot


## Server Structure / 서버구조
![프레젠테이션1](https://user-images.githubusercontent.com/85853167/147995935-a7cb34a9-0699-4763-ba5b-34d71b2becf6.png)
> Spring MVC를 바탕으로 제작이 되었습니다. View인 Vew에서 Rest API로 백의 Controller에 요청이 오면 Service, ServiceImpl를 통해 DB에 접근하게 됩니다.
> 이 때 Query문을 써야하면 Mybatis로 아니면 JPA를 이용하여 데이터에 접근합니다. 이 때 어떤 방법으로 접근했는지, 어떤 형태의 데이터가 받아오는지에 따라 Entity, DTO, Projection에 데이터를 담게 됩니다.


## ERD 설계
![캡처](https://user-images.githubusercontent.com/85853167/147996583-40b5b84d-f71e-420f-a659-2c9697015a9a.PNG)
총 15개의 테이블이 있으며 ADMIN, MEMBER로 구분하여 설계를 했습니다. 설계에 대한 저의 생각이나 구조를 더 자세히 보고싶으시다면 👉🏼[ERD COLUD](https://www.erdcloud.com/d/X52ATW8iNCRWnrLGW)를 눌러주세요.

## Problems and Solutions
### 카카오 유저와 기존 회원과의 혼동
#### 1. 문제정의
- 기존회원과 카카오톡 회원의 이메일이 같을 시 구분불가 

#### 2. 사실수집
- 기존 회원의 이메일이 카카오톡 계정의 이메일과 동일 시 카카오톡 로그인과 사이트 내부 로그인 구분없이 로그인 시도가 가능해짐
- 비밀번호 찾기와 같은 기능은 기존회원만 가능(카카오톡은 유저 구별을 위해 암호가 따로 정해져있음), 카카오톡 로그인을 하려다 암호가 틀렸다는 알람을 보고 오해하여 암호를 변경할 수 있음

#### 3. 원인추론
- 로그인 시 sns으로 시도하려는 것인지 구분 없음

#### 4. 조사방법결정
- 카카오톡 로그인인지 구분하는 값을 Param으로 전달받고, 내부 security bcpe match기능으로 지정된 카카오톡 비밀번호와 비교해 맞는지를 구분

#### 5. 조사방법구현
``` javascript
// 로그인
// POST 127.0.0.1:8080/REST/api/member/login?sns=true
@PostMapping(value = "/member/login", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public Map<String, Object> memberLoginPOST(@RequestBody Member member, @RequestParam(name = "sns") Boolean sns) {
    Map<String, Object> map = new HashMap<String, Object>();
    try {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        int check = mServiece.leaveMemberCheck(member.getUseremail());
        if(check == 1){ // 탈퇴한 회원
            map.put("result", 4L);
            map.put("state", "이미 탈퇴하신 회원입니다.");
        }
        else{ // 탈퇴하지 않은 회원
            Member member1 = mServiece.getMemberOne(member.getUseremail());
            if(sns == true){ // 카카오로 로그인 시
                if (bcpe.matches("kakao_login_pw", member1.getUserpw())) {
                    map.put("result", 1L);
                    map.put("token", jwtUtil.generateToken(member.getUseremail()));
                }
                else{
                    map.put("result", 0L);
                    map.put("state", "카카오 유저가 아닙니다. 사이트 로그인에서 로그인을 시도하여 주십시오.");
                }
            }else{ // 그냥 로그인 시
                if(bcpe.matches("kakao_login_pw", member1.getUserpw())) {
                    map.put("result", 0L);
                    map.put("state", "카카오 유저입니다. 카카오 로그인에서 로그인을 시도하여 주십시오.");
                }
                else{
                    authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(member.getUseremail(), member.getUserpw()));
                    map.put("result", 1L);
                    map.put("token", jwtUtil.generateToken(member.getUseremail()));
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        map.put("result", e.hashCode());
    }
    return map;
}

// 비밀번호 변경 시 카카오유저인지 확인
// GET 127.0.0.1:8080/REST/api/kakao/user/check
@GetMapping(value = "/kakao/user/check")
public int kakaoUserCheckGET(@RequestHeader("token") String token) {
    int result;
    try {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
        // 아이디를 이용해 기존 정보 가져오기
        Member member = mServiece.getMemberOne(useremail);
        // 토큰과 사용자 아이디 일치 시점
        if (bcpe.matches("kakao_login_pw", member.getUserpw())) {
            result = 1;   
        }
        else{
            result = 0;
        }
    } catch (Exception e) {
        e.printStackTrace();
        result = e.hashCode();
    }
    return result;
}
```
- `if(bcpe.matches("kakao_login_pw", member1.getUserpw()))`

#### 6. 문제해결
- 암호화된 비밀번호를 내부 기능인 bcpe로 이메일과 카카오톡 비밀번호를 비교해 맞는지를 구분

---
### 회원탈퇴, 물품삭제 시 연관된 정보들 일괄 처리
#### 1. 문제정의
- 회원탈퇴, 물품삭제 등 외래키로 잡혀있는 정보가 있을 시 에러발생

#### 2. 사실수집
- 회원의 경우 외래키로 지정된 CartItem, Cart, CancleHistory, PayHistory, Report, Question, Review가 정리되야지 삭제 가능
- 물품의 경우 외래키로 지정된 SubImage, Question, Review, CartItem이 정리되야지 삭제 가능

#### 3. 조사방법결정
- EntityManager의 기능인 Transaction을 사용하여 한 번에 처리

#### 4. 조사방법구현
``` javascript
// 로그인
// POST 127.0.0.1:8080/REST/api/member/login?sns=true
@PostMapping(value = "/member/login", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public Map<String, Object> memberLoginPOST(@RequestBody Member member, @RequestParam(name = "sns") Boolean sns) {
    Map<String, Object> map = new HashMap<String, Object>();
    try {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        int check = mServiece.leaveMemberCheck(member.getUseremail());
        if(check == 1){ // 탈퇴한 회원
            map.put("result", 4L);
            map.put("state", "이미 탈퇴하신 회원입니다.");
        }
        else{ // 탈퇴하지 않은 회원
            Member member1 = mServiece.getMemberOne(member.getUseremail());
            if(sns == true){ // 카카오로 로그인 시
                if (bcpe.matches("kakao_login_pw", member1.getUserpw())) {
                    map.put("result", 1L);
                    map.put("token", jwtUtil.generateToken(member.getUseremail()));
                }
                else{
                    map.put("result", 0L);
                    map.put("state", "카카오 유저가 아닙니다. 사이트 로그인에서 로그인을 시도하여 주십시오.");
                }
            }else{ // 그냥 로그인 시
                if(bcpe.matches("kakao_login_pw", member1.getUserpw())) {
                    map.put("result", 0L);
                    map.put("state", "카카오 유저입니다. 카카오 로그인에서 로그인을 시도하여 주십시오.");
                }
                else{
                    authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(member.getUseremail(), member.getUserpw()));
                    map.put("result", 1L);
                    map.put("token", jwtUtil.generateToken(member.getUseremail()));
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        map.put("result", e.hashCode());
    }
    return map;
}

// 비밀번호 변경 시 카카오유저인지 확인
// GET 127.0.0.1:8080/REST/api/kakao/user/check
@GetMapping(value = "/kakao/user/check")
public int kakaoUserCheckGET(@RequestHeader("token") String token) {
    int result;
    try {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
        // 아이디를 이용해 기존 정보 가져오기
        Member member = mServiece.getMemberOne(useremail);
        // 토큰과 사용자 아이디 일치 시점
        if (bcpe.matches("kakao_login_pw", member.getUserpw())) {
            result = 1;   
        }
        else{
            result = 0;
        }
    } catch (Exception e) {
        e.printStackTrace();
        result = e.hashCode();
    }
    return result;
}
```
- `if(bcpe.matches("kakao_login_pw", member1.getUserpw()))`

#### 5. 문제해결
- Transaction을 사용하여 연관 정보들을 Query문을 통해 일괄 처리, 회원의 경우 결제와 같은 중요한 정보를 지니고 있기때문에 탈퇴한다는 날로부터 1년 뒤를 정보삭제날로 지정 후 매일 자정 스케쥴러를 이용해 LeaveCheck가 ture이면서 LeaveDate가 당일인 것들을 삭제
- 연관정보 중 누적통계에 필요한 결제정보의 경우 외래키인 Member를 ghost라는 임시 계정을 참조하게 하여 회원정보는 사라지고 필요한 통계정보만 남기게함

---
## Fuction / 기능


## Features / 특징

## Tech / 개발환경

## Open API / 오픈 api
![header](https://capsule-render.vercel.app/api?type=soft&height=300&text=Hello%20World!&desc=Hello%20capsule%20render)
