<template>
  <div class="main-layout">
    <aside class="sidebar">
      <div class="user-info">
        <div class="avatar">👨‍🎓</div>
        <h3>Dr. Zhao</h3>
        <p>全栈练习生</p>
      </div>
      <nav>
        <router-link to="/">🏠 仪表盘</router-link>
        <a @click="logout" href="#">🚪 退出登录</a>
      </nav>
    </aside>
    <main class="content">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
const router = useRouter()
const logout = () => {
  localStorage.removeItem('token')
  router.push('/login')
}
</script>

<style scoped>
.main-layout { display: flex; height: 100vh; }
.sidebar { width: 250px; background: #2c3e50; color: white; padding: 20px; display: flex; flex-direction: column; }
.content { flex: 1; padding: 40px; overflow-y: auto; }
.avatar { font-size: 60px; margin-bottom: 10px; }
nav a { color: white; text-decoration: none; display: block; margin: 15px 0; font-size: 18px; cursor: pointer; }
nav a:hover { color: #42b983; }
</style>