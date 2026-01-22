#!/bin/bash

# 1. 创建目录结构
mkdir -p mood-station/frontend/src
mkdir -p mood-station/backend-java/src/main/java/com/example/demo/controller
mkdir -p mood-station/backend-python

# 2. 生成前端文件 (Frontend)
touch mood-station/frontend/README.md
touch mood-station/frontend/index.html
touch mood-station/frontend/src/App.vue
touch mood-station/frontend/src/main.js
touch mood-station/frontend/vite.config.js

# 3. 生成 Java 后端文件 (Backend-Java)
touch mood-station/backend-java/src/main/java/com/example/demo/DemoApplication.java
touch mood-station/backend-java/src/main/java/com/example/demo/controller/MoodController.java
touch mood-station/backend-java/pom.xml

# 4. 生成 Python 后端文件 (Backend-Python)
touch mood-station/backend-python/main.py
touch mood-station/backend-python/requirements.txt

# 打印结果
echo "✨ 项目结构生成成功！可以使用 'ls -R mood-station' 查看。"