# MEDIA-ADMIN 后端接口协议

## 基础信息

- **认证方式**: Bearer Token (Header: `Authorization: Bearer <token>`)
- **响应格式**: JSON
- **统一响应结构**:

```json
{
  "code": 200,
  "message": "操作成功",
  "data": { ... }
}
```

---

## 统一响应结构

```typescript
interface ApiResponse<T> {
  code: number      // 200-成功, 其他-失败
  message: string   // 响应消息
  data: T           // 响应数据
}
```

---

## 分页响应结构

```typescript
interface PaginatedResponse<T> {
  records: T[]      // 数据列表
  current: number   // 当前页码
  size: number      // 每页条数
  total: number     // 总条数
}
```

---

## 状态码说明

| code | 说明 |
|------|------|
| 200 | 成功 |
| 400 | 参数错误 |
| 401 | 未授权 |
| 403 | 无权限 |
| 404 | 资源不存在 |
| 500 | 服务器错误 |

---

# 一、媒体应用管理 API

**Base URL**: `/api/media/app`

## 1.1 分页查询应用列表

**请求**

```http
GET /api/media/app/list
```

**Query 参数**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| current | number | 是 | 当前页码 |
| size | number | 是 | 每页条数 |
| mediaId | number | 否 | 媒体ID |
| name | string | 否 | 应用名称（模糊搜索） |
| osType | number | 否 | 操作系统类型：1-Android 2-iOS |
| enable | number | 否 | 状态：0-禁用 1-正常 2-审核中 3-拒绝 |

**响应**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "records": [
      {
        "id": 1,
        "mediaId": 100,
        "name": "测试应用",
        "osType": 1,
        "osTypeName": "Android",
        "accessType": 2,
        "accessTypeName": "SDK",
        "pkg": "com.example.app",
        "downloadUrl": "https://...",
        "enable": 1,
        "enableName": "正常",
        "createTime": "2024-01-01T12:00:00",
        "updateTime": "2024-01-01T12:00:00",
        "remark": "备注"
      }
    ],
    "current": 1,
    "size": 10,
    "total": 100
  }
}
```

---

## 1.2 根据ID查询应用

**请求**

```http
GET /api/media/app/{id}
```

**路径参数**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | number | 是 | 应用ID |

**响应**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "mediaId": 100,
    "name": "测试应用",
    "osType": 1,
    "osTypeName": "Android",
    "accessType": 2,
    "accessTypeName": "SDK",
    "pkg": "com.example.app",
    "downloadUrl": "https://...",
    "enable": 1,
    "enableName": "正常",
    "createTime": "2024-01-01T12:00:00",
    "updateTime": "2024-01-01T12:00:00",
    "remark": "备注"
  }
}
```

---

## 1.3 创建应用

**请求**

```http
POST /api/media/app/create
Content-Type: application/json
```

**请求体**

```json
{
  "mediaId": 100,
  "name": "测试应用",
  "osType": 1,
  "accessType": 2,
  "pkg": "com.example.app",
  "downloadUrl": "https://...",
  "remark": "备注"
}
```

**响应**

```json
{
  "code": 200,
  "message": "创建成功",
  "data": {
    "id": 101,
    "name": "测试应用"
  }
}
```

---

## 1.4 更新应用

**请求**

```http
POST /api/media/app/update
Content-Type: application/json
```

**请求体**

```json
{
  "id": 101,
  "name": "新应用名称",
  "osType": 2,
  "accessType": 1,
  "pkg": "com.example.new",
  "downloadUrl": "https://...",
  "enable": 1,
  "remark": "新备注"
}
```

**响应**

```json
{
  "code": 200,
  "message": "更新成功",
  "data": null
}
```

---

## 1.5 删除应用

**请求**

```http
POST /api/media/app/delete/{id}
```

**路径参数**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | number | 是 | 应用ID |

**响应**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": null
}
```

---

## 1.6 批量删除应用

**请求**

```http
POST /api/media/app/batchDelete
Content-Type: application/json
```

**请求体**

```json
[1, 2, 3]
```

**响应**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": null
}
```

---

## 1.7 根据媒体ID查询应用列表

**请求**

```http
GET /api/media/app/list/{mediaId}
```

**路径参数**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| mediaId | number | 是 | 媒体ID |

**响应**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "name": "应用1"
    }
  ]
}
```

---

# 二、广告位管理 API

**Base URL**: `/api/media/slot`

## 2.1 分页查询广告位列表

**请求**

```http
GET /api/media/slot/list
```

**Query 参数**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| current | number | 是 | 当前页码 |
| size | number | 是 | 每页条数 |
| mediaId | number | 否 | 媒体ID |
| appId | number | 否 | 应用ID |
| name | string | 否 | 广告位名称（模糊搜索） |
| enable | number | 否 | 状态：0-禁用 1-正常 2-审核中 3-拒绝 |

**响应**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "records": [
      {
        "id": 1,
        "mediaId": 100,
        "appId": 200,
        "name": "开屏广告",
        "nameAlise": "开屏",
        "adSceneId": 1,
        "adTypeId": 1,
        "adSizeId": 1,
        "sspPayType": 1,
        "sspPayTypeName": "分成",
        "sspDealRatio": 0.5,
        "width": 1080,
        "height": 1920,
        "adImage": "https://...",
        "interactionType": 7,
        "interactionTypeName": "打开网页 Deeplink 直接下载",
        "enable": 1,
        "enableName": "正常",
        "createTime": "2024-01-01T12:00:00",
        "updateTime": "2024-01-01T12:00:00",
        "remark": "备注"
      }
    ],
    "current": 1,
    "size": 10,
    "total": 100
  }
}
```

---

## 2.2 根据ID查询广告位

**请求**

```http
GET /api/media/slot/{id}
```

**路径参数**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | number | 是 | 广告位ID |

**响应**: 同 2.1 单条记录格式

---

## 2.3 创建广告位

**请求**

```http
POST /api/media/slot/create
Content-Type: application/json
```

**请求体**

```json
{
  "mediaId": 100,
  "appId": 200,
  "name": "开屏广告",
  "nameAlise": "开屏",
  "adSceneId": 1,
  "adTypeId": 1,
  "adSizeId": 1,
  "sspPayType": 1,
  "sspDealRatio": 0.5,
  "width": 1080,
  "height": 1920,
  "adImage": "https://...",
  "interactionType": 7,
  "remark": "备注"
}
```

**响应**

```json
{
  "code": 200,
  "message": "创建成功",
  "data": { ... }
}
```

---

## 2.4 更新广告位

**请求**

```http
POST /api/media/slot/update
Content-Type: application/json
```

**请求体**: 包含 `id` 字段和需要更新的字段

**响应**

```json
{
  "code": 200,
  "message": "更新成功",
  "data": null
}
```

---

## 2.5 删除广告位

**请求**

```http
POST /api/media/slot/delete/{id}
```

---

## 2.6 批量删除广告位

**请求**

```http
POST /api/media/slot/batchDelete
Content-Type: application/json
```

**请求体**: `[1, 2, 3]`

---

## 2.7 根据应用ID查询广告位列表

**请求**

```http
GET /api/media/slot/list/app/{appId}
```

---

## 2.8 根据媒体ID查询广告位列表

**请求**

```http
GET /api/media/slot/list/media/{mediaId}
```

---

# 三、广告位数据查询 API

**Base URL**: `/api/media/data`

## 3.1 分页查询广告位数据

**请求**

```http
GET /api/media/data/list
```

**Query 参数**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| current | number | 是 | 当前页码 |
| size | number | 是 | 每页条数 |
| mediaId | number | 否 | 媒体ID |
| appId | number | 否 | 应用ID |
| sspSlotId | number | 否 | 广告位ID |

**响应**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "records": [ ... ],
    "current": 1,
    "size": 10,
    "total": 100
  }
}
```

---

## 3.2 根据ID查询广告位数据

**请求**

```http
GET /api/media/data/{id}
```

---

## 3.3 按月份查询数据

**请求**

```http
GET /api/media/data/month/{month}
```

**路径参数**

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| month | string | 是 | 月份，格式：yyyy-MM |

---

## 3.4 按月份查询日表数据

**请求**

```http
GET /api/media/data/day/{month}?mediaId=&appId=&sspSlotId=
```

---

## 3.5 按月份查询小时表数据

**请求**

```http
GET /api/media/data/hour/{month}?mediaId=&appId=&sspSlotId=
```

---

## 3.6 根据广告位ID查询数据列表

**请求**

```http
GET /api/media/data/list/slot/{sspSlotId}
```

---

## 3.7 根据应用ID查询数据列表

**请求**

```http
GET /api/media/data/list/app/{appId}
```

---

## 3.8 根据媒体ID查询数据列表

**请求**

```http
GET /api/media/data/list/media/{mediaId}
```

---

# 四、媒体认证 API

**Base URL**: `/api/media/auth`

## 4.1 媒体用户注册

**请求**

```http
POST /api/media/auth/register
Content-Type: application/json
```

**请求体**

```json
{
  "account": "user@example.com",
  "password": "password123",
  "name": "媒体名称",
  "contact": "联系人",
  "phone": "13800138000"
}
```

---

## 4.2 媒体用户登录

**请求**

```http
POST /api/media/auth/login
Content-Type: application/json
```

**请求体**

```json
{
  "account": "user@example.com",
  "password": "password123"
}
```

**响应**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "mediaId": 100
  }
}
```

---

## 4.3 获取媒体用户信息

**请求**

```http
GET /api/media/auth/info?id={mediaId}
```

---

## 4.4 验证Token

**请求**

```http
GET /api/media/auth/validate
Authorization: Bearer <token>
```

---

## 4.5 登出

**请求**

```http
POST /api/media/auth/logout
```

---

# 五、枚举值说明

## 应用状态 (enable)

| 值 | 说明 |
|------|------|
| 0 | 禁用 |
| 1 | 正常 |
| 2 | 审核中 |
| 3 | 拒绝 |

## 操作系统类型 (osType)

| 值 | 说明 |
|------|------|
| 1 | Android |
| 2 | iOS |

## 接入方式 (accessType)

| 值 | 说明 |
|------|------|
| 1 | API |
| 2 | SDK |

## 结算方式 (sspPayType)

| 值 | 说明 |
|------|------|
| 1 | 分成 |
| 2 | RTB |

## 交互类型 (interactionType) - 位掩码

| 值 | 说明 |
|------|------|
| 1 | 打开网页 |
| 2 | Deeplink |
| 4 | 直接下载 |
| 8 | 广点通 |
| 16 | 小程序 |
| 32 | 应用商店 |
| 64 | 快应用 |

---

# 六、实现状态

| 模块 | 接口 | 状态 |
|------|------|------|
| 应用管理 | GET /api/media/app/list | ✅ 已实现 |
| 应用管理 | GET /api/media/app/{id} | ✅ 已实现 |
| 应用管理 | POST /api/media/app/create | ✅ 已实现 |
| 应用管理 | POST /api/media/app/update | ✅ 已实现 |
| 应用管理 | POST /api/media/app/delete/{id} | ✅ 已实现 |
| 应用管理 | POST /api/media/app/batchDelete | ✅ 已实现 |
| 应用管理 | GET /api/media/app/list/{mediaId} | ✅ 已实现 |
| 广告位管理 | GET /api/media/slot/list | ✅ 已实现 |
| 广告位管理 | GET /api/media/slot/{id} | ✅ 已实现 |
| 广告位管理 | POST /api/media/slot/create | ✅ 已实现 |
| 广告位管理 | POST /api/media/slot/update | ✅ 已实现 |
| 广告位管理 | POST /api/media/slot/delete/{id} | ✅ 已实现 |
| 广告位管理 | POST /api/media/slot/batchDelete | ✅ 已实现 |
| 广告位管理 | GET /api/media/slot/list/app/{appId} | ✅ 已实现 |
| 广告位管理 | GET /api/media/slot/list/media/{mediaId} | ✅ 已实现 |
| 数据查询 | GET /api/media/data/list | ✅ 已实现 |
| 数据查询 | GET /api/media/data/{id} | ✅ 已实现 |
| 数据查询 | GET /api/media/data/month/{month} | ✅ 已实现 |
| 数据查询 | GET /api/media/data/day/{month} | ✅ 已实现 |
| 数据查询 | GET /api/media/data/hour/{month} | ✅ 已实现 |
| 媒体认证 | POST /api/media/auth/register | ✅ 已实现 |
| 媒体认证 | POST /api/media/auth/login | ✅ 已实现 |
| 媒体认证 | GET /api/media/auth/info | ✅ 已实现 |
| 媒体认证 | GET /api/media/auth/validate | ✅ 已实现 |
| 媒体认证 | POST /api/media/auth/logout | ✅ 已实现 |
