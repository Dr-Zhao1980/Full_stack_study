<template>
  <div class="login-form">
    <div class="input-group">
      <input type="text" v-model="form.username" placeholder="Username" />
    </div>
    <div class="input-group">
      <input type="password" v-model="form.password" placeholder="Password" />
    </div>
    <button @click="handleLogin" :disabled="loading" class="login-btn">
      {{ loading ? 'Logging in...' : 'Enter System' }}
    </button>
    <p v-if="errorMsg" class="error">{{ errorMsg }}</p>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '@/api/auth' // 确保你之前创建了 api/auth.js

const router = useRouter()
const loading = ref(false)
const errorMsg = ref('')

const form = reactive({
  username: '',
  password: ''
})

const handleLogin = async () => {
  if (!form.username || !form.password) {
    errorMsg.value = "请输入账号密码"
    return
  }

  loading.value = true
  try {
    // 1. 发送请求给 Java 后端
    const res = await login(form) 
    
    // 2. 假设后端返回 { token: 'xyz...', user: {...} }
    // 注意：根据你的 Java 后端 Result 结构调整，可能是 res.data.token
    const token = res.data?.token || 'mock-token-for-dev' 
    
    // 3. 存 Token
    localStorage.setItem('token', token)
    
    // 4. 跳转 Dashboard
    router.push('/')
    
  } catch (err) {
    console.error(err)
    errorMsg.value = "登录失败，请检查 Java 后端是否启动"
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.input-group { margin-bottom: 20px; }
input {
  width: 100%;
  padding: 12px;
  background: rgba(255,255,255,0.05);
  border: 1px solid rgba(255,255,255,0.2);
  border-radius: 8px;
  color: white;
  outline: none;
  font-size: 1rem;
}
input:focus { border-color: #42b983; }
.login-btn {
  width: 100%;
  padding: 12px;
  background: #42b983;
  border: none;
  border-radius: 8px;
  color: white;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s;
}
.login-btn:hover { background: #3aa876; transform: translateY(-2px); }
.login-btn:disabled { opacity: 0.7; cursor: not-allowed; }
.error { color: #ff6b6b; margin-top: 15px; font-size: 0.9rem; }
</style>