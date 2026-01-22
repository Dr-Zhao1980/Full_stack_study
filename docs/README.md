# 文件结构
这次，我们已经成功完成了一个功能的添加，接下来进行一个更加复杂的内容的构建：个人博客，同时加上许多的插件功能，现在首先要有三个：贪吃蛇(java实现)，俄罗斯方块(由python实现)，以及AI心情分析器。这个新的项目架构如下：
/home/zzn16/Full_stack_study/personal-portal/
├── docs/                        # 数据库 Schema 设计、Auth 逻辑说明、API 接口文档
├── scripts/                     # 自动化脚本
│   ├── init_db.sh               # 初始化本地 H2 和 SQLite 数据库脚本
│   ├── dev.sh                   # 一键启动：Vite(5173) + SpringBoot(8080) + FastAPI(8000)
│   └── build.sh                 # 自动化打包并将 dist 迁移至 SpringBoot static 目录
│
├── portal-frontend/             # 【前端：Vue3 + Vite + Pinia】
│   ├── src/
│   │   ├── api/                 # Axios 封装：包含 Auth 拦截器（请求自动带上 Token）
│   │   ├── assets/              # 全局样式、图标、Alex 风格头像
│   │   ├── components/          # 全局通用 UI（功能卡片 FeatureCard.vue、弹窗）
│   │   ├── layouts/             # 【嵌套 UI 布局层】
│   │   │   ├── MainLayout.vue   # 基础框架：左侧 Alex 信息栏 + 右侧功能 RouterView
│   │   │   ├── AuthLayout.vue   # 简洁布局：仅用于登录和注册页面
│   │   │   └── ModuleLayout.vue # 插件内部容器：带返回按钮和模块标题的子布局
│   │   ├── middleware/          # 【路由守卫】校验用户登录状态（检查本地存储的 Token）
│   │   │   └── authGuard.js     
│   │   ├── modules/             # 【功能插件箱：业务逻辑物理隔离】
│   │   │   ├── mood/            # --- AI 心情模块 ---
│   │   │   │   ├── MoodMain.vue
│   │   │   │   └── router.js    # 模块私有路由
│   │   │   ├── snake-java/      # --- Java 贪吃蛇前端 --- (与 8080 端口通信)
│   │   │   │   ├── SnakeMain.vue
│   │   │   │   └── components/
│   │   │   ├── tetris-py/       # --- Python 俄罗斯方块前端 --- (与 8000 端口通信)
│   │   │   │   ├── TetrisMain.vue
│   │   │   │   └── components/
│   │   │   └── blog/            # --- 个人博客模块 --- (无需登录即可访问)
│   │   │       └── BlogView.vue
│   │   ├── views/               # 平台基础视图
│   │   │   ├── Dashboard.vue    # 仪表盘：读取注册表，若未登录则显示锁定图标
│   │   │   └── Login.vue        # 登录页：包含正则校验逻辑（如邮箱、密码强度）
│   │   ├── router/              # 路由总控
│   │   │   └── index.js         # 整合所有 modules 路由，应用 authGuard
│   │   ├── registry/            # 【功能注册表】
│   │   │   └── featureList.js   # 定义：ID、标题、是否需要登录、路由路径
│   │   ├── App.vue
│   │   └── main.js
│   └── vite.config.js           # 配置代理解决跨域
│
├── portal-backend/              # 【主后端：Spring Boot + H2 数据库】
│   ├── src/main/java/com/drzhao/portal/
│   │   ├── common/              # 全局统一 Result 返回体、枚举、异常拦截器
│   │   ├── infrastructure/      # 基础设施层
│   │   │   ├── config/          # H2 数据库连接配置、CORS 配置
│   │   │   └── security/        # JWT 或 Session 鉴权核心配置
│   │   └── modules/             # 【后端业务插件包】
│   │       ├── auth/            # --- 登录鉴权模块 ---
│   │       │   ├── AuthController.java # 接收登录请求、注销
│   │       │   ├── AuthService.java    # 编写密码加密与格式验证逻辑
│   │       │   └── UserRepository.java # 对接本地 H2 数据库中的用户表
│   │       ├── game/            # --- 游戏模块：Java 贪吃蛇 ---
│   │       │   ├── SnakeController.java
│   │       │   ├── SnakeService.java   # 处理蛇的坐标、长度及排行榜结算
│   │       │   └── ScoreRepository.java # 记录最高分到 H2
│   │       └── blog/            # --- 博客管理 --- (读写本地 H2)
│   ├── src/main/resources/
│   │   ├── db/                  # SQL 脚本 (schema.sql 定义表结构, data.sql 初始化数据)
│   │   └── application.yml      # 配置本地 H2 数据库存储路径 (data/portal.db)
│   ├── data/                    # 【本地数据库文件】存放 portal.db
│   └── pom.xml
│
└── ai-services/                 # 【子后端：FastAPI + SQLite】
    ├── app/
    │   ├── database/            # SQLite 数据库连接器 (用于存储 Python 侧游戏数据)
    │   │   └── session.py
    │   ├── core/                # 核心算法：情绪分析逻辑
    │   ├── routers/             # 功能路由
    │   │   ├── mood.py          # /analyze 接口
    │   │   └── tetris.py        # --- Python 俄罗斯方块逻辑 --- (计分与存储)
    │   │       ├── logic.py     # 游戏内核逻辑
    │   │       └── crud.py      # 读取/保存俄罗斯方块得分至 SQLite
    │   ├── schemas/             # Pydantic 响应与请求模型
    │   └── main.py              # FastAPI 入口
    ├── data/                    # 【本地数据库文件】存放 ai_tools.db
    └── requirements.txt





























































