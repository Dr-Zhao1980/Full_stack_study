<template>
  <div class="blog-view">
    <div class="blog-header">
      <h2>📝 学习日志</h2>
      <button class="new-post-btn" @click="createNew">+ 写文章</button>
    </div>

    <div v-if="loading" class="loading">加载中...</div>

    <div v-else class="post-list">
      <article v-for="post in posts" :key="post.id" class="post-item">
        <h3>{{ post.title }}</h3>
        <div class="meta">
          <span>📅 {{ formatDate(post.createdAt) }}</span>
          <span>🏷️ {{ post.tags }}</span>
        </div>
        <p class="summary">{{ post.summary }}...</p>
        <button class="read-more">阅读全文</button>
      </article>
      
      <div v-if="posts.length === 0" class="empty-state">
        暂无文章，开始记录你的全栈之旅吧！
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '@/api/request'

const posts = ref([])
const loading = ref(true)

// 模拟获取博客列表
const fetchPosts = async () => {
  try {
    // 调用 Java 后端接口
    // const res = await request.get('/api/java/blog/list')
    // posts.value = res.data
    
    // 【开发阶段 Mock 数据】
    // 在你 Java 后端没写好之前，先用这个假数据测试界面
    setTimeout(() => {
      posts.value = [
        { id: 1, title: '我的全栈架构设计心得', createdAt: '2026-01-20', tags: 'Architecture', summary: '从单体应用到微服务模块化设计的演进过程...' },
        { id: 2, title: 'Vue3 Composition API 实践', createdAt: '2026-01-18', tags: 'Vue3', summary: '为什么 setup 语法糖比 Options API 更适合逻辑复用...' }
      ]
      loading.value = false
    }, 500)
    
  } catch (e) {
    console.error(e)
    loading.value = false
  }
}

const formatDate = (dateStr) => {
  return new Date(dateStr).toLocaleDateString()
}

const createNew = () => {
  alert("功能开发中：将跳转到 Markdown 编辑器")
}

onMounted(() => {
  fetchPosts()
})
</script>

<style scoped>
.blog-view { max-width: 800px; margin: 0 auto; }
.blog-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }
.new-post-btn { background: #2c3e50; color: white; border: none; padding: 8px 16px; border-radius: 6px; cursor: pointer; }

.post-item { background: white; padding: 25px; border-radius: 12px; margin-bottom: 20px; box-shadow: 0 2px 10px rgba(0,0,0,0.03); transition: transform 0.2s; }
.post-item:hover { transform: translateX(5px); }
.post-item h3 { margin: 0 0 10px 0; color: #2c3e50; }
.meta { font-size: 0.85rem; color: #999; margin-bottom: 15px; display: flex; gap: 15px; }
.summary { color: #666; line-height: 1.6; }
.read-more { background: none; border: none; color: #42b983; cursor: pointer; padding: 0; font-weight: bold; margin-top: 10px; }
</style>