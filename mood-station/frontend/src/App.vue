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
    // const response = await axios.post('https://6ea2367d.r30.cpolar.top/api/mood', {
    //   username: "zzn16", 
    //   text: inputMood.value
    // });
    const response = await axios.post('/api/mood', {
      username: "zzn16", 
      text: inputMood.value
      });
    //使用相对路径
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