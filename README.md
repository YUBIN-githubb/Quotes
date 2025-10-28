# 📖 Table of Contents

- [API 명세서](#API-명세서)
    - [Auth](#Auth)
        - [회원 가입](#회원-가입)
        - [로그인](#로그인)
    - [Quotes](#Quotes)
        - [인용 생성](#인용-생성)
        - [인용 다건 조회](#인용-다건-조회)
        - [인용 단건 조회](#인용-단건-조회)
        - [인용 수정](#인용-수정)
        - [인용 공개 여부 수정](#인용-공개-여부-수정)
        - [인용 삭제](#인용-삭제)
    - [Likes](#Likes)
        - [좋아요 생성](#좋아요-생성)
        - [좋아요 한 인용 조회](#좋아요-한-인용-조회)
        - [좋아요 삭제](#좋아요-삭제)
    - [Comments](#comments)
        - [댓글 생성](#댓글-생성)
        - [댓글 조회](#댓글-조회)
        - [댓글 수정](#댓글-수정)
        - [댓글 삭제](#댓글-삭제)
    - [Bookmarks](#bookmarks)
        - [북마크 생성](#북마크-생성)
        - [북마크 한 인용 조회](#북마크-한-인용-조회)
        - [북마크 삭제](#북마크-삭제)
    - [Users](#users)
        - [유저 조회](#유저-조회)
        - [유저 정보 업데이트](#유저-정보-업데이트)
        - [비밀번호 업데이트](#비밀번호-업데이트)
        - [회원 탈퇴](#회원-탈퇴)
    - [외부 API 요청](#외부-api-요청)
        - [카카오 도서 API 요청](#카카오-도서-api-요청)


# API 명세서
## Auth
### 회원 가입
```json
{
    "Description" : "회원가입",
    "HTTP method" : "POST",
    "URL" : "/signup",
    "request header" : {
        "Content-Type" : "application/json"
    },
    "request body" : {
        "email" : "String",
        "password" : "String",
        "userRole" : "Enum",
        "profileUrl" : "String",
        "nickname" : "String"
    },
    "response header" : "None",
    "response body" : {
        "email" : "String",
        "userRole" : "Enum",
        "profileUrl" : "String",
        "nickname" : "String"
    },
    "success code" : "200 OK",
    "error code" : "400 BAD REQUEST"
}
```

### 로그인
```json
{
    "Description" : "로그인",
    "HTTP method" : "POST",
    "URL" : "/signin",
    "request header" : {
        "Content-Type" : "application/json"
    },
    "request body" : {
        "email" : "String",
        "password" : "String"
    },
    "response header" : "None",
    "response body" : {
      "token" : "String"
    },
    "success code" : "200 OK",
    "error code" : "401 UNAUTHORIZED"
}
```

## Quotes
### 인용 생성
```json
{
    "Description" : "인용 생성",
    "HTTP method" : "POST",
    "URL" : "/quotes",
    "request header" : {
        "Content-Type" : "application/json",
        "Authorization" : "token"
    },
    "request body" : {
        "title" : "String",
        "author" : "String",
        "category" : "Enum",
        "pageNumber" : "Long",
        "sentence" : "String",
        "thought" : "String",
        "isPublic" : "Enum"
    },
    "response header" : "None",
    "response body" : {
        "userId" : "Long",
        "nickname" : "String",
        "title" : "String",
        "category" : "Enum",
        "pageNumber" : "Long",
        "sentence" : "String",
        "thought" : "String",
        "isPublic" : "Enum",
        "createdAt" : "LocalDateTime",
        "modifiedAt" : "LocalDateTime",
        "deletedAT" : "LocalDateTime"
    },
    "success code" : "200 OK",
    "error code" : "404 NOT FOUND"
}
```

### 인용 다건 조회
```json
{
    "Description" : "인용 다건 조회",
    "HTTP method" : "GET",
    "URL" : "/quotes",
    "request header" : {
        "Content-Type" : "application/json",
        "Authorization" : "token"
    },
    "request body" : "None",
    "response header" : "None",
    "response body" : {
        "content": [
          {
            "userId" : "Long",
            "nickname" : "String",
            "title" : "String",
            "category" : "Enum",
            "pageNumber" : "Long",
            "sentence" : "String",
            "thought" : "String",
            "isPublic" : "Enum",
            "createdAt" : "LocalDateTime",
            "modifiedAt" : "LocalDateTime",
            "deletedAT" : "LocalDateTime"
          }
        ],
        "size": "int",
        "page": "int",
        "totalElements": "long",
        "totalPages": "int"
    },
    "success code" : "200 OK",
    "error code" : "None"
}
```

### 인용 단건 조회
```json
{
    "Description" : "인용 단건 조회",
    "HTTP method" : "GET",
    "URL" : "/quotes/{quoteId}",
    "request header" : {
        "Content-Type" : "application/json",
        "Authorization" : "token"
    },
    "request body" : "None",
    "response header" : "None",
    "response body" : {
        "userId" : "Long",
        "nickname" : "String",
        "title" : "String",
        "category" : "Enum",
        "pageNumber" : "Long",
        "sentence" : "String",
        "thought" : "String",
        "isPublic" : "Enum",
        "createdAt" : "LocalDateTime",
        "modifiedAt" : "LocalDateTime",
        "deletedAT" : "LocalDateTime"
    },
    "success code" : "200 OK",
    "error code" : "404 NOT FOUND"
}
```

### 인용 수정
```json
{
    "Description" : "인용 수정",
    "HTTP method" : "PUT",
    "URL" : "/quotes/{quoteId}",
    "request header" : {
        "Content-Type" : "application/json",
        "Authorization" : "token"
    },
    "request body" : {
        "title": "String",
        "author": "String",
        "category": "Enum",
        "pageNumber": "Long",
        "sentence": "String",
        "thought": "String",
        "isPublic": "Enum"
    },
    "response header" : "None",
    "response body" : {
        "userId" : "Long",
        "nickname" : "String",
        "title" : "String",
        "category" : "Enum",
        "pageNumber" : "Long",
        "sentence" : "String",
        "thought" : "String",
        "isPublic" : "Enum",
        "createdAt" : "LocalDateTime",
        "modifiedAt" : "LocalDateTime",
        "deletedAT" : "LocalDateTime"
    },
    "success code" : "200 OK",
    "error code" : ["404 NOT FOUND", "401 UNAUTHORIZED"]
}
```

### 인용 공개 여부 수정
```json
{
    "Description" : "인용 단건 조회",
    "HTTP method" : "PATCH",
    "URL" : "/quotes/{quoteId}",
    "request header" : {
        "Content-Type" : "application/json",
        "Authorization" : "token"
    },
    "request body" : {
        "isPublic" : "Enum"
    },
    "response header" : "None",
    "response body" : "None",
    "success code" : "200 OK",
    "error code" : ["404 NOT FOUND", "401 UNAUTHORIZED"]
}
```

### 인용 삭제
```json
{
    "Description" : "인용 삭제",
    "HTTP method" : "DELETE",
    "URL" : "/quotes/{quoteId}",
    "request header" : {
        "Content-Type" : "application/json",
        "Authorization" : "token"
    },
    "request body" : "None",
    "response header" : "None",
    "response body" : "None",
    "success code" : "200 OK",
    "error code" : ["404 NOT FOUND", "401 UNAUTHORIZED"]
}
```

## Likes
### 좋아요 생성
```json
{
    "Description" : "좋아요 생성",
    "HTTP method" : "POST",
    "URL" : "/quotes/{quoteId}/likes",
    "request header" : {
        "Content-Type" : "application/json",
        "Authorization" : "token"
    },
    "request body" : "None",
    "response header" : "None",
    "response body" : "None",
    "success code" : "200 OK",
    "error code" : "400 BAD REQUEST"
}
```

### 좋아요 한 인용 조회
```json
{
    "Description" : "좋아요 한 인용 조회",
    "HTTP method" : "GET",
    "URL" : "/likes",
    "request header" : {
        "Content-Type" : "application/json",
        "Authorization" : "token",
        "page" : "int",
        "size" : "int"
    },
    "request body" : "None",
    "response header" : "None",
    "response body" : {
        "content": [
          {
            "quoteId" : "Long",
            "title" : "String",
            "category" : "Enum"
          }
        ],
        "size": "int",
        "page": "int",
        "totalElements": "long",
        "totalPages": "int"
    },
    "success code" : "200 OK",
    "error code" : "None"
}
```

### 좋아요 삭제
```json
{
    "Description" : "좋아요 삭제",
    "HTTP method" : "DELETE",
    "URL" : "/quotes/{quoteId}/likes/{likeId}",
    "request header" : {
        "Content-Type" : "application/json",
        "Authorization" : "token"
    },
    "request body" : "None",
    "response header" : "None",
    "response body" : "None",
    "success code" : "200 OK",
    "error code" : ["400 BAD REQUEST", "404 NOT FOUND"]
}
```

## Comments
### 댓글 생성
```json
{
    "Description" : "댓글 생성",
    "HTTP method" : "POST",
    "URL" : "/quotes/{quoteId}/comments",
    "request header" : {
        "Content-Type" : "application/json",
        "Authorization" : "token"
    },
    "request body" : {
        "contents" : "String"
    },
    "response header" : "None",
    "response body" : {
        "id" : "Long",
        "userId" : "Long",
        "nickname" : "String",
        "profileUrl" : "String",
        "contents" : "String",
        "createdAt" : "LocalDateTime",
        "modifiedAt" : "LocalDateTime",
        "deletedAT" : "LocalDateTime"
    },
    "success code" : "200 OK",
    "error code" : "404 NOT FOUND"
}
```

### 댓글 조회
```json
{
    "Description" : "댓글 조회",
    "HTTP method" : "GET",
    "URL" : "/quotes/{quoteId}/comments",
    "request header" : {
        "Content-Type" : "application/json",
        "Authorization" : "token",
        "page" : "int",
        "size" : "int"
    },
    "request body" : "None",
    "response header" : "None",
    "response body" : {
    "content": [
          {
            "id" : "Long",
            "userId" : "Long",
            "nickname" : "String",
            "profileUrl" : "String",
            "contents" : "String",
            "createdAt" : "LocalDateTime",
            "modifiedAt" : "LocalDateTime",
            "deletedAT" : "LocalDateTime"
          }
        ],
        "size": "int",
        "page": "int",
        "totalElements": "long",
        "totalPages": "int"
    },
    "success code" : "200 OK",
    "error code" : "None"
}
```

### 댓글 수정
```json
{
    "Description" : "댓글 수정",
    "HTTP method" : "PATCH",
    "URL" : "/quotes/{quoteId}/comments/{commentId}",
    "request header" : {
        "Content-Type" : "application/json",
        "Authorization" : "token"
    },
    "request body" : {
        "contents" : "String"
    },
    "response header" : "None",
    "response body" : {
        "id" : "Long",
        "userId" : "Long",
        "nickname" : "String",
        "profileUrl" : "String",
        "contents" : "String",
        "createdAt" : "LocalDateTime",
        "modifiedAt" : "LocalDateTime",
        "deletedAT" : "LocalDateTime"
    },
    "success code" : "200 OK",
    "error code" : "404 NOT FOUND"  
}
```

### 댓글 삭제
```json
{
    "Description" : "댓글 삭제",
    "HTTP method" : "DELETE",
    "URL" : "/quotes/{quoteId}/comments/{commentId}",
    "request header" : {
        "Content-Type" : "application/json",
        "Authorization" : "token"
    },
    "request body" : "None",
    "response header" : "None",
    "response body" : "None",
    "success code" : "200 OK",
    "error code" : ["404 NOT FOUND", "403 FORBIDDEN"]
}
```

## Bookmarks
### 북마크 생성
```json
{
    "Description" : "북마크 생성",
    "HTTP method" : "POST",
    "URL" : "/quotes/{quoteId}/bookmarks",
    "request header" : {
        "Content-Type" : "application/json",
        "Authorization" : "token"
    },
    "request body" : "None",
    "response header" : "None",
    "response body" : {
        "id" : "Long",
        "quoteId" : "Long",
        "title" : "String",
        "category" : "Enum"
    },
    "success code" : "200 OK",
    "error code" : "400 BAD REQUEST"
}
```

### 북마크 한 인용 조회
```json
{
    "Description" : "북마크 한 인용 조회",
    "HTTP method" : "GET",
    "URL" : "/bookmarks",
    "request header" : {
        "Content-Type" : "application/json",
        "Authorization" : "token",
        "page" : "int",
        "size" : "int"
    },
    "request body" : "None",
    "response header" : "None",
    "response body" : {
        "content": [
          {
            "id": "Long",
            "quoteId": "Long",
            "title": "String",
            "category": "Enum"
          }
        ],
        "size": "int",
        "page": "int",
        "totalElements": "long",
        "totalPages": "int"
    },
    "success code" : "200 OK",
    "error code" : "None"
}
```

### 북마크 삭제
```json
{
    "Description" : "북마크 삭제",
    "HTTP method" : "DELETE",
    "URL" : "/quotes/{quoteId}/bookmarks/{bookmarkId}",
    "request header" : {
        "Content-Type" : "application/json",
        "Authorization" : "token"
    },
    "request body" : "None",
    "response header" : "None",
    "response body" : "None",
    "success code" : "200 OK",
    "error code" : ["400 BAD REQUEST", "404 NOT FOUND"]
}
```

## Users
### 유저 조회
```json
{
    "Description" : "유저 조회",
    "HTTP method" : "GET",
    "URL" : "/users",
    "request header" : {
        "Content-Type" : "application/json",
        "Authorization" : "token"
    },
    "request body" : "None",
    "response header" : "None",
    "response body" : {
        "email" : "String",
        "userRole" : "Enum",
        "profileUrl" : "String",
        "nickname" : "String"
    },
    "success code" : "200 OK",
    "error code" : "404 NOT FOUND"
}
```

### 유저 정보 업데이트
```json
{
    "Description" : "유저 정보 업데이트",
    "HTTP method" : "PUT",
    "URL" : "/users",
    "request header" : {
        "Content-Type" : "application/json",
        "Authorization" : "token"
    },
    "request body" : {
        "profileUrl" : "String",
        "nickname" : "String"
    },
    "response header" : "None",
    "response body" : {
        "email" : "String",
        "userRole" : "Enum",
        "profileUrl" : "String",
        "nickname" : "String"
    },
    "success code" : "200 OK",
    "error code" : "404 NOT FOUND"
}
```

### 비밀번호 업데이트
```json
{
    "Description" : "비밀번호 업데이트",
    "HTTP method" : "PATCH",
    "URL" : "/users",
    "request header" : {
        "Content-Type" : "application/json",
        "Authorization" : "token"
    },
    "request body" : {
        "oldPassword" : "String",
        "newPassword" : "String"
    },
    "response header" : "None",
    "response body" : "None",
    "success code" : "200 OK",
    "error code" : ["404 NOT FOUND", "401 UNAUTHORIZED"]
}
```

### 회원 탈퇴
```json
{
    "Description" : "회원 탈퇴",
    "HTTP method" : "DELETE",
    "URL" : "/users",
    "request header" : {
        "Content-Type" : "application/json",
        "Authorization" : "token"
    },
    "request body" : {
        "password" : "String"
    },
    "response header" : "None",
    "response body" : "None",
    "success code" : "200 OK",
    "error code" : ["404 NOT FOUND", "401 UNAUTHORIZED"]
}
```

## 외부 API 요청
### 카카오 도서 API 요청
```json
{
    "Description" : "카카오북 API 요청",
    "HTTP method" : "POST",
    "URL" : "/books",
    "request header" : {
        "Content-Type" : "application/json",
        "query" : "String",
        "page" : "int"
    },
    "request body" : "None",
    "response header" : "None",
    "response body" : {
        "meta": {
            "is_end": "boolean",
            "pageable_count": "int",
            "total_count": "int"
        },
        "documents": [
          {
            "title": "String",
            "authors": [
              "String"
            ],
            "publisher": "String",
            "isbn": "String",
            "thumbnail": "String"
          }
        ]
    },
    "success code" : "200 OK",
    "error code" : "400 BAD REQUEST"
}
```
