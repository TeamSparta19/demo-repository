# SNS 기본 기능 구현 프로젝트


## 📝 **프로젝트 개요**

이 프로젝트는 게시글 CRUD, 페이지네이션, 그리고 팔로우 기능을 중심으로 사용자가 원활하게 소셜 피드를 관리하고 소통할 수 있도록 설계된 웹 애플리케이션입니다. 다양한 기능을 효과적으로 구현하기 위해 다양한 디자인 패턴과 기술을 사용하였으며, 아래와 같은 핵심 기능들을 구현하였습니다.

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

![SNS](https://github.com/user-attachments/assets/e042c37f-be64-43ef-89fd-922fa33872cb)

### 프로젝트 README (마크다운 정리)


---

## 🎯 **트러블슈팅**

### 1. **Lazy Loading 문제**
- **문제**: Lazy Loading이 제대로 작동하지 않아 데이터를 조회하는 데 문제가 발생하였습니다.
- **해결 방법**: `@Transactional` 어노테이션과 `open-in-view=true` 설정을 통해 뷰에서 Lazy Loading이 가능하도록 하였습니다.
- **생각**: 성능 저하가 발생할 수 있다는 점에서 이 설정은 신중히 사용해야 합니다.

### 2. **DTO 남발 문제**
- **문제**: DTO를 과도하게 사용하면서, 불필요한 정보가 포함되거나 연관성 문제가 발생했습니다.
- **해결 방법**: 각 DTO의 역할을 명확하게 하고, 필요한 데이터만을 담도록 수정했습니다.

---

## ✅ **장점과 단점**

### **장점**
- **효율적인 데이터 처리**: 페이지네이션을 사용하여 대량의 데이터를 처리할 수 있도록 설계.
- **유연한 설계**: DTO를 통해 필요한 데이터만 주고받을 수 있어 시스템의 확장성 증가.

### **단점**
- **Lazy Loading 문제**: 설정에 따른 성능 저하 가능성이 있어 적절한 설정이 필요.
- **DTO 남발**: 처음엔 DTO 남발로 인해 코드의 복잡성이 증가했으나, 이를 해결하고 구조를 단순화함.

---

## 📚 **배운 점**

- **협업의 중요성**: 코드 리뷰와 원활한 소통이 프로젝트의 성패를 좌우한다는 것을 배웠습니다.
- **트랜잭션 관리**: 트랜잭션의 범위와 Lazy Loading의 상호작용을 잘 이해하는 것이 중요함을 깨달았습니다.
- **API 설계와 문서화**: 명확한 문서화가 사용자와 개발자 모두에게 유익하다는 것을 실감했습니다.

---

## 💡 **향후 계획**

- **DTO 사용의 최적화**: 필요한 정보만을 DTO에 담아 불필요한 데이터의 전송을 최소화할 계획입니다.
- **트랜잭션 관리 개선**: 트랜잭션의 범위와 성능을 고려한 트랜잭션 관리를 강화할 것입니다.

---

