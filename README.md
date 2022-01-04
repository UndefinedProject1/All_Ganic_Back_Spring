# 🌏 All_Ganic (Back End-Spring)

![header](https://capsule-render.vercel.app/api?type=soft&color=auto&height=300&section=header&text=효율적이고%20일관성있는%20코드로&desc=깔끔한%20데이터%20전달에%20집중&fontSize=40&descSize=25&descAlign=65)

## 목차
1. [요약](#summary--요약)
2. [서버구조](#server-structure--서버구조)
3. [ERD 설계](#erd-설계)

## Summary / 요약
- __프로젝트 기간__ : 2021.10.04 - 2021.12.03
- __백엔드 구성__ : 정지희
- __메인 개발 환경__ : Spring Boot


## Server Structure / 서버구조
![프레젠테이션1](https://user-images.githubusercontent.com/85853167/147995935-a7cb34a9-0699-4763-ba5b-34d71b2becf6.png)
> Spring MVC를 바탕으로 제작이 되었습니다. View인 Vew에서 Rest API로 백의 Controller에 요청이 오면 Service, ServiceImpl를 통해 DB에 접근하게 됩니다.
> 이 때 Query문을 써야하면 Mybatis로 아니면 JPA를 이용하여 데이터에 접근합니다. 이 때 어떤 방법으로 접근했는지, 어떤 형태의 데이터가 받아오는지에 따라 Entity, DTO, Projection에 데이터를 담게 됩니다.


## ERD 설계

![header](https://capsule-render.vercel.app/api?type=soft&height=300&text=Hello%20World!&desc=Hello%20capsule%20render)
