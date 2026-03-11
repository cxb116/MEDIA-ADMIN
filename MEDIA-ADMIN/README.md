# MEDIA-ADMIN

Media Admin Project for Database Operations

## 技术栈

- JDK 11
- Spring Boot 2.3.12.RELEASE
- Spring Data JPA
- MySQL 8.0
- Lombok
- Maven

## 项目结构

```
MEDIA-ADMIN/
├── src/
│   ├── main/
│   │   ├── java/com/media/admin/
│   │   │   ├── entity/          # 实体类
│   │   │   ├── repository/      # 数据访问层
│   │   │   ├── service/         # 业务逻辑层
│   │   │   ├── controller/      # 控制器层
│   │   │   └── MediaAdminApplication.java
│   │   └── resources/
│   │       └── application.yml  # 配置文件
│   └── test/
├── pom.xml
└── README.md
```

## 快速开始

### 1. 配置数据库

在 `src/main/resources/application.yml` 中修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/media_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: your_password
```

### 2. 创建数据库

```sql
CREATE DATABASE media_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. 运行项目

```bash
mvn spring-boot:run
```

或者在IDE中运行 `MediaAdminApplication` 主类。

### 4. 访问API

项目启动后，访问 `http://localhost:8080/api/users` 进行测试。

## API 接口

### 用户管理接口

- `GET /api/users` - 查询所有用户
- `GET /api/users/{id}` - 根据ID查询用户
- `GET /api/users/username/{username}` - 根据用户名查询
- `POST /api/users` - 创建用户
- `PUT /api/users/{id}` - 更新用户
- `DELETE /api/users/{id}` - 删除用户

## 示例代码

项目中已包含完整的用户管理示例代码，包括：

- **Entity**: `User.java` - 用户实体类
- **Repository**: `UserRepository.java` - 用户数据访问层
- **Service**: `UserService.java` - 用户业务逻辑层
- **Controller**: `UserController.java` - 用户控制器

可以参考这些示例代码来实现其他实体的数据库操作。

## 开发说明

1. 添加新的实体类到 `entity` 包
2. 创建对应的 Repository 接口继承 `JpaRepository`
3. 创建 Service 类实现业务逻辑
4. 创建 Controller 类提供REST API

## 许可证

MIT License
