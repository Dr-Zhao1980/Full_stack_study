<template>
  <div class="mood-module">
    <div class="chat-box">
      <div class="ai-avatar">🧠</div>
      <h2>AI 情绪感知中心</h2>
      <p class="subtitle">写下你现在想说的话，Python NLP 模型将分析你的潜意识情绪。</p>
      
      <div class="input-area">
        <textarea 
          v-model="inputText" 
          placeholder="例如：今天的代码写得很顺，但是Fall Stack太难了..."
          rows="5"
        ></textarea>
      </div>

      <button @click="analyze" :disabled="loading || !inputText" class="analyze-btn">
        {{ loading ? 'Neural Network Processing...' : '开始分析' }}
      </button>

      <transition name="fade">
        <div v-if="result" class="result-card" :class="result.sentiment">
          <div class="score-ring">{{ result.score }}</div>
          <div class="analysis-text">
            <h4>分析报告</h4>
            <p>{{ result.comment }}</p>
            <p class="suggestion">💡 建议: {{ result.suggestion }}</p>
          </div>
        </div>
      </transition>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import request from '@/api/request'

const inputText = ref('')
const loading = ref(false)
const result = ref(null)

const analyze = async () => {
  loading.value = true
  result.value = null
  
  try {
    // 调用 Python 后端 (通过 vite 代理转发到 8000)
    // 这里的路径 /api/python 对应 vite.config.js 的配置
    const res = await request.post('/api/python/mood/analyze', {
      text: inputText.value
    })
    
    // 假设后端返回: { score: 85, sentiment: 'positive', comment: '...', suggestion: '...' }
    result.value = res
  } catch (error) {
    console.error(error)
    alert("Python 服务连接失败，请确保 python-backend 已启动")
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.mood-module { max-width: 800px; margin: 0 auto; }
.chat-box { background: white; padding: 40px; border-radius: 20px; box-shadow: 0 10px 30px rgba(0,0,0,0.05); text-align: center; }
.ai-avatar { font-size: 4rem; margin-bottom: 10px; animation: float 3s ease-in-out infinite; }
textarea { width: 100%; padding: 15px; border: 2px solid #eee; border-radius: 12px; resize: vertical; margin: 20px 0; font-family: inherit; transition: border 0.3s; }
textarea:focus { border-color: #3498db; outline: none; }

.analyze-btn {
  background: linear-gradient(90deg, #3498db, #2980b9);
  color: white; border: none; padding: 12px 30px; border-radius: 50px; font-size: 1.1rem; cursor: pointer; transition: transform 0.2s;
}
.analyze-btn:disabled { background: #ccc; cursor: not-allowed; }
.analyze-btn:hover:not(:disabled) { transform: scale(1.05); }

.result-card { margin-top: 30px; padding: 20px; border-radius: 12px; display: flex; align-items: flex-start; text-align: left; gap: 20px; background: #f8f9fa; }
.score-ring { font-size: 2rem; font-weight: bold; color: #2c3e50; min-width: 60px; }
.suggestion { color: #666; font-style: italic; margin-top: 10px; }

@keyframes float { 0%, 100% { transform: translateY(0); } 50% { transform: translateY(-10px); } }
.fade-enter-active, .fade-leave-active { transition: opacity 0.5s; }
.fade-enter-from, .fade-leave-to { opacity: 0; }
</style>