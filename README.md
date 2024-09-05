# SNS 기본 기능 구현 프로젝트

## 개요

입력 바랍니다 

## 와이어프레임

프로젝트의 뉴스피드 기능에 대한 와이어프레임은 아래 링크를 통해 확인할 수 있습니다:

[뉴스피드 와이어 프레임 (draw.io)](https://drive.google.com/file/d/1FhLqA07bWjnv6lhmOAaUphb6l33Kne6L/view?usp=sharing)

## API 설계

### 유저 관리

| 메서드 | 설명             | 엔드포인트           | 요청 헤더                             | 요청 바디                                    | 응답 예시                                |
|--------|------------------|----------------------|----------------------------------------|----------------------------------------------|-----------------------------------------|
| POST   | 회원가입         | /api/users           | 없음                                   | `{ "email": "", "password": "" }`            | `{ "code": 200, "message": "정상 처리되었습니다.", "data": { "email": "", "createdAt": "" } }` |
| POST   | 로그인           | /api/users/login     | 없음                                   | `{ "email": "", "password": "" }`            | `{ "code": 200, "message": "정상 처리되었습니다.", "data": { "token": "" } }`                 |
| PATCH  | 유저 수정        | /api/users/{id}      | `Authorization: Bearer <token>`        | `{ "oldPassword": "", "newPassword": "" }`   | `{ "code": 200, "message": "정상 처리되었습니다.", "data": null }`                           |
| GET    | 유저 단건 조회   | /api/users/{id}      | `Authorization: Bearer <token>`        | 없음                                         | `{ "code": 200, "message": "정상 처리되었습니다.", "data": { "email": "", "createdAt": "" } }`|
| DELETE | 회원 탈퇴        | /api/users/{id}      | `Authorization: Bearer <token>`        | `{ "password": "" }`                         | `{ "code": 200, "message": "정상 처리되었습니다.", "data": null }`                           |

### 게시물 관리

| 메서드 | 설명             | 엔드포인트           | 요청 헤더                             | 요청 바디                                    | 응답 예시                                |
|--------|------------------|----------------------|----------------------------------------|--------------------------------------------------------|-----------------------------------------|
| POST   | 게시물 등록       | /api/post            | `Authorization: Bearer <token>`        | `{ "title": "", "contents": "" }`                       | `{ "code": 200, "message": "정상 처리되었습니다.", "data": { "title": "", "contents": "", "email": "", "createdAt": "" } }` |
| GET    | 게시물 목록 조회   | /api/post/feed       | `Authorization: Bearer <token>`        | 없음 (쿼리 파라미터: `pageNumber`, `pageSize`, `sort`)   | `{ "code": 200, "message": "정상 처리되었습니다.", "data": { ... } }` (페이지네이션 데이터 포함) |
| GET    | 게시물 단건 조회   | /api/post/{postId}   | `Authorization: Bearer <token>`        | 없음                                                    | `{ "code": 200, "message": "정상 처리되었습니다.", "data": { "title": "", "contents": "", "email": "", "createdAt": "", "updatedAt": "" } }` |
| PATCH  | 게시물 수정       | /api/post/{postId}   | `Authorization: Bearer <token>`        | `{ "title": "", "contents": "" }`                       | `{ "code": 200, "message": "정상 처리되었습니다.", "data": { "title": "", "contents": "", "email": "", "createdAt": "", "updatedAt": "" } }` |
| DELETE | 게시물 삭제       | /api/post/{postId}   | `Authorization: Bearer <token>`        | 없음                                                    | `{ "code": 200, "message": "정상 처리되었습니다.", "data": null }` |

### 댓글 관리

| 메서드 | 설명             | 엔드포인트           | 요청 헤더                             | 요청 바디                                    | 응답 예시                                |
|--------|------------------|----------------------|----------------------------------------|----------------------------------------------|-----------------------------------------|
| POST   | 댓글 등록         | /api/comments        | `Authorization: Bearer <token>`        | `{ "postId": "", "contents": "" }`           | `{ "code": 200, "message": "정상 처리되었습니다.", "data": { "postId": "", "email": "", "contents": "" } }` |
| PATCH  | 댓글 수정         | /api/comments/{id}   | `Authorization: Bearer <token>`        | `{ "contents": "" }`                         | `{ "code": 200, "message": "정상 처리되었습니다.", "data": { "postId": "", "email": "", "contents": "" } }` |
| DELETE | 댓글 삭제         | /api/comments/{id}   | `Authorization: Bearer <token>`        | 없음                                         | `{ "code": 200, "message": "정상 처리되었습니다.", "data": null }` |

### 팔로우 관리

| 메서드 | 설명             | 엔드포인트           | 요청 헤더                             | 요청 바디                                    | 응답 예시                                |
|--------|------------------|----------------------|----------------------------------------|----------------------------------------------|-----------------------------------------|
| POST   | 팔로우            | /api/following       | `Authorization: Bearer <token>`        | `{ "followingId": "" }`                      | `{ "code": 200, "message": "정상 처리되었습니다.", "data": null }`                           |
| DELETE | 언팔로우          | /api/following       | `Authorization: Bearer <token>`        | `{ "followingId": "" }`                      | `{ "code": 200, "message": "정상 처리되었습니다.", "data": null }`                           |
| GET    | 팔로워 목록 조회   | /api/followers       | `Authorization: Bearer <token>`        | 없음                                         | `{ "code": 200, "message": "정상 처리되었습니다.", "data": [ { "followerEmail": "" } ] }`   |
| GET    | 팔로잉 목록 조회   | /api/following       | `Authorization: Bearer <token>`        | 없음                                         | `{ "code": 200, "message": "정상 처리되었습니다.", "data": [ { "followingEmail": "" } ] }`  |

## ERD

![SNS.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/83c75a39-3aba-4ba4-a792-7aefe4b07895/28ffe5ac-e678-4f71-8ce1-cafedf9fe487/44220f79-12b4-4e4f-8e7c-3d8f604b3eb2.png)

