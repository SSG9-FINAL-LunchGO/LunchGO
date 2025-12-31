# 런치고 (LunchGo) 프로젝트 기획

## 📋 프로젝트 개요

런치고는 **법인카드 기반 팀 점심 예약 및 추천 서비스**입니다.
복잡한 팀 점심 예약 과정을 간소화하고, 개인화된 식당 추천을 통해 직장인들의 점심시간을 효율적으로 관리할 수 있는 웹 애플리케이션입니다.

**프로젝트 성격**: Spring Boot + Vue.js 부트캠프 최종 프로젝트

## 🎯 기획 배경

### 1. 메뉴 결정 장애

법인카드 한도와 팀원 식성을 동시에 만족시키는 식당 찾기의 어려움

### 2. 짧은 점심시간

웨이팅과 조리 대기로 실제 휴식 시간 단축

### 3. 노쇼 문제

점심 피크타임 노쇼로 인한 식당의 치명적 매출 손실

### 4. 초개인화 니즈

날씨, 예산, 구내식당 메뉴 등 구체적 상황에 맞는 추천 부재

## 💡 런치고의 솔루션

### 1. 정보 수집의 어려움 → 앱 내 팀 설정 기능

팀장이 메뉴 제한, 가격대, 인원수를 미리 입력하면 모든 팀원이 동일한 기준으로 검색 가능

### 2. 추천의 한계 → 지도 기반, 초개인화 식당 리스트

회사 위치 기반 강력한 필터링과 회식 맞춤 식당으로 새로운 식당 발견

### 3. 전화 확인의 번거로움 → 실시간 예약 가능 여부 확인

앱에서 즉시 예약 가능 여부, 주차 공간, 룸 정보 확인 가능

### 4. 승인 프로세스의 반복 → 원클릭 예약 확정

팀장 승인 후 자동으로 예약 진행, 중복 커뮤니케이션 제거

### 5. 예약 확정의 불안정성 → 메뉴 선주문 및 자동 정산

예약 시 메뉴를 미리 선택하고 선결제하여 현장에서 즉시 식사 가능, 영수증 자동 정산

## 🚀 핵심 기능

### 1. 예산 기반 추천

개인, 법인카드 한도에 맞춘 가격 대별 필터링

### 2. 태그 매칭

개인 및 팀원의 선호도를 지정하여 식당 매칭

### 3. 선 결제/선주문

착석 즉시 식사를 통해 점심시간 확보/가맹점 회전율 상승

### 4. 구내식당 대체 추천

구내식당 메뉴를 조회해 팀원이 좋아할만한 식당 추천

## ✅ 구현된 기능 요약 (백엔드 중심)

- 추천/검색
  - 트렌딩 식당 추천 API (최근 n일 통계 스코어링 + 신규성 가중치)
  - 대표 이미지/식당 태그/리뷰 태그 상위 3개 포함
  - Kakao 지도 기반 검색 API 연동
  - 날씨 데이터 API 연동 추천(예정)
  - 추천 고도화(법인카드 한도 매핑, 개인/팀 취향 태그 유사도)(예정)
- 통계/집계
  - 식당 상세 조회수 누적(중복 조회 필터)
  - 결제 완료 예약 확정수 누적(트랜잭션 after-commit)
  - Redis Hash 기반 비동기 집계 + 배치 플러시
- 예약/결제
  - 결제 완료/실패/만료/웹훅 처리 및 예약 상태 업데이트
  - 선주문/선결제 기능 고도화(예정)
  - 예약 동시성 제어 강화 및 부하 테스트 수행(예정)
- 리뷰/콘텐츠
  - 리뷰/댓글 CRUD 및 태그 통계 제공
  - 악성 리뷰 블라인드 요청 및 관리자 처리
  - 네이버 OCR 기반 영수증 리뷰 및 구내식당 메뉴 추천(예정)
- 회원/사업자
  - 사업자 권한 인증 API 기반 인증 모듈 구현
  - 사용자/사업자 이메일 인증 기능 구현
  - 사업자 프로모션 기능 지원
  - Spring Security 기반 인증/인가(예정)
  - 사용자 알림/리마인더 기능(예정)

## ✅ 프론트 구현 현황/화면 목록

- 홈
  - 트렌딩 식당 리스트 노출 및 카드/스켈레톤 컴포넌트
- 인증/소개
  - 로그인, 회원가입, 서비스 소개
- 관리자
  - 대시보드, 회원 관리, 점주 승인, 예약 관리, 리뷰 관리, 정산 관리, 프랜차이즈 관리
- 마이페이지
  - 즐겨찾기, 내 예약 목록
- 식당
  - 상세 페이지, 예약, 메뉴 선택, 결제, 예약 확인/요약, 리뷰 목록/상세
  - Kakao 지도 기반 검색/상세 UI
- 파트너/사업자
  - 파트너 입점, 사업자 대시보드/예약 관리/식당 정보 관리/직원 관리

## 🎬 사용 시나리오

### 시나리오 1: 까다로운 팀원의 취향을 반영한 추천

**상황**: 팀원 중 한 명은 소고기를 못 먹고, 다른 한 명은 해산물 알러지가 있음

**기존 방식의 문제**: 팀장이 일일이 전화로 메뉴를 확인하고 식당을 찾아야 함 (평균 1-2시간 소요)

**런치고의 솔루션**: 앱에서 팀원의 불호 메뉴를 입력하면, 자동으로 해당 메뉴를 피한 식당만 추천

**결과**:

- 5분 만에 모든 팀원이 만족하는 식당 3곳 추천
- 팀원 간 의견 충돌 없음
- 예약 확정까지 15분 이내

### 시나리오 2: 구내 식당 메뉴가 좋지 않은 날의 긴급 추천

**상황**: 수요일 구내 식당 메뉴가 반복되는 메뉴로 팀원들이 외부 식당을 원함

**기존 방식의 문제**: 갑자기 식당을 찾아야 하고, 시간이 부족 → 점심 시간이 압박되어 선택지 제한

**런치고의 솔루션**: 구내식당 메뉴를 OCR로 자동 인식하고, 자동으로 다른 메뉴의 식당 추천

**결과**:

- 아침 10시에 앱을 켜면 이미 추천 식당 목록이 준비됨
- 구내식당과 다른 메뉴의 식당만 필터링
- 점심 시간 최적화로 빠게 예약 가능

### 시나리오 3: 팀장 출장 중 팀원끼리 회식하는 경우

**상황**: 팀장이 출장 중이지만 팀원들이 회식을 하고 싶음

**기존 방식의 문제**: 팀장 승인을 기다려야 하고, 법인카드 사용 불가 → 회식 계획이 미뤄지거나 개인 카드로 결제해야 함

**런치고의 솔루션**: 팀원이 직접 예약하고, 등록된 법인카드로 선결제 가능 (팀장 사전 승인 설정)

**결과**:

- 팀장 없이도 자유롭게 회식 및 결제 가능
- 영수증 자동 정산으로 번거로움 제거
- 팀장은 나중에 영수증만 확인하면 됨

## 🎁 기대 효과

### 1. 시간 절약

메뉴 고민부터 식사까지 병목 시간 제거

### 2. 예산 최적화

법인카드 한도 내 최적의 선택

### 3. 노쇼 방어

예약금 제도로 식당 매출 보호

---

## 🛠 기술 스택

### Backend

- **Framework**: Spring Boot
- **Database**: MySQL, Redis
- **ORM**: JPA, MyBatis
- **Container**: Docker
- **Build Tool**: Gradle

### Frontend

- **Framework**: Vue.js 3.5.25 (Composition API)
- **Build Tool**: Vite 5.2.0
- **Styling**: Tailwind CSS 4.1.18
- **State Management**: Pinia 3.0.4
- **Routing**: Vue Router 4.6.3
- **HTTP Client**: Axios 1.4.0
- **Icons**: Lucide Vue Next 0.561.0
- **UI Components**: Custom components (Button, Card, Input, Modal)

### Infrastructure & External APIs

- **Cloud Platform**: Naver Cloud Platform
- **OCR**: Naver Clova OCR
- **Payment**: PortOne
- **Map**: Kakao Map API

### Development Tools

- **Version Control**: Git, GitHub
- **Code Quality**: Commitlint, Husky
- **Database Tool**: MySQL Workbench
- **IDE**: IntelliJ IDEA, VS Code

## 🎯 특화 전략

- 최근 7일 트렌드 스코어링, 개인/팀원 선호도 반영 추천
- 네이버 OCR 연동 구내식당 메뉴 회피 추천
- 기상청 API 연동 날씨 가중치

## 📅 개발 일정

### Phase 1: 기획 및 멘토링 (12월 4일 - 12월 12일)

- [ ] 기획 및 1차 멘토링

### Phase 2: 프론트엔드 개발 (12월 13일 - 12월 20일)

- [ ] 프론트엔드 개발 및 단위테스트

### Phase 3: 백엔드 개발 (12월 21일 - 1월 4일)

- [ ] 백엔드 개발 및 단위테스트

### Phase 4: MVP 통합 테스트 (1월 4일 - 1월 6일)

- [ ] MVP 1차 통합 테스트

### Phase 5: 추가 기능 개발 (1월 7일 - 1월 15일)

- [ ] 2차 멘토링, 추가 기능 개발

### Phase 6: 배포 준비 (1월 16일 - 1월 21일)

- [ ] 배포 안정화 및 발표 준비

### Phase 7: 최종 발표 (1월 22일)

- [ ] 최종 발표

## 👥 팀 구성

| 이름              | 역할                                     |
| ----------------- | ---------------------------------------- |
| **박기웅 (팀장)** | 추천 알고리즘 담당, 리뷰 모듈, 일정 관리 |
| **강우혁**        | 식당 정보 관리 모듈, 노션 관리           |
| **김다혜**        | 예약·결제 모듈, 동시성 제어              |
| **이현빈**        | 식당 정보 관리 모듈, ERD 관리            |
| **전예원**        | 회원 관리, 보안·권한, GitHub 관리        |

---

## 📁 프로젝트 구조

### 전체 디렉토리 구조

```
/webapp/
├── frontend/              # Vue3 프론트엔드
├── src/                   # Spring Boot 백엔드
├── gradle/                # Gradle 빌드 설정
├── .git/                  # Git 저장소
├── .husky/                # Git Hooks 설정
├── README.md              # 프로젝트 문서
├── package.json           # 루트 패키지 (commitlint, husky)
├── build.gradle           # Gradle 빌드 파일
└── settings.gradle        # Gradle 설정
```

### 프론트엔드 구조 (`/frontend`)

```
frontend/
├── src/
│   ├── views/                    # 페이지 컴포넌트
│   │   ├── HomeView.vue          # 메인 홈페이지
│   │   ├── AboutView.vue         # 소개 페이지
│   │   ├── intro/                # 서비스 소개
│   │   ├── login/                # 로그인
│   │   ├── signup/               # 회원가입
│   │   ├── my-reservations/      # 내 예약 목록
│   │   ├── mypage/               # 마이페이지
│   │   ├── partner/              # 파트너(식당) 관련
│   │   ├── restaurant/           # 식당 상세/예약/결제/리뷰
│   │   ├── business/             # 사업자 페이지
│   │   ├── admin/                # 관리자 페이지
│   │   └── staff/                # 직원 관리 페이지
│   │
│   ├── components/
│   │   └── ui/                   # UI 재사용 컴포넌트
│   │
│   ├── composables/              # Vue composables
│   ├── data/                     # 더미 데이터
│   ├── router/                   # Vue Router 설정
│   ├── stores/                   # Pinia 상태관리
│   ├── utils/                    # 유틸리티 함수
│   ├── assets/                   # 전역 스타일/이미지
│   ├── App.vue                   # 루트 컴포넌트
│   └── main.js                   # 앱 엔트리 포인트
│
├── public/                       # 정적 파일
│   └── images/                   # 이미지 파일
│
├── package.json                  # 프론트엔드 의존성
├── vite.config.js                # Vite 빌드 설정
├── tailwind.config.js            # Tailwind CSS 설정
├── postcss.config.js             # PostCSS 설정
└── index.html                    # HTML 엔트리
```

### 백엔드 구조 (`/src`)

```
src/
├── main/
│   ├── java/com/example/LunchGo/
│   │   ├── account/              # 계정 찾기/가입 보조 로직
│   │   ├── bookmark/             # 즐겨찾기
│   │   ├── cafeteria/            # 구내식당 메뉴/추천
│   │   ├── common/               # 공통 설정/유틸
│   │   ├── config/               # MyBatis 설정
│   │   ├── email/                # 이메일 인증/프로모션
│   │   ├── image/                # 이미지 업로드/오브젝트 스토리지
│   │   ├── member/               # 회원/사업자/직원
│   │   ├── reservation/          # 예약/결제
│   │   ├── restaurant/           # 식당/메뉴/통계
│   │   ├── review/               # 리뷰/댓글/영수증 OCR
│   │   ├── sms/                  # SMS 인증
│   │   ├── tag/                  # 태그/검색어
│   │   └── LunchGoApplication.java
│   └── resources/
│       ├── mapper/               # MyBatis XML 매퍼
│       ├── sql/                  # 테이블/마이그레이션/시드 SQL
│       ├── templates/            # 메일 템플릿
│       ├── application.yml
│       └── application.properties
└── test/
    └── java/com/example/LunchGo/ # 테스트 코드
```

### 주요 라우트

| 경로                                     | 컴포넌트                  | 설명                       |
| ---------------------------------------- | ------------------------- | -------------------------- |
| `/`                                      | HomeView                  | 메인 홈 (식당 추천 목록)   |
| `/intro`                                 | ServiceIntroPage          | 서비스 소개                |
| `/login`                                 | LoginPage                 | 로그인                     |
| `/signup`                                | SignupChoicePage          | 회원가입 유형 선택         |
| `/signup/user`                           | UserSignupPage            | 사용자 회원가입            |
| `/signup/owner`                          | OwnerSignupPage           | 사업자 회원가입            |
| `/mypage`                                | UserMyPage                | 마이페이지                 |
| `/business/mypage`                       | OwnerMyPage               | 사업자 마이페이지          |
| `/mypage/reviews`                        | ReviewsPage               | 내 리뷰 관리               |
| `/my-reservations`                       | MyReservationsPage        | 내 예약 목록               |
| `/my-reservations/:id/cancel`            | ReservationCancelView     | 예약 취소                  |
| `/partner`                               | PartnerPage               | 파트너 입점 문의           |
| `/restaurant/:id`                        | RestaurantDetailPage      | 식당 상세                  |
| `/restaurant/:id/booking`                | RestaurantBookingPage     | 예약하기                   |
| `/restaurant/:id/menu`                   | MenuSelectionPage         | 메뉴 선택                  |
| `/restaurant/:id/menus`                  | RestaurantMenusPage       | 메뉴 전체보기              |
| `/restaurant/:id/payment`                | RestaurantPaymentPage     | 결제                       |
| `/restaurant/:id/confirmation`           | RestaurantConfirmationPage | 예약 확인                  |
| `/restaurant/:id/summary`                | RestaurantSummaryPage     | 예약 요약                  |
| `/restaurant/:id/reviews`                | RestaurantReviewsPage     | 리뷰 목록                  |
| `/restaurant/:id/reviews/write`          | WriteReviewPage           | 리뷰 작성                  |
| `/restaurant/:id/reviews/:reviewId`      | ReviewDetailPage          | 리뷰 상세                  |
| `/restaurant/:id/reviews/:reviewId/edit` | WriteReviewPage           | 리뷰 수정                  |
| `/business/dashboard`                    | BusinessDashboardPage     | 사업자 대시보드            |
| `/business/reservations`                 | BusinessReservationsPage  | 사업자 예약 관리           |
| `/business/reservations/:id`             | ReservationDetailPage     | 예약 상세                  |
| `/business/restaurant-info/:id`          | RestaurantInfoPage        | 식당 정보                  |
| `/business/restaurant-info/:id/menus`    | MenusInfoPage             | 메뉴 리스트                |
| `/business/restaurant-info/add`          | RestaurantInfoEditPage    | 식당 등록                  |
| `/business/restaurant-info/edit/:id`     | RestaurantInfoEditPage    | 식당 정보 수정             |
| `/business/restaurant-info/menu/add`     | MenuEditPage              | 메뉴 추가                  |
| `/business/restaurant-info/menu/edit/:id` | MenuEditPage             | 메뉴 수정                  |
| `/business/staff`                        | BusinessStaffPage         | 직원 관리                  |
| `/business/reviews`                      | BusinessReviewsPage       | 리뷰 관리                  |
| `/business/promotion`                    | BusinessPromotionPage     | 프로모션 관리              |
| `/business/notifications`                | BusinessNotificationsPage | 방문/알림 관리             |
| `/admin/dashboard`                       | AdminDashboardPage        | 관리자 대시보드            |
| `/admin/reservations`                    | AdminReservationsPage     | 예약 관리                  |
| `/admin/franchises`                      | AdminFranchisesPage       | 프랜차이즈 관리            |
| `/admin/reviews`                         | AdminReviewsPage          | 리뷰 관리                  |
| `/admin/members`                         | AdminMembersPage          | 회원 관리                  |
| `/admin/finance`                         | AdminFinancePage          | 정산 관리                  |
| `/admin/owners`                          | AdminOwnerApprovalPage    | 사업자 승인                |
| `/staff/list`                            | StaffListPage             | 직원 리스트                |

### 디자인 시스템

#### 컬러 팔레트

- **Primary**: `#ff6b4a` (브랜드 산호색)
- **Primary Hover**: `#FF8A6D`
- **Title**: `#1e3a5f` (다크 블루)
- **Body Text**: `#495057` (회색)
- **Secondary Text**: `#6c757d` (연한 회색)
- **Border**: `#e9ecef`, `#dee2e6`
- **Background**: `#f8f9fa`

#### 주요 UI 컴포넌트

- **Button**: Variant 지원 (outline, default)
- **Card**: 식당 카드, 정보 표시
- **Modal**: 필터, 검색, 계정 찾기
- **Input**: 폼 입력

---

## 🧩 백엔드 주요 API 목록 (모듈별)

| 모듈 | 컨트롤러 | 설명 |
| ---- | -------- | ---- |
| account | AccountController | 아이디/비밀번호 찾기 등 계정 보조 기능 |
| bookmark | BookmarkController | 즐겨찾기 CRUD |
| cafeteria | CafeteriaMenuController | 구내식당 메뉴 조회/추천 |
| email | EmailController | 이메일 인증/프로모션 발송 |
| image | ImageUploadController | 이미지 업로드/프리사인 URL |
| member | MemberController | 회원/사업자/직원 관리 |
| reservation | ReservationController, ReservationPaymentController, PaymentController | 예약/결제 생성 및 웹훅 처리 |
| restaurant | BusinessRestaurantController, MenuController, RestaurantTrendController, BusinessRestaurantImageController, BusinessMenuImageController | 식당/메뉴 관리, 트렌딩 조회, 이미지 관리 |
| review | ReviewController, ReviewCommentController, ReviewBlindController, AdminReviewController, OcrController | 리뷰/댓글/블라인드/관리자 처리, OCR |
| sms | SmsController | SMS 인증 |
| tag | SearchTagController | 검색 태그 조회 |

---

## 🔀 Git 브랜치 전략

- `main`: 프로덕션 브랜치
- `dev`: 개발 메인 브랜치
- `feat/*`: 기능 개발 브랜치
- `design/*`: 디자인 작업 브랜치

### 현재 작업 브랜치

- `feat/reviewSelected`: 리뷰 선택 페이지 개발
