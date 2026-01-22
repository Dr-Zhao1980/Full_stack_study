<template>
  <div class="game-wrapper">
    <div class="game-header">
      <h2>🐍 贪吃蛇大作战 (Java版)</h2>
      <div class="stats">
        <span>得分: {{ score }}</span>
        <span>最高分: {{ bestScore }}</span>
      </div>
    </div>

    <div class="canvas-container">
      <canvas ref="gameCanvas" width="400" height="400"></canvas>
      <div v-if="!isPlaying" class="overlay">
        <h3 v-if="isGameOver">游戏结束</h3>
        <button @click="startGame" class="start-btn">
          {{ isGameOver ? '再试一次' : '开始游戏' }}
        </button>
      </div>
    </div>

    <div class="controls-hint">
      使用 ⬆️⬇️⬅️➡️ 控制方向
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import request from '@/api/request'

const gameCanvas = ref(null)
const score = ref(0)
const bestScore = ref(0)
const isPlaying = ref(false)
const isGameOver = ref(false)

// 游戏配置
const TILE_SIZE = 20
const GRID_COUNT = 20 // 400px / 20px
let ctx = null
let intervalId = null
let snake = []
let food = {}
let velocity = { x: 0, y: 0 }
let startTime = 0

// 初始化
onMounted(() => {
  ctx = gameCanvas.value.getContext('2d')
  document.addEventListener('keydown', handleKeydown)
  fetchLeaderboard() // 进页面先查一下最高分
})

onUnmounted(() => {
  document.removeEventListener('keydown', handleKeydown)
  clearInterval(intervalId)
})

const startGame = () => {
  snake = [{ x: 10, y: 10 }] // 初始位置
  food = spawnFood()
  velocity = { x: 0, y: 0 }
  score.value = 0
  isPlaying.value = true
  isGameOver.value = false
  startTime = Date.now()
  
  if (intervalId) clearInterval(intervalId)
  intervalId = setInterval(gameLoop, 150) // 150ms 刷新一次
}

const gameLoop = () => {
  // 1. 移动位置
  const head = { x: snake[0].x + velocity.x, y: snake[0].y + velocity.y }

  // 2. 只有当蛇开始动了才检测碰撞
  if (velocity.x !== 0 || velocity.y !== 0) {
    // 撞墙检测
    if (head.x < 0 || head.x >= GRID_COUNT || head.y < 0 || head.y >= GRID_COUNT) {
      return gameOver()
    }
    // 撞自己检测
    for (let i = 0; i < snake.length; i++) {
      if (head.x === snake[i].x && head.y === snake[i].y) {
        return gameOver()
      }
    }
    snake.unshift(head) // 加头

    // 吃食物检测
    if (head.x === food.x && head.y === food.y) {
      score.value += 10
      food = spawnFood()
    } else {
      snake.pop() // 去尾
    }
  }

  // 3. 绘制
  draw()
}

const draw = () => {
  // 清屏
  ctx.fillStyle = '#222'
  ctx.fillRect(0, 0, 400, 400)

  // 画蛇
  ctx.fillStyle = '#42b983'
  snake.forEach(part => {
    ctx.fillRect(part.x * TILE_SIZE, part.y * TILE_SIZE, TILE_SIZE - 2, TILE_SIZE - 2)
  })

  // 画食物
  ctx.fillStyle = '#ff6b6b'
  ctx.fillRect(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE - 2, TILE_SIZE - 2)
}

const spawnFood = () => {
  return {
    x: Math.floor(Math.random() * GRID_COUNT),
    y: Math.floor(Math.random() * GRID_COUNT)
  }
}

const handleKeydown = (e) => {
  if (!isPlaying.value) return
  switch(e.key) {
    case 'ArrowUp': if(velocity.y !== 1) velocity = {x:0, y:-1}; break;
    case 'ArrowDown': if(velocity.y !== -1) velocity = {x:0, y:1}; break;
    case 'ArrowLeft': if(velocity.x !== 1) velocity = {x:-1, y:0}; break;
    case 'ArrowRight': if(velocity.x !== -1) velocity = {x:1, y:0}; break;
  }
}

// 游戏结束：发送分数给 Java
const gameOver = async () => {
  clearInterval(intervalId)
  isPlaying.value = false
  isGameOver.value = true
  
  const duration = Math.floor((Date.now() - startTime) / 1000)
  
  try {
    // 调用 Java 接口
    await request.post('/api/java/game/snake/score', {
      score: score.value,
      duration: duration
    })
    console.log('分数已上传')
    fetchLeaderboard() // 刷新最高分
  } catch (e) {
    console.error('分数上传失败', e)
  }
}

// 获取 Java 端的排行榜第一名
const fetchLeaderboard = async () => {
  try {
    const res = await request.get('/api/java/game/snake/leaderboard')
    // 假设返回的是 List，取第一个作为最高分
    if (res.data && res.data.length > 0) {
      bestScore.value = res.data[0].score
    }
  } catch (e) {
    console.error(e)
  }
}
</script>

<style scoped>
.game-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.game-header {
  display: flex;
  justify-content: space-between;
  width: 400px;
  margin-bottom: 20px;
  color: #2c3e50;
}
.canvas-container {
  position: relative;
  width: 400px;
  height: 400px;
  box-shadow: 0 10px 20px rgba(0,0,0,0.2);
}
canvas {
  border-radius: 8px;
}
.overlay {
  position: absolute;
  top: 0; left: 0;
  width: 100%; height: 100%;
  background: rgba(0,0,0,0.7);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 8px;
  color: white;
}
.start-btn {
  margin-top: 20px;
  padding: 10px 30px;
  background: #42b983;
  border: none;
  border-radius: 20px;
  color: white;
  font-size: 1.2rem;
  cursor: pointer;
}
.controls-hint {
  margin-top: 20px;
  color: #666;
}
</style>