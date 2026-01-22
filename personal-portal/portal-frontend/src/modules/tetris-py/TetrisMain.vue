<template>
  <div class="tetris-container">
    <h2>🧱 俄罗斯方块 (Demo)</h2>
    <div class="game-board">
        <div class="placeholder-text">
            游戏开发中... <br>
            Python AI 引擎已就绪
        </div>
    </div>
    <div class="info-panel">
        <button @click="callPython" class="test-btn">测试 Python 连接</button>
        <p>后端状态: {{ status }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import request from '@/api/request'

const status = ref('等待测试...')

const callPython = async () => {
    try {
        // 调用我们之前在 main.py 定义的根路由
        const res = await request.get('/api/python/')
        status.value = `连接成功: ${res.message}`
    } catch (e) {
        status.value = '连接失败'
    }
}
</script>

<style scoped>
.tetris-container { text-align: center; }
.game-board { 
    width: 200px; height: 400px; background: #333; margin: 20px auto; 
    display: flex; align-items: center; justify-content: center;
    color: #666; border: 4px solid #555;
}
.test-btn {
    padding: 10px 20px; background: #3498db; color: white; border: none; border-radius: 4px; cursor: pointer;
}
</style>