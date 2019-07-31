# kakaopay_assignment

## 주택금융 공급현황 분석 서비스

    - 각 금융기관 데이터를 통한 API 개발
        - 데이터 업로드 API
        - 은행 목록 출력 API
        - 년도별 지원금액 합계 출력 API
        - 년도별 지원금액중 가장 큰 금액을 출력 API
        - 전체 년도중 지원금액 평균이 가장 큰값과 가장 작은값 출력 API
        - 특정 은행의 특정 달에 대한 금액 예측 API
    - 추가 API
        - JWT 인증 토큰(signup / signin) 
        
    - 개발 관련 스펙
        - Spring Boot : 2.1.6.RELEASE
        - Kotlin : 1.3.31
        - Postgresql : 9.5
        - Mybatis : 3.5.2
        - io.jsonwebtoken:jjwt : 0.9.1
        
        etc
        - mokito-kotlin : 2.1.0 + with Junit : 4.12
        - jenkins 서버(정적 분석 및 Code Coverage 측정 지표활용)
            - jacoco
            - ktlint
    

     
## API Spec

### 1. 데이터 업로드
 - 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API 
 - POST /api/v1/uploadHistory 
 - RequestBody : file={file.csv} 
 - Response : 
 ```json
202 Accepted
{
    "message": "KA2021",
    "code": "ACCEPTED"
}
```

### 2. 은행목록
 - 주택금융 공급 금융기관(은행) 목록을 출력하는 API 
 - GET /api/v1/banks 
 - RequestParam : - 
 - Response : 
```json
200 OK
{
    "bankList": [
        {
            "bankCode": "01",
            "bankName": "주택도시기금"
        },
        {
            "bankCode": "02",
            "bankName": "국민은행"
        },
//...
        {
            "bankCode": "08",
            "bankName": "외환은행"
        },
        {
            "bankCode": "09",
            "bankName": "기타은행"
        }
    ],
    "message": "KA2001",
    "code": "SUCCESS"
}
```


### 3. 년도별 지원금액 합계 
- 년도별 각 금융기관의 지원금액 합계를 출력하는 API 
- GET /api/v1/statistics/summarize 
- RequestParam : -
- Response : 
```json
200 OK
{
    "name": "주택금융 공급현황",
    "items": [
        {
            "year": "2005 년",
            "total_amount": 46640,
            "detail_amount": [
                {
                    "name": "국민은행",
                    "amount": 13231
                },
                {
                    "name": "농협은행/수협은행",
                    "amount": 1486
                },
//...
                {
                    "name": "하나은행",
                    "amount": 3122
                },
                {
                    "name": "한국시티은행",
                    "amount": 704
                }
            ]
        },
        {
            "year": "2017 년",
            "total_amount": 316658,
            "detail_amount": [
                {
                    "name": "국민은행",
                    "amount": 40109
                },
//...
                {
                    "name": "한국시티은행",
                    "amount": 7
                }
            ]
        }
    ]
}
```

### 4. 년도별 지원금액중 Max 
- 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API 
- GET /api/v1/statistics/largest 
- RequestParam : code={bankCode} 
- Response : 
```json
200 OK
{
    "year": 2016,
    "bank": "하나은행"
}
```

### 5. 전체 년도중 지원금액 평균의 MIN MAX 값
- 전체 년도(2005~2016)에서 지원금액 평균 중 가장 작은 금액과 큰 금액을 출력하는 API 
- GET /api/v1/statistics/minmax 
- RequestParam : code={bankCode} 
- Response :
```json
200 OK
{
    "bank": "신한은행",
    "support_amount": [
        {
            "year": 2006,
            "amount": 99
        },
        {
            "year": 2017,
            "amount": 4215
        }
    ]
}
```


### 6. 특정은행의 특정달에 대한 금액 예측 
- 특정 은행의 특정 달에 대해서 2018년도 해당 달에 금융지원 금액을 예측하는 API 
- GET /api/v1/prediction 
- RequestParam : code={bankCode}&month={month}
- Response : 
```json
200 OK
{
    "bankName": "농협은행/수협은행",
    "year": 2018,
    "month": 12,
    "amount": 1371,
    "message": "KA2001",
    "code": "SUCEESS"
}
```


### 7. Sign Up 
- signup 계정생성 API: 입력으로 ID, PW 받아 내부 DB에 계정 저장하고 토큰 생성하여 출력 
- POST /public/signup 
- RequestBody : 
```json
{
  "username":"{username}",
  "password":"{password}"
}
```
- Response: 
```json
200 OK
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJxd2VydHcyIiwiZXhwIjoxNTY0NjA1MzI3LCJpYXQiOjE1NjQ1ODczMjd9.Z_e3UQHrmMfycVXgm3rX_Z1l4qfaui6_M-UdOc-THQZizvx3a66InkwhIpu3X9DahtT4skHuPAZSuVwZQdW8Jw",
    "message": "KA2001",
    "code": "SUCEESS"
}
```


### 8. Sign In 
- signin 로그인 API: 입력으로 생성된 계정 (ID, PW)으로 로그인 요청하면 토큰을 발급 
- POST /public/signin 
- RequestBody : 
```json
{
  "username":"{username}",
  "password":"{password}"
}
```
- Response:
``` 
200 OK
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJxd2VydHciLCJleHAiOjE1NjQ2MDM3NDksImlhdCI6MTU2NDU4NTc0OX0.q0hMBv1SQDhsJhd-wAejyKPKXq0nZxfbFAZ86asRu1SnQpXcKY1hPkg8T_54BN6JfZeqSNB8a6KS7ABj8zQazQ",
    "responseType": "KAKAO_SUCCESS",
    "message": "KA2001",
    "code": "SUCEESS"
}
```

#### Details
--- 
7번 API에서는 동일한 username이 있는 경우 Exception 발생
```json
{
    "message": "KA4001",
    "code": "BAD_REQUEST"
}
```
---
 
8번 API에서는 ID와 PW가 맞을 경우 refresh token과 같은 효과.

--- 
1~6번 API에서는 7번이나 8번에서 발급받은 토큰을 이용하여 Header에 Authorization 필드에 Bearer 토큰으로 추가.
```json
{
  "Authorization": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJxd2VydHciLCJleHAiOjE1NjQ2MDM3NDksImlhdCI6MTU2NDU4NTc0OX0.q0hMBv1SQDhsJhd-wAejyKPKXq0nZxfbFAZ86asRu1SnQpXcKY1hPkg8T_54BN6JfZeqSNB8a6KS7ABj8zQazQ"
} 
```


## DB Spec

### Postgresql 

#### info

 - userId : kakaoapp
 - password : !q2w3e4r
 - databases : kakaofinance
 - schema : kakao_finance_be
 - table 
    - tasur_bnk (은행 관리 테이블)
    - tasur_hist (결제금액 관리 테이블)
    - tasur_usr (유저 관리 테이블)
        

#### schema

- database
```postgresql
CREATE DATABASE kakaofinance
    WITH 
    OWNER = kakaoapp
    ENCODING = 'UTF8'
    LC_COLLATE = 'C.UTF-8'
    LC_CTYPE = 'C.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
```

- schema
```postgresql
CREATE SCHEMA kakao_finance_be
    AUTHORIZATION kakaoapp;
```

- table
```postgresql
CREATE TABLE kakao_finance_be.tasur_bnk
(
    id integer NOT NULL DEFAULT nextval('kakao_finance_be.tasur_bnk_id_seq'::regclass),
    bnk_cd character(2) COLLATE pg_catalog."default" NOT NULL,
    bnk_nm character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tasur_bnk_pk PRIMARY KEY (id),
    CONSTRAINT tasur_bank_uq01 UNIQUE (bnk_cd)

)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE kakao_finance_be.tasur_bnk
    OWNER to kakaoapp;
```
```postgresql
CREATE TABLE kakao_finance_be.tasur_hist
(
    id integer NOT NULL DEFAULT nextval('kakao_finance_be.tasur_hist_id_seq'::regclass),
    year smallint NOT NULL,
    month smallint NOT NULL,
    bank_type character(2) COLLATE pg_catalog."default" NOT NULL,
    asur_price integer NOT NULL,
    CONSTRAINT "TASURE_HIST_PK" PRIMARY KEY (id),
    CONSTRAINT tasure_hist_uq01 UNIQUE (year, month, bank_type)

)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE kakao_finance_be.tasur_hist
    OWNER to kakaoapp;
```
```postgresql
CREATE TABLE kakao_finance_be.tasur_usr
(
    user_id character varying(22) COLLATE pg_catalog."default" NOT NULL,
    enc_password character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tasur_usr_pkey PRIMARY KEY (user_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE kakao_finance_be.tasur_usr
    OWNER to kakaoapp;
```

- sequence
```postgresql
CREATE SEQUENCE kakao_finance_be.tasur_bnk_id_seq
    INCREMENT 1
    START 9
    MINVALUE 1
    MAXVALUE 100000000
    CACHE 1;

ALTER SEQUENCE kakao_finance_be.tasur_bnk_id_seq
    OWNER TO postgres;
```
```postgresql
CREATE SEQUENCE kakao_finance_be.tasur_hist_id_seq
    INCREMENT 1
    START 13702
    MINVALUE 0
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE kakao_finance_be.tasur_hist_id_seq
    OWNER TO kakaoapp;
```