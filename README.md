# 😎 I am here 😎 

### 아이디어 구상

국내 거주 외국인을 매칭하여 커뮤니티를 형성하는 서비스

### 프로젝트 개요

프로젝트명 : Iamhere

일정 : 2023.04.11 ~ 2023.05.20(약 5주)

주요 기능

- 회원 관리 서비스 : Security 회원가입, 로그인, OAuth 2.0 (구글, 페이스북, 트위터, 카카오), 마이페이지
- 커뮤니티 게시판 서비스 : 게시글 및 댓글 CRUD, 조회수, 페이징 및 검색 처리
- 실시간 채팅 서비스 : 채팅방 입장 및 퇴장, 채팅방 리스트 조회, 알림, 신고 기능

사용 기술 

**백엔드**

- 개발 언어 : Java 17
- 개발 환경 : Spring Boot 3.0.4, Gradle, Junit5 Spring Security, OAuth, JPA, Lombok, Kafka
- 데이터베이스 : MySQL, MongoDB
- 형상 관리 툴 : Github
- 아키텍쳐: MSA
- 인프라 : AWS EC2, AWS Route53, Docker, Docker Hub, Jenkins

**프론트엔드**

- 개발 언어 : HTML/CSS3, JavaScript, Thymeleaf, Bootstrap

### 프로젝트 목표 및 일정 산정

**프로젝트 목표** 

- 클린 코드와 클린 아키텍쳐에 고민하면서 개발하기
- 개발 과정을 꼼꼼히 기록하기
- JenKins를 이용한 자동화 배포 관리
- DevOps 서비스 환경 관리 : 버전 관리, 성능 모니터링
- RESTful API를 만들기 위해 고민하고 개선하기
- MSA, Terraform, 컨테이너 오케스트레이션 경험

**프로젝트 일정**

- 4월 11일 - 18일 회원 가입 및 로그인 API 구현
- 4월 19일 - 4월 26일 게시판 및 댓글 API 구현
- 4월 27일 - 5월 7일 채팅 API 구현
- 5월 8일 - 20일 프론트엔드 구현 및 테스트, 최종 배포

### 요구사항 분석

- 회원 관리 마이크로 서비스
    
    **(1) 회원 가입**
    
    - 입력값 (유효성 검사)
        - **아이디** : 4 ~ 12자의 영문 대소문자와 숫자로만 입력 (중복 불가, 변경 불가)
        - **비밀번호** : 4 ~ 12자의 영문 대소문자와 숫자로만 입력, 비밀번호 확인
        - **이메일** : 이메일 형식 패턴 적용해 입력값 확인, 이메일 인증번호 확인 (중복 불가)
        - **내/외국인**  : 외국인에 체크 시 국적을 입력하도록 한다.
        - **여성/남성**
        - **생년월일** : 31/08/2001 과 같은 형식으로 입력 받는다.
        - **거주 지역** : 주소 API를 활용하여 우편번호 및 주소 찾기 기능
        - 한 줄 소개
    
    - 아이디/비밀번호 찾기
        - 아이디 찾기 : 이메일 인증 → 아이디의 일부를 노출함.
        - 비밀번호 찾기 : 이메일 인증 → 임시 비밀번호 발급
    
    **(2) 로그인**
    
    - 로그인을 하지 않은 경우 볼 수 있는 페이지
        - 메인 페이지
        - 커뮤니티 게시글 목록 조회 페이지
        - 커뮤니티 게시글 검색 페이지
        - 채팅 대상자 목록 조회 페이지
        - 회원가입 페이지/로그인 페이지
        - 로그인을 하지 않은 사용자가 이외 페이지에 접근하려 할 때 경고창을 띄우고 로그인 페이지로 이동
    - 로그인 검사
        - 아이디 혹은 비밀번호가 일치하지 않으면 “아이디 또는 비밀번호가 일치하지 않습니다” 경고창 띄우기
        - 아이디와 비밀번호가 일치하면 메인 페이지로 이동하기
        - 로그인 상태 유지 기능
    
    **(3) 소셜 로그인**
    
    - 구글, 페이스북, 트위터, 네이버, 카카오 소셜 로그인 연동 기능 제공
    - 인증 및 약관동의와 필수 제공 항목 (이메일, 성별, 생년월일)과 회원 정보(내/외국인, 거주 지역) 입력으로 회원가입 완료
    - 이미 가입되어있는 사용자가 소셜 로그인을 시도하는 경우, 두 계정이 자동 연동된다.
    
    **(4) 마이페이지**
    
    - 회원 정보 수정
        - 이메일 인증번호 확인 후 이메일 수정 가능 (중복 불가)
        - 비밀 번호 수정 가능 (비밀번호 확인 절차)
        - 거주 지역 수정 가능
        - 국적 수정 가능
        - 한 줄 소개 수정 가능
        - 한 줄 소개 제외 빈칸이 있으면 수정이 불가능하며, 수정 완료 시 수정 날짜를 업데이트한다.
    - 내가 쓴 글, 댓글 조회
        - 게시글 조회 : 내가 쓴 글의 목록 조회 및 상세 조회 (댓글 포함)
        - 댓글 조회 : 내가 쓴 댓글의 목록 조회, 댓글을 쓴 게시글의 상세 조회 (댓글 포함)


### 와이어프레임

- (1) 회원 관리 페이지
    - 회원가입
        
    <img width="516" alt="image" src="https://user-images.githubusercontent.com/67851124/226255129-0bcd2e7a-69d0-4a23-a3f4-aee5c0a3b8b3.png">

    - 로그인
    
<img width="606" alt="image" src="https://user-images.githubusercontent.com/67851124/226255160-8e0146f2-5301-4919-8998-f586df7e35b4.png">    


### 데이터베이스 설계

- (1) 회원 관리 데이터베이스 설계
    
    관계형 데이터베이스 (MariaDB) 사용
    
    **USER**
    
    <img width="600" alt="image" src="https://user-images.githubusercontent.com/67851124/226254544-81cae8bf-deba-4ea5-b0db-078890b6da94.png">

    

### API 설계

- (1) 회원 관리 API 설계
    
    **USER**
    
    <img width="800" alt="image" src="https://user-images.githubusercontent.com/67851124/226254458-94f29671-0110-4873-a155-c0d27fa39463.png">
