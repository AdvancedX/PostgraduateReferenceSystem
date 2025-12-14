# 研究生辅助报考系统 (Postgraduate Reference System)

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.5.15-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-1.8-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-Multi--Module-red.svg)](https://maven.apache.org/)

## 📖 项目简介

研究生辅助报考系统（PGS）是一个基于 Spring Boot 的综合性研究生报考辅助平台。系统旨在帮助考生进行院校、专业查询，分数预测，并提供智能化的报考建议。系统集成了机器学习算法进行分数预测，并提供博客论坛功能供用户交流。

## ✨ 主要功能

### 核心功能模块

1. **院校信息管理** (`school`)
   - 全国研究生招生院校信息查询
   - 院校详细信息展示
   - 院校对比分析

2. **专业信息管理** (`major`)
   - 各院校招生专业信息
   - 专业详情查询
   - 专业排名展示

3. **历年分数查询** (`score`, `score_2022`)
   - 历年录取分数线查询
   - 分数趋势分析
   - 多维度分数数据展示

4. **智能分数预测** (`presc`, `linerpr`)
   - 基于历史数据的分数预测
   - 线性回归算法预测
   - Transformer 深度学习模型预测（Python 服务）
   - 个性化录取概率分析

5. **AI 智能问答** (`ai-chat`)
   - 集成 DeepSeek API 的智能对话功能
   - 考研相关问题咨询
   - 智能报考建议

6. **博客论坛** (`blogs`)
   - 用户经验分享
   - 考研心得交流
   - 富文本编辑支持

7. **系统管理** (`system`)
   - 用户权限管理
   - 角色管理
   - 菜单管理
   - 系统配置

8. **定时任务** (`quartz`)
   - 数据自动更新
   - 定时任务调度

9. **代码生成器** (`generator`)
   - 快速生成 CRUD 代码
   - 自定义模板支持

## 🛠️ 技术栈

### 后端技术

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 2.5.15 | 核心框架 |
| Apache Shiro | 1.12.0 | 权限控制 |
| MyBatis | - | ORM 框架 |
| Druid | 1.2.16 | 数据库连接池 |
| PageHelper | 1.4.6 | 分页插件 |
| Swagger | 3.0.0 | API 文档 |
| Quartz | - | 定时任务 |
| FastJSON | 1.2.83 | JSON 处理 |
| Apache POI | 4.1.2 | Excel 操作 |
| Velocity | 2.3 | 模板引擎 |
| Thymeleaf | - | 视图模板 |

### 前端技术

- HTML5 / CSS3
- JavaScript
- jQuery
- Bootstrap
- Thymeleaf 模板引擎

### AI/ML 技术

- **Python Flask** - 提供深度学习预测服务
- **PyTorch** - 深度学习框架
- **Transformer** - 时序预测模型
- **Scikit-learn** - 数据预处理
- **DeepSeek API** - AI 对话服务

### 数据库

- MySQL 9.0+

## 📦 项目结构

```
PostgraduateReferenceSystem/
├── admin/              # 主应用模块（启动入口）
├── framework/          # 核心框架模块
├── system/             # 系统管理模块
├── common/             # 通用工具模块
├── school/             # 院校信息模块
├── major/              # 专业信息模块
├── score/              # 历年分数模块
├── score_2022/         # 2022年详细分数模块
├── presc/              # 分数预测模块
├── linerpr/            # 线性回归预测模块
├── ai-chat/            # AI 智能对话模块
├── blogs/              # 博客论坛模块
├── quartz/             # 定时任务模块
├── generator/          # 代码生成器模块
├── pyweb/              # Python 深度学习预测服务
│   ├── pyweb.py       # Flask Web 服务
│   └── final_transformer_model.pth  # 训练好的模型
├── sql/                # 数据库脚本
│   └── PGS.sql        # 数据库初始化脚本
├── bin/                # 编译产物目录
├── 训练数据/           # 机器学习训练数据
├── pom.xml             # Maven 父项目配置
├── ry.sh               # Linux 启动脚本
└── ry.bat              # Windows 启动脚本
```

## 🚀 快速开始

### 环境要求

- **JDK**: 1.8+
- **Maven**: 3.6+
- **MySQL**: 9.0+ (或其他兼容版本)
- **Python**: 3.7+ (如需使用深度学习预测功能)

### 安装步骤

#### 1. 克隆项目

```bash
git clone https://github.com/AdvancedX/PostgraduateReferenceSystem.git
cd PostgraduateReferenceSystem
```

#### 2. 创建数据库

```bash
# 创建数据库
mysql -u root -p

CREATE DATABASE pgs CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 导入数据
USE pgs;
SOURCE sql/PGS.sql;
```

#### 3. 修改配置文件

编辑 `admin/src/main/resources/application.yml`:

```yaml
# 修改文件上传路径（根据实际情况调整）
pgs:
  profile: D:/pgs/uploadPath  # Windows
  # profile: /home/pgs/uploadPath  # Linux

# 修改 DeepSeek API Key（如需使用 AI 功能）
ds:
  key: your-deepseek-api-key
  url: https://api.deepseek.com/chat/completions
```

编辑 `admin/src/main/resources/application-druid.yml`:

```yaml
spring:
  datasource:
    druid:
      master:
        url: jdbc:mysql://localhost:3306/pgs?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
        username: root
        password: your_password
```

#### 4. 编译项目

```bash
# 使用 Maven 编译
mvn clean package -DskipTests

# 编译完成后，在 admin/target 目录下会生成 pgs-admin.jar
```

#### 5. 启动服务

**方式一：使用脚本启动（推荐）**

```bash
# Linux/Mac
chmod +x ry.sh
./ry.sh start

# Windows
ry.bat start
```

**方式二：直接运行 JAR**

```bash
cd admin/target
java -jar pgs-admin.jar
```

**方式三：IDEA 启动**

直接运行 `admin` 模块的 `com.pgs.PGSApplication` 主类

#### 6. 启动 Python 预测服务（可选）

如需使用深度学习分数预测功能：

```bash
cd pyweb

# 安装依赖
pip install flask flask-cors torch numpy pandas scikit-learn

# 启动服务（默认端口 5000）
python pyweb.py
```

#### 7. 访问系统

打开浏览器访问：

- 系统首页: http://localhost
- 默认端口: 80
- Swagger API 文档: http://localhost/swagger-ui/index.html

**默认账号**:
- 管理员: admin / admin123
- 普通用户: 请通过注册功能创建

## 📝 使用说明

### 管理员功能

1. **系统管理**
   - 用户管理：创建、编辑、删除用户
   - 角色管理：配置角色权限
   - 菜单管理：动态菜单配置
   - 系统配置：系统参数设置

2. **数据管理**
   - 院校信息：维护院校数据
   - 专业信息：维护专业数据
   - 分数管理：录取分数线维护

3. **代码生成**
   - 选择数据表
   - 配置生成参数
   - 一键生成 CRUD 代码

### 用户功能

1. **信息查询**
   - 搜索院校信息
   - 查询专业详情
   - 历年分数查询

2. **智能预测**
   - 输入个人信息
   - 选择目标院校/专业
   - 获取录取概率预测

3. **AI 咨询**
   - 在线智能问答
   - 获取报考建议

4. **社区交流**
   - 发布经验分享
   - 浏览其他用户博客
   - 互动交流

## 🔧 系统配置

### 端口配置

修改 `application.yml`:

```yaml
server:
  port: 80  # 修改为需要的端口
```

### 数据库配置

修改 `application-druid.yml` 中的数据库连接信息

### Shiro 权限配置

在 `application.yml` 中配置：

```yaml
shiro:
  user:
    loginUrl: /login
    unauthorizedUrl: /unauth
    captchaEnabled: false  # 验证码开关
```

### 文件上传配置

```yaml
spring:
  servlet:
    multipart:
      max-file-size: 10MB      # 单个文件大小
      max-request-size: 20MB   # 总上传大小
```

## 📊 API 文档

系统集成 Swagger3 自动生成 API 文档。

访问地址: http://localhost/swagger-ui/index.html

可以在 `application.yml` 中控制是否启用：

```yaml
swagger:
  enabled: true  # false 关闭 Swagger
```

## 🔐 安全说明

1. **默认密码修改**: 部署前务必修改默认管理员密码
2. **API Key 保护**: 不要将 DeepSeek API Key 提交到公共仓库
3. **数据库安全**: 使用强密码，限制远程访问
4. **XSS 防护**: 系统已配置 XSS 过滤器
5. **CSRF 保护**: Shiro 提供 CSRF 保护

## 🐛 常见问题

### 1. 启动失败

- 检查 JDK 版本是否为 1.8+
- 检查数据库连接配置
- 检查端口是否被占用

### 2. 数据库连接失败

- 确认 MySQL 服务已启动
- 检查数据库用户名密码
- 确认数据库已创建并导入数据

### 3. 文件上传失败

- 检查 `pgs.profile` 配置的目录是否存在
- 确认目录有写入权限

### 4. Python 预测服务无法访问

- 确认 Python 服务已启动
- 检查 5000 端口是否被占用
- 确认模型文件 `final_transformer_model.pth` 存在

## 📄 许可证

本项目采用 [MIT License](LICENSE) 开源协议。

## 👥 贡献

欢迎提交 Issue 和 Pull Request！

## 📧 联系方式

- 项目主页: https://www.pgs.vip
- GitHub: https://github.com/AdvancedX/PostgraduateReferenceSystem
- 作者: AdvancedX

## 🙏 致谢
- 本项目基于ruoyi框架进行二次开发
- 官方网站：https://ruoyi.vip/
感谢所有为本项目做出贡献的开发者！

---

**注意**: 本系统仅供学习交流使用，预测结果仅供参考，实际报考请以官方数据为准。
