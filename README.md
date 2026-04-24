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

如需部署，请查看根目录下的 `部署指南.md`。

### 快速提示

- 部署前先准备好 JDK、Maven、MySQL
- `ai-chat` 的 DeepSeek 聊天功能需要自行申请 `ds.key`
- Python 预测服务为可选项

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


## 📄 许可证

本项目采用 [MIT License](LICENSE) 开源协议。

## 👥 贡献

欢迎提交 Issue 和 Pull Request！

## 📧 联系方式

- GitHub: https://github.com/AdvancedX/PostgraduateReferenceSystem
- 作者: AdvancedX
- Email：advancedx@foxmail.com

## 🙏 致谢
- 本项目基于ruoyi框架进行二次开发
- 官方网站：https://ruoyi.vip/
感谢所有为本项目做出贡献的开发者！

---

**注意**: 本系统仅供学习交流使用，预测结果仅供参考，实际报考请以官方数据为准。
