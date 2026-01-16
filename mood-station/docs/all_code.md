# [前端代码App.vue](../frontend/src/App.vue)
<!-- 
<script> 是(大脑/JS)：
这一部分主要表示script的相关内容，用于表示JS的内容。 
-->
<script setup>
import { ref } from 'vue'
import axios from 'axios'

// --- 定义数据 (食材) ---

// 1. 标题变量
const title = ref("我的心情气象站") 

// 2. 用户输入的心情 (初始是空的)
const inputMood = ref("") 

// 3. AI分析的结果 (初始是null，表示没结果)
const aiResult = ref(null)

// 4. 加载状态 (是否正在请求中)
const isLoading = ref(false)

// --- 定义动作 (烹饪步骤) ---
const sendMood = async () => {
  // 如果输入框是空的，就不发送
  if (inputMood.value === "") return;

  isLoading.value = true; // 开始加载 (打开转圈圈)

  try {
    // 发送请求给 Java 后端
    const response = await axios.post('https://6ea2367d.r30.cpolar.top/api/mood', {
      username: "zzn16", 
      text: inputMood.value
    });
    
    // 把后端返回的菜，放到我们的盘子里
    aiResult.value = response.data; 
    
    // 清空输入框
    inputMood.value = ""; 
  } catch (error) {
    alert("连接后端失败，请确保Java运行中！");
  } finally {
    isLoading.value = false; // 结束加载
  }
}
</script>















<!--    
<style> 是(衣服/CSS)： 
这个内容主要表示style的相关内容，用于表示CSS的内容。 
-->
<template>
  <div class="container">
    
    <h1>{{ title }}</h1>
    
    <div class="input-box">
      <input v-model="inputMood" placeholder="今天发生了什么？" />
      
      <button @click="sendMood" :disabled="isLoading">
        {{ isLoading ? "分析中..." : "发射给AI" }}
      </button>
    </div>

    <div v-if="aiResult" class="result-card">
      <h3>AI 分析报告</h3>
      
      <p>心情得分：<strong>{{ aiResult.score }}</strong></p>
      
      <p>幸运色：<span :style="{ color: aiResult.luckyColor }">{{ aiResult.luckyColor }}</span></p>
      
      <p class="comment">“{{ aiResult.aiComment }}”</p>
    </div>

  </div>
</template>


























<!--
<style> 是(衣服/CSS)：
这个内容主要表示style的相关内容，用于表示CSS的内容。 
-->
<style scoped>
.container {
  max-width: 600px;
  margin: 50px auto;
  text-align: center;
  font-family: sans-serif;
}

h1 {
  color: #2c3e50; /* 这是 CSS 控制颜色，跟 Vue 没关系 */
}

.input-box {
  display: flex;
  gap: 10px;
  justify-content: center;
  margin-bottom: 30px;
}

input {
  padding: 10px;
  width: 300px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:disabled {
  background-color: #ccc; /* 按钮被禁用时的颜色 */
  cursor: wait;
}

.result-card {
  border: 2px solid #42b983;
  padding: 20px;
  border-radius: 10px;
  background-color: #f9f9f9;
}

.comment {
  font-style: italic;
  color: #666;
}
</style>
# [前端代码main.js](../frontend/src/main.js)
// src/main.js
import { createApp } from 'vue'
import App from './App.vue' // 引入你写的那个文件

createApp(App).mount('#app')
# [前端代码index.html](../frontend/index.html)
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>心情气象站</title>
  </head>
  <body>
    <div id="app"></div>
    <script type="module" src="/src/main.js"></script>
  </body>
</html>

# [前端代码vite.config.js](../frontend/vite.config.js)
// import { defineConfig } from 'vite'
// import vue from '@vitejs/plugin-vue'

// export default defineConfig({
//   plugins: [vue()],
// })
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    // 关键步骤 1：允许局域网/公网访问 (Host: 0.0.0.0)
    host: '0.0.0.0', 
    
    // 关键步骤 2：把报错里的那个域名加进去，或者直接设为 true (允许所有)
    // 推荐设为 true，这样下次 cpolar 换了域名也不用改代码
    allowedHosts: true, 
  }
})
# [前端代码package.json](../frontend/package.json)
{
  "name": "mood-weather-station",
  "version": "1.0.0",
  "description": "一个基于Vue3和Java后端的心情气象站前端项目",
  "type": "module",
  "scripts": {
    "dev": "vite",
    "build": "vite build",
    "preview": "vite preview"
  },
  "dependencies": {
    "vue": "^3.4.0",
    "axios": "^1.6.0"
  },
  "devDependencies": {
    "@vitejs/plugin-vue": "^5.0.0",
    "vite": "^5.0.0"
  }
}
# [后端代码DemoApplication.java](../backend-java/src/main/java/com/example/demo/DemoApplication.java)
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 这个注解告诉 Spring Boot：这里是起点，请开始扫描周围所有的组件（Controller, Service等）
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
# [后端代码MoodController.java](../backend-java/src/main/java/com/example/demo/controller/MoodController.java)
// package com.example.demo.controller;

// import com.example.demo.model.MoodRecord;
// import com.example.demo.utils.FileService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.client.RestTemplate;

// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.Map;

// @RestController
// @RequestMapping("/api")
// @CrossOrigin(origins = "*") // 允许 Vue (localhost:5173) 访问 Java (localhost:8080)
// public class MoodController {

//     @Autowired
//     private FileService fileService;

//     @PostMapping("/mood")
//     public MoodRecord submitMood(@RequestBody Map<String, String> payload) {
//         String username = payload.get("username");
//         String text = payload.get("text");

//         System.out.println("收到前端请求: " + text);

//         // 1. 呼叫 Python 小弟 (FastAPI)
//         RestTemplate restTemplate = new RestTemplate();
//         String pythonUrl = "http://localhost:8000/analyze";
        
//         // 准备发给 Python 的数据
//         Map<String, String> pythonPayload = Map.of("text", text);
        
//         // 拿到 Python 的结果 (这里是下划线命名 snake_case)
//         Map aiResult = restTemplate.postForObject(pythonUrl, pythonPayload, Map.class);

//         // 2. 数据转换与封装 (关键步骤！)
//         // Python给的是 lucky_color, 我们要存成 luckyColor
//         String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
//         MoodRecord newRecord = new MoodRecord(
//             now,
//             text,
//             (Integer) aiResult.get("score"),           // 拿 Python 的 score
//             (String) aiResult.get("lucky_color"),      // 拿 Python 的 lucky_color
//             (String) aiResult.get("ai_comment")        // 拿 Python 的 ai_comment
//         );

//         // 3. 保存到硬盘
//         fileService.saveRecord(username, newRecord);

//         // 4. 返回给 Vue (Vue 会收到驼峰命名的 JSON)
//         return newRecord;
//     }
// }

package com.example.demo.controller;

import com.example.demo.model.MoodRecord;
import com.example.demo.utils.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List; // <--- 必须导入 List
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") 
public class MoodController {

    @Autowired
    private FileService fileService;

    // 👇👇👇 之前缺失的登录接口 👇👇👇
    @GetMapping("/login")
    public List<MoodRecord> login(@RequestParam String username) {
        System.out.println("👤 用户登录: " + username);
        // 调用 FileService 去读硬盘上的 json 文件
        return fileService.readRecords(username);
    }
    // 👆👆👆 补上这一段 👆👆👆

    @PostMapping("/mood")
    public MoodRecord submitMood(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String text = payload.get("text");

        System.out.println("收到前端请求: " + text);

        // 1. 呼叫 Python 小弟 (FastAPI)
        RestTemplate restTemplate = new RestTemplate();
        String pythonUrl = "http://localhost:8000/analyze";
        
        // 准备发给 Python 的数据
        Map<String, String> pythonPayload = Map.of("text", text);
        
        // 拿到 Python 的结果
        Map aiResult = restTemplate.postForObject(pythonUrl, pythonPayload, Map.class);

        // 2. 数据转换与封装
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        MoodRecord newRecord = new MoodRecord(
            now,
            text,
            (Integer) aiResult.get("score"),           
            (String) aiResult.get("lucky_color"),      
            (String) aiResult.get("ai_comment")        
        );

        // 3. 保存到硬盘
        fileService.saveRecord(username, newRecord);

        // 4. 返回给 Vue
        return newRecord;
    }
}
# [后端代码FileService.java](../backend-java/src/main/java/com/example/demo/utils/FileService.java)
package com.example.demo.utils;

import com.example.demo.model.MoodRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service // 标记这是一个服务
public class FileService {

    private final String DATA_DIR = "data";
    private final ObjectMapper mapper = new ObjectMapper(); // JSON 转换工具

    public FileService() {
        // 启动时检查 data 文件夹是否存在，没有就创建
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    private File getUserFile(String username) {
        return new File(DATA_DIR + "/" + username + ".json");
    }

    // 保存记录
    public void saveRecord(String username, MoodRecord record) {
        List<MoodRecord> history = readRecords(username);
        history.add(record);
        try {
            // 写入文件 (PrettyPrinter 让 JSON 格式化好看)
            mapper.writerWithDefaultPrettyPrinter().writeValue(getUserFile(username), history);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读取记录
    public List<MoodRecord> readRecords(String username) {
        File file = getUserFile(username);
        if (!file.exists()) return new ArrayList<>();
        try {
            return mapper.readValue(file, new TypeReference<List<MoodRecord>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
# [后端代码MoodRecord.java](../backend-java/src/main/java/com/example/demo/model/MoodRecord.java)
package com.example.demo.model;

// 这是一个数据模具，对应 Vue 里的 aiResult
public class MoodRecord {
    public String date;       // 记录时间
    public String text;       // 你的心情文本
    public int score;         // 心情得分
    
    // 注意：这里必须用驼峰命名，为了配合 Vue 的 {{ aiResult.luckyColor }}
    public String luckyColor; 
    public String aiComment;  

    // 空构造函数 (必须有，不然报错)
    public MoodRecord() {}

    public MoodRecord(String date, String text, int score, String luckyColor, String aiComment) {
        this.date = date;
        this.text = text;
        this.score = score;
        this.luckyColor = luckyColor;
        this.aiComment = aiComment;
    }
}
# [后端代码pom.xml](../backend-java/pom.xml)
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" 
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.1</version>
        <relativePath/> </parent>

    <groupId>com.example</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>demo</name>
    <description>Mood Station Project</description>

    <properties>
        <java.version>17</java.version> </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

# [后端代码main.py](../backend-python/main.py)
from fastapi import FastAPI
from pydantic import BaseModel
import requests
import json

app = FastAPI()


class MoodRequest(BaseModel):
    text: str


API_KEY = "sk-ioptkidyhppvzillnotrypqjhczvyhkigppzgcgwctwhedgs" # 替换为你提供的Key
API_URL = "https://api.siliconflow.cn/v1/chat/completions"

@app.post("/analyze")
def analyze_mood(mood: MoodRequest):
    print(f"收到心情分析请求: {mood.text}")
    
    # 构造 Prompt，强迫 AI 返回 JSON 格式
    system_prompt = """
    你是一个心情分析师。请分析用户输入的心情文本。
    必须严格以 JSON 格式返回，不要包含 markdown 标记或其他废话。
    JSON 格式要求如下：
    {
        "score": (整数, 0-100, 分数越高越开心),
        "lucky_color": (字符串, 推荐一个幸运色),
        "ai_comment": (字符串, 一句简短暖心的点评, 50字以内)
    }
    """

    payload = {
        "model": "deepseek-ai/DeepSeek-V3.2-Exp",
        "messages": [
            {"role": "system", "content": system_prompt},
            {"role": "user", "content": mood.text}
        ],
        "stream": False,
        "max_tokens": 512,
        "temperature": 0.7,
        "response_format": { "type": "json_object" } # 关键：强制 JSON 模式
    }

    headers = {
        "Authorization": f"Bearer {API_KEY}",
        "Content-Type": "application/json"
    }

    try:
        # 真正调用 AI
        response = requests.post(API_URL, json=payload, headers=headers)
        response_data = response.json()
        
        # 提取 AI 回复的内容
        ai_content = response_data['choices'][0]['message']['content']
        print("AI 返回原始内容:", ai_content)
        
        # 将字符串转为 Python 字典返回
        return json.loads(ai_content)
        
    except Exception as e:
        print(f"调用出错: {e}")
        return {
            "score": 0, 
            "lucky_color": "灰色", 
            "ai_comment": "AI 大脑暂时短路了，请检查网络或 Key。"
        }



# [后端代码requirements.txt](../backend-python/requirements.txt)
fastapi
uvicorn
requests