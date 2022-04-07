# Co-ala 코딩! 알려주라

![](../../아키텍처이미지/og_img.png)
<br>

## 🤷 프로젝트 소개

### co-ala 코딩! 알려주라

안녕하세요! 항해99 5기 8조 "코알라” 팀입니다<br/>
“코알라”는 코딩 관련하여 질문 사항이 있는 사람이 채팅과 게시글을 통해 질문 하고 응답을 받을 수 있는 사이트입니다.

이런 분에게 좋아요!👍🏻<br/>

“빠르게 해결하고 싶은데 답변이 안달려 답답해요”😮‍💨<br/>
“에러가 발생했는데 어떻게 해결해야할지 모르겠어요”🥲<br/>
“자료는 많은데 어떻게 해결 방법을 적용해야할지 복잡해요”😵‍💫<br/>
당신이 직면한 문제! 당신만의 문제가 아닙니다. 공유하고 함께 해결해요! 코딩! 알려주라~<br/>

#### 👉🏻Web Site : https://www.co-ala.com

<br>

## 📌 Co-ala의 핵심 기능
- #### 메인 페이지 및 게시글 페이지 별 실시간 채팅 구현 ✨

- #### 채팅 별 접속 인원수 표기 🔢

- #### 답변 채택 및 답변 작성 알람 기능🗨️

- #### 채택에 따른 포인트 획득 및 종합, 월간, 주간 랭킹 시스템👑


 ## 🎥 시연 영상
바로가기 -> https://youtu.be/Vwd4n9a205M



## 👩🏻‍💻 개발기간 및 팀원소개 🧑🏻‍💻

### 기간: 2022.02.25 ~ 2022.04.08

### Front-end
<p><a href="https://github.com/zeze88" target="_blank"><img width="150"  src="https://user-images.githubusercontent.com/93499244/162171992-47a626ce-3856-4fae-9d05-045d2f6f9ebb.svg"/></a></p>
<p><a href="https://github.com/cyjin463" target="_blank"><img width="150"  src="https://user-images.githubusercontent.com/93499244/162171379-246ed4d8-2bfb-4ef8-8498-caa79622d792.svg"/></a></p>

### Back-end
<p><a href="https://github.com/Livelyoneweek" target="_blank"><img width="150"  src="https://user-images.githubusercontent.com/93499244/162172511-809a848d-db93-4020-81fc-7fffbfc8acb5.svg"/></a></p>
<p><a href="https://github.com/kyungwoon" target="_blank"><img width="150"  src="https://user-images.githubusercontent.com/93499244/162172506-1239cfb0-da08-4532-b2b2-5769a8982d9e.svg"/></a></p>
<p><a href="https://github.com/P-jeong-hee" target="_blank"><img width="150"  src="https://user-images.githubusercontent.com/93499244/162172494-fa9b6bdd-f337-4e15-9d11-0cb824ae4932.svg"/></a></p>
<br>

## 🔨 사용한 기술 스택 🔨

### Front-end

<img src="https://img.shields.io/badge/javascript-F7DF1E?style=float&logo=javascript&logoColor=white"><img src="https://img.shields.io/badge/Redux-764ABC?style=float&logo=Redux&logoColor=white"> <img src="https://img.shields.io/badge/css-1572B6?style=float&logo=css3&logoColor=white">
<img sc="https://img.shields.io/badge/react-61DAFB?style=float&logo=react&logoColor=white"><img src="https://img.shields.io/badge/aws-232F3E?style=float&logo=Amazon AWS&logoColor=white"><img src="https://img.shields.io/badge/Axios-181717?style=float&logo=github&logoColor=white">

### Back-end
<img src="https://img.shields.io/badge/Springboot-47?style=for-the-badge&logo=Springboot&logoColor=white"><img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"><img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white"><img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white"><img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white"><img src="https://img.shields.io/badge/Redis-FC5230?style=for-the-badge&logo=Redis&logoColor=white"><br>
<img src="https://img.shields.io/badge/Amazon_AWS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white"><img src="https://img.shields.io/badge/TravisCI-FC5230?style=for-the-badge&logo=TravisCI&logoColor=white"><img src="https://img.shields.io/badge/CodeDepoly-1F497D?style=for-the-badge&logo=CodeDepoly&logoColor=white"><img src="https://img.shields.io/badge/S3-FC5230?style=for-the-badge&logo=S3&logoColor=white"><img src="https://img.shields.io/badge/Nginx-7DB249?style=for-the-badge&logo=Nginx&logoColor=white">


### Tool

<img src="https://img.shields.io/badge/github-181717?style=float&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/git-F05032?style=float&logo=git&logoColor=white">

<br>

## 🏷 API Table 🏷
<details>
 <summary>아키텍처</summary>
    아키텍처 이미지
 </details>

<details>
 <summary>ERD</summary>
     ERD 이미지
 </details>

<details>
 <summary>API</summary>
    API 이미지
 </details>
<br/>

## ⚽ Trouble Shooting 
<details>
 <summary> (1) 채팅 비속어 필터링</summary>
 1. 문제 발생 : 처음 비속어 필터링을 구현할 때  매 채팅 시마다 DB에 저장한 비속어들을 리스트로 받아서 반복문을 통해 비속어 감지하여 속도 저하<br>
2. 원인 : 비속어 DB는 자주 변하는 것이 아니고, DB를 계속해서 조회<br>
3. 해결 : 인메모리 디비를 활용할 수 있다고 생각하여 비속어 디비에서 레디스로 내려받아 저장을 하였고,
데이터 구조를 리스트가 아닌 해쉬맵으로 받아 containsValue 메소드를 통해 비속어를 감지하여 반복작업 감


 </details>

<details>
 <summary> (2) 채팅 참여 인원수 확인</summary>
1. 문제 발생 : 채팅 참여 인원은 정상적으로 카운팅이 되었지만 채팅 퇴장 인원은 정상적으로 카운팅이 안되는 문제 발생<br>
 <img width="414" alt="스크린샷 2022-04-07 오후 7 19 02" src="https://user-images.githubusercontent.com/94433709/162177693-bd76da2b-ed2d-4a10-8df2-a3c35bb3f2f8.png">

2. 원인 : 연결이 끊어지는 경우 구독 정보를 리턴해주지 않아 유저가 채팅방 퇴장 시 정상적으로 카운팅이 되지 않음<br>
- 해당 채팅을 구독하면 Message Header(메시지 헤더)의 destination(데스틴에이션)정보를 키값으로 유저 카운팅을 했지만 
연결이 끊기면 Message Header(메시지 헤더)에 destination (데스틴에이션)정보가 없어 채팅 참여 인원수 카운팅을 할 수 없는 원인을 찾음 <br>
3. 해결 : 유저가 구독한 채팅방 정보와 유저 SessionId를 매핑하여 저장<br>
   - 유저가 채팅방을 구독한 경우와 연결을 끊었을 경우 Message Header(메시지 헤더)에서 어떤 정보를 return해주는지 로그를 남겼고 모두 SessionId(세션아이디)를 return 해주는 것을 확인했습니다.
   유저가 구독한 채팅방 정보와 SesstionId(세션아이디)를 매핑하여 저장하였고 퇴장 시 해당 SessionId(세션아이디)로 매핑해두었던 채팅방 정보를 조회하여 해당 키값에서 유저 카운팅을 했습니다.
 <img width="674" alt="스크린샷 2022-04-07 오후 7 18 01" src="https://user-images.githubusercontent.com/94433709/162177524-b763e658-2e3f-4410-a16d-0bf0f74e00b8.png">
 
 </details>
