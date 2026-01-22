import axios from 'axios'
// 导入路由(这个是官方插件)
import router from '@/router'
//这个是src/router，是我自己写的路由。之前在vite.config.js中配置了路径别名，所以这里可以使用`@/router`来代替`portal-frontend/src/router`。

//创建Axios实例
const service = axios.create({
  timeout: 5000 //请求超时时间
})










//请求拦截器
service.interceptors.request.use(
  (config) => {
    // 从本地存储获取 Token
    const token = localStorage.getItem('token')
    if (token) {
      // 注意：这里假设后端需要 Bearer 格式
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)






//响应拦截器
service.interceptors.response.use(
  (response) => {
    return response.data
  },
  (error) => {
    // 统一处理 401 未授权
    if (error.response && error.response.status === 401) {
      localStorage.removeItem('token')
      router.push('/login')
    }
    return Promise.reject(error)
  }
)

export default service



//