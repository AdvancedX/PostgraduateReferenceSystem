# 历年分数与 2022 详细数据统一重构方案

## 1. 文档目的

当前系统同时存在“历年分数”和“2022 年详细信息”两个菜单、两个页面、两套后端模块及两张数据表。它们的核心数据存在重叠，但详细程度不同。

本方案的目标是：

1. 对用户只保留一个“分数查询”页面；
2. 统一查询、维护、导出和 AI 调用入口；
3. 保留 2022 年已有的单科线和院校扩展信息；
4. 允许不同年份的数据完整度不同；
5. 避免未来继续增加 `score_2023`、`score_2024` 等年度模块；
6. 在可回滚的前提下逐步移除 `score_2022` 历史模块。

## 2. 当前情况

### 2.1 `score` 模块

- 页面入口：`/score/score`
- 列表接口：`/score/score/list`
- 数据表：`year_score`
- 权限前缀：`score:score:*`
- SQL 文件内约 50,089 条记录
- 当前 AI 的 `query_score` 工具依赖该模块

主要字段：

| 字段 | 含义 |
| --- | --- |
| `id` | 主键 |
| `year` | 年份 |
| `school` | 院校名称 |
| `major_id` | 专业代码 |
| `major` | 专业名称 |
| `score` | 总分 |

这套数据适合跨年份查询和趋势对比，但无法展示单科线、硕士类型等详细信息。

### 2.2 `score_2022` 模块

- 页面入口：`/score_2022/score_2022`
- 列表接口：`/score_2022/score_2022/list`
- 数据表：`major_score_2022`
- 权限前缀：`score_2022:score_2022:*`
- SQL 文件内约 19,773 条记录
- 由 2025 年 4 月 8 日的提交 `51d21c9 Add:添加了2022年的详细信息` 引入

除年份、学校、专业代码、专业名称和总分外，它还包含：

- 学术型/专业型硕士；
- 政治、外语、专业课Ⅰ、专业课Ⅱ单科线；
- 备注、省份、学校属性、隶属关系；
- 学校官网、专业官网、电话、邮箱和地址。

当前该模块仅服务自己的 CRUD 页面，AI 和其他业务没有直接使用。

### 2.3 当前主要问题

1. 用户难以理解两个“分数”入口的区别；
2. 2022 年数据同时出现在两张表中，存在重复和不一致风险；
3. 两套 Controller、Service、Mapper 和页面产生重复维护成本；
4. 权限被拆成两套，角色配置较复杂；
5. 详细数据以年份命名模块，不具备继续扩展其他年份的能力；
6. 院校官网、地址等信息在每个专业分数记录中重复保存；
7. `majorCode`、`major_id` 等字段命名不统一。

## 3. 目标形态

### 3.1 用户入口

只保留一个菜单：

```text
分数预测
└── 分数查询
```

统一访问地址：

```text
/score/score
```

旧地址暂时保留重定向：

```text
/score_2022/score_2022 -> /score/score?year=2022&detail=true
```

### 3.2 页面结构

统一页面建议包含以下区域：

1. 查询条件：年份、院校、专业名称、专业代码、硕士类型、省份；
2. 快速视图：“全部年份”“2022 详细数据”；
3. 主表格：始终展示年份、学校、专业代码、专业名称和总分；
4. 详细列：有数据时展示硕士类型和四项单科线，无数据时显示 `—`；
5. 详情抽屉：展示备注、学校属性、官网、联系方式等低频字段；
6. 行操作：查看、编辑、删除；
7. 工具栏：新增、批量删除、导出。

不建议继续维护两个独立的 Bootstrap Table 页面。所谓“2022 详细数据”应当只是统一页面中的筛选结果或快速视图。

### 3.3 统一权限

保留以下权限：

```text
score:score:view
score:score:list
score:score:add
score:score:edit
score:score:remove
score:score:export
```

迁移完成后删除：

```text
score_2022:score_2022:view
score_2022:score_2022:list
score_2022:score_2022:add
score_2022:score_2022:edit
score_2022:score_2022:remove
score_2022:score_2022:export
```

过渡期内，应先把拥有 `score_2022:*` 权限的角色补充对应的 `score:score:*` 权限，再删除旧权限，避免用户突然无法访问。

## 4. 数据模型设计

### 4.1 推荐方案：新建统一表后迁移

不建议直接在生产环境原地修改 `year_score`。新建 `score_record`，完成迁移和校验后再切换代码，回滚成本最低。

建议结构：

```sql
CREATE TABLE score_record (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    year SMALLINT NOT NULL COMMENT '年份',
    school VARCHAR(255) NOT NULL COMMENT '院校名称',
    major_code VARCHAR(32) NOT NULL COMMENT '专业代码',
    major_name VARCHAR(255) NOT NULL COMMENT '专业名称',
    master_type VARCHAR(32) NULL COMMENT '学术型硕士/专业型硕士',
    total_score VARCHAR(32) NULL COMMENT '总分或原始分数字符串',
    politic_score VARCHAR(32) NULL COMMENT '政治单科线',
    language_score VARCHAR(32) NULL COMMENT '外语单科线',
    subject1_score VARCHAR(32) NULL COMMENT '专业课Ⅰ单科线',
    subject2_score VARCHAR(32) NULL COMMENT '专业课Ⅱ单科线',
    note VARCHAR(500) NULL COMMENT '备注',
    province VARCHAR(64) NULL COMMENT '学校省份',
    school_type VARCHAR(255) NULL COMMENT '学校属性',
    website VARCHAR(500) NULL COMMENT '学校官网',
    major_website VARCHAR(500) NULL COMMENT '专业官网',
    phone_number VARCHAR(64) NULL COMMENT '电话',
    email VARCHAR(255) NULL COMMENT '电子邮箱',
    address VARCHAR(500) NULL COMMENT '地址',
    belonging VARCHAR(255) NULL COMMENT '隶属',
    source_table VARCHAR(32) NOT NULL COMMENT '迁移来源',
    source_id BIGINT NOT NULL COMMENT '来源主键',
    created_at DATETIME NULL,
    updated_at DATETIME NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_score_source (source_table, source_id),
    KEY idx_score_query (year, school, major_code),
    KEY idx_score_major (major_name),
    KEY idx_score_province (province)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

分数字段暂时保留为字符串，原因是原始数据可能出现 `--`、空值、范围或其他非纯数字内容。后续如果要做数值统计，可以额外增加清洗后的数值字段，而不是直接破坏原始数据。

### 4.2 院校信息的后续规范化

当前 `school_info` 只有学校名称、学校类型和地区，暂时无法承载 `score_2022` 中的全部院校信息。因此第一阶段先把这些字段迁入 `score_record`，确保不丢数据。

后续可以扩展独立的院校档案表，将以下字段从分数记录中移出：

- 省份、学校属性、隶属关系；
- 学校官网、电话、邮箱、地址。

专业官网和专业级联系方式仍可以保留在分数/专业关系中。完成院校信息规范化后，统一页面通过关联查询得到学校资料。

## 5. 数据迁移策略

### 5.1 迁移原则

1. 原表在验收完成前不删除；
2. 所有迁移记录保留 `source_table` 和 `source_id`；
3. 先迁入历年数据，再用 2022 详细数据补齐或新增；
4. 不直接用学校名称和专业名称做不可逆覆盖；
5. 对重复、缺失和冲突数据生成审计结果。

### 5.2 第一批：迁移 `year_score`

```sql
INSERT INTO score_record (
    year, school, major_code, major_name, total_score,
    source_table, source_id
)
SELECT
    CAST(year AS UNSIGNED), school, major_id, major, score,
    'year_score', id
FROM year_score;
```

### 5.3 第二批：迁移 `major_score_2022`

建议先把 2022 详细记录全部作为独立来源插入，再通过审计脚本判断哪些记录可以与 `year_score` 合并。这样不会因模糊匹配错误覆盖数据。

```sql
INSERT INTO score_record (
    year, school, major_code, major_name, master_type, total_score,
    politic_score, language_score, subject1_score, subject2_score,
    note, province, school_type, website, major_website,
    phone_number, email, address, belonging,
    source_table, source_id
)
SELECT
    CAST(year AS UNSIGNED), school, majorCode, major, type, score,
    politic, language, subject1, subject2,
    note, province, school_type, website, major_website,
    phonenumber, email, site, belonging,
    'major_score_2022', id
FROM major_score_2022;
```

### 5.4 重复数据处理

候选重复记录可使用以下组合进行识别：

```text
year + 标准化学校名称 + 标准化专业代码 + 标准化专业名称 + 总分
```

不应仅凭学校、专业代码和年份自动删除，因为：

- 同一专业可能同时存在学硕和专硕；
- 同一学校可能存在学院级或方向级分数线；
- 专业代码在不同数据源中的前导零可能不一致；
- 专业名称可能包含“专业学位”等前缀差异。

推荐输出三类审计结果：

| 类型 | 处理方式 |
| --- | --- |
| 完全一致 | 可合并为一条，保留两个来源映射 |
| 核心字段一致、分数冲突 | 保留两条并人工确认 |
| 只有名称近似 | 不自动合并 |

如果需要完整保留多来源关系，后续可以增加 `score_record_source` 映射表，而不是在主表只保存一个来源。

## 6. 后端重构

### 6.1 统一领域对象

在 `score` 模块中新增或重命名为：

```text
ScoreRecord
ScoreRecordMapper
IScoreRecordService
ScoreRecordServiceImpl
ScoreRecordController
```

统一查询对象至少支持：

```text
year
startYear
endYear
school
majorCode
majorName
masterType
province
minTotalScore
maxTotalScore
detailOnly
```

### 6.2 统一接口

建议保留现有 URL 风格，降低菜单和前端改造量：

| 功能 | 方法 | 地址 |
| --- | --- | --- |
| 页面 | GET | `/score/score` |
| 列表 | POST | `/score/score/list` |
| 详情 | GET | `/score/score/{id}` |
| 新增页面 | GET | `/score/score/add` |
| 新增保存 | POST | `/score/score/add` |
| 编辑页面 | GET | `/score/score/edit/{id}` |
| 编辑保存 | POST | `/score/score/edit` |
| 删除 | POST | `/score/score/remove` |
| 导出 | POST | `/score/score/export` |

列表返回统一字段。某年份没有详细数据时，对应字段返回 `null`，前端显示 `—`。

### 6.3 旧接口兼容

在过渡版本中：

- `/score_2022/score_2022` 重定向到统一页面；
- 旧列表接口可以保留一个发布周期，并在日志中记录调用；
- 确认没有调用方后删除 `MajorScore2022Controller`；
- 不建议长期维护两套写接口，否则数据仍会发生分叉。

### 6.4 AI 工具调整

当前 `ScoreQueryTool` 使用 `IYearScoreService`。切换步骤：

1. 给统一 Service 提供受限查询方法；
2. 保持 AI 工具名称 `query_score` 和权限 `score:score:list` 不变；
3. 将返回字段扩充为可选的单科线和硕士类型；
4. 保留 `limit` 最大 100 条的限制；
5. 增加针对 2022 详细查询和跨年份查询的测试。

保持工具名称和权限不变，可以避免对 Agent 提示词、工具注册和角色权限做额外迁移。

## 7. 前端重构

### 7.1 查询体验

统一页面默认使用常用条件：

- 年份；
- 院校名称；
- 专业名称；
- 专业代码。

“更多筛选”中放置：

- 硕士类型；
- 省份；
- 学校属性；
- 隶属关系；
- 总分范围；
- 仅查看有单科线的数据。

### 7.2 表格列

默认列：

```text
年份｜院校｜硕士类型｜专业代码｜专业名称｜总分｜政治｜外语｜专业课Ⅰ｜专业课Ⅱ｜操作
```

官网、联系方式、地址等字段放入详情抽屉，避免表格横向过宽。

移动端只保留年份、院校、专业和总分，点击行后查看完整详情。

### 7.3 快速视图

页面顶部可以提供：

```text
[全部年份] [2022 详细数据] [有单科线] [我的收藏（未来）]
```

这些按钮只改变查询条件，不切换到另一套页面。

## 8. 菜单和模块清理

完成切换后需要：

1. 删除 `sys_menu` 中 ID 2091—2096 对应的旧菜单和权限；
2. 将原“历年分数”菜单重命名为“分数查询”；
3. 从 `admin/pom.xml` 删除 `score_2022` 依赖；
4. 从根 `pom.xml` 的 `dependencyManagement` 和 `modules` 删除 `score_2022`；
5. 删除 `score_2022` 模块源码；
6. 最后删除或归档 `major_score_2022` 表。

删除模块必须在统一接口、迁移数据和旧地址重定向全部验证通过之后执行。

## 9. 分阶段实施计划

### 阶段 A：统一页面，暂不迁表

目标：尽快消除用户看到两个入口的问题。

1. 重构 `/score/score` 页面；
2. 页面通过统一的查询适配层读取两张表；
3. 增加“全部年份”和“2022 详细数据”快速视图；
4. 将旧页面入口重定向到统一页面；
5. 暂时保留两套表和 Service。

验收后，用户侧只看到一个页面，但后端仍处于过渡状态。

### 阶段 B：新建统一表并迁移

1. 创建 `score_record`；
2. 执行两批数据迁移；
3. 生成数量、空值、重复和冲突审计报告；
4. 抽样核对学校、专业、总分和单科线；
5. 新增统一 Mapper、Service 和 Controller；
6. 列表、导出和 AI 全部切换到统一 Service。

### 阶段 C：清理旧模块

1. 停止旧表写入；
2. 观察一个发布周期；
3. 删除旧菜单和权限；
4. 删除 `score_2022` Maven 模块；
5. 备份并归档 `major_score_2022`；
6. 更新部署文档和数据库初始化 SQL。

## 10. 验收标准

### 10.1 功能验收

- 系统中只显示一个“分数查询”菜单；
- 能按年份、学校、专业和专业代码查询；
- 2022 年详细记录能展示总分及四项单科线；
- 缺少详细字段的年份正常显示，不出现前端异常；
- 新增、编辑、删除和导出功能正常；
- 旧地址能正确跳转；
- AI 能完成历年查询和 2022 单科线查询；
- 未授权用户不能通过直接请求访问管理接口。

### 10.2 数据验收

- `year_score` 的每条原始记录都有可追溯迁移记录；
- `major_score_2022` 的每条原始记录都有可追溯迁移记录；
- 迁移前后各来源记录数量一致；
- 分数、专业代码中的前导零和非数字内容未被意外转换；
- 冲突数据没有被静默覆盖；
- 随机抽样记录的全部字段一致。

### 10.3 技术验收

- Maven Reactor 构建成功；
- `score` 与 `ai-chat` 相关测试全部通过；
- 新增 Mapper 查询有分页和必要索引；
- 导出大量数据时不会一次加载全部记录导致内存问题；
- 页面在后台 iframe 和移动端均可正常滚动与操作。

## 11. 回滚方案

阶段 A 回滚：恢复原菜单 URL 和原页面，不涉及数据库。

阶段 B 回滚：将代码查询重新指向 `year_score` 和 `major_score_2022`。由于迁移期间不删除原表，回滚不需要反向恢复数据。

阶段 C 开始前必须完成数据库备份。旧表建议至少保留一个发布周期的只读备份，再进行物理删除。

## 12. 推荐结论

采用“先统一页面，再统一数据，最后删除旧模块”的三阶段方案。

不要直接删除 `score_2022`，也不要把详细字段未经审计地覆盖到 `year_score`。先让用户只面对一个入口，再用带来源追踪的新表完成安全迁移，可以同时控制开发风险和数据风险。
