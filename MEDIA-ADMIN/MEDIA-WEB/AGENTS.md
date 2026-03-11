# Git 提交规范

本项目遵循 [Conventional Commits 1.0.0](https://www.conventionalcommits.org/zh-hans/v1.0.0/) 规范。

## 提交信息格式

```
<类型>[可选 范围]: <描述>

[可选 正文]

[可选 脚注]
```

## 提交类型

### 核心类型

| 类型 | 说明 | 语义化版本影响 |
|------|------|---------------|
| `feat` | 新功能 | MINOR |
| `fix` | 修复 Bug | PATCH |
| `BREAKING CHANGE` | 破坏性变更 | MAJOR |

### 其他推荐类型

| 类型 | 说明 |
|------|------|
| `build` | 修改项目构建系统，例如修改依赖库、外部接口或者升级 Node 版本等 |
| `chore` | 对非业务性代码进行修改，例如修改构建流程或者工具配置等 |
| `ci` | 修改持续集成流程，例如修改 Travis、Jenkins 等工作流配置 |
| `docs` | 修改文档，例如修改 README 文件、API 文档等 |
| `style` | 修改代码的样式，例如调整缩进、空格、空行等 |
| `refactor` | 重构代码，例如修改代码结构、变量名、函数名等但不修改功能逻辑 |
| `perf` | 优化性能，例如提升代码的性能、减少内存占用等 |
| `test` | 修改测试用例，例如添加、删除、修改代码的测试用例等 |

## 规范要求

### 必须遵守

1. 每个提交都必须使用类型字段前缀（如 `feat` 或 `fix`）
2. 类型后接可选的范围字段、可选的 `!`、必要的冒号和空格
3. 新功能必须使用 `feat` 类型
4. Bug 修复必须使用 `fix` 类型
5. 描述字段必须直接跟在冒号和空格之后
6. 破坏性变更必须在提交信息中标记

### 可选项

- **范围**：用圆括号包围，例如 `feat(parser):`
- **正文**：在描述后空一行开始，提供额外上下文
- **脚注**：在正文后空一行开始，格式为 `令牌: 值` 或 `令牌 #值`

## 示例

### 基础示例

```bash
# 新功能
feat: 添加用户登录功能

# Bug 修复
fix: 修复用户头像上传失败的问题

# 文档更新
docs: 更新 API 接口文档

# 代码重构
refactor: 优化数据库查询逻辑

# 性能优化
perf: 减少首页加载时间

# 测试相关
test: 添加用户模块单元测试
```

### 带范围的示例

```bash
# 指定影响范围
feat(auth): 添加 OAuth2.0 登录支持
fix(api): 修复用户列表接口分页错误
docs(readme): 更新安装说明
```

### 破坏性变更示例

```bash
# 使用 ! 标记
feat!: 重构用户认证系统

# 使用 BREAKING CHANGE 脚注
feat: 升级 API 版本

BREAKING CHANGE: API v1 接口已废弃，请迁移至 v2
```

### 完整示例

```bash
feat(user): 添加用户权限管理功能

- 新增角色管理模块
- 实现基于 RBAC 的权限控制
- 添加权限验证中间件

BREAKING CHANGE: 用户 API 返回结构已变更

Closes #123
Reviewed-by: 张三
```

## 最佳实践

### 描述编写

- ✅ 使用简体中文
- ✅ 使用祈使句，例如"添加"而不是"添加了"
- ✅ 首字母小写
- ✅ 结尾不加句号
- ✅ 简洁明了，不超过 50 个字符

```bash
# 好的示例
feat: 添加用户导出功能
fix: 修复登录超时问题

# 不好的示例
feat: 添加了用户导出功能。
Fix: 修复了一个登录超时的Bug...
```

### 提交粒度

- 每次提交只做一件事
- 提交应该是原子性的
- 避免混合不相关的修改

### 何时使用不同类型

**feat vs fix**
- `feat`: 新增功能或增强现有功能
- `fix`: 修复缺陷或错误

**refactor vs feat/fix**
- `refactor`: 不改变外部行为的代码改进
- `feat/fix`: 改变外部行为的修改

**style vs refactor**
- `style`: 不影响代码逻辑的格式调整
- `refactor`: 改变代码结构但不改变功能

## 工具集成

### commitlint 配置

推荐使用 [@commitlint/config-conventional](https://github.com/conventional-changelog/commitlint) 进行提交信息校验。

```json
{
  "extends": ["@commitlint/config-conventional"]
}
```

### 自动生成 CHANGELOG

可以使用以下工具基于提交信息自动生成变更日志：

- [standard-version](https://github.com/conventional-changelog/standard-version)
- [semantic-release](https://github.com/semantic-release/semantic-release)
- [conventional-changelog](https://github.com/conventional-changelog/conventional-changelog)

## 常见问题

### 提交类型选错了怎么办？

在合并或发布前，使用 `git rebase -i` 修改提交历史。

### 一个提交包含多种类型？

拆分为多个独立的提交，每个提交只做一件事。

### 破坏性变更如何处理？

- 在类型后添加 `!` 标记：`feat!: ...`
- 在脚注中添加 `BREAKING CHANGE:` 说明

## 参考资源

- [Conventional Commits 官方文档](https://www.conventionalcommits.org/zh-hans/v1.0.0/)
- [语义化版本规范](https://semver.org/lang/zh-CN/)
- [Angular 提交规范](https://github.com/angular/angular/blob/master/CONTRIBUTING.md#commit)
