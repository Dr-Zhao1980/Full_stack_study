import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'




export default defineConfig({
  plugins: [vue()],

//================================================================
// 整个文件可以分成两个部分：
// 1，路径别名配置
// 2，代理配置
//================================================================







//================================================================
//1，路径别名配置
//================================================================
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
//这个内容是路径别名配置，相当于将 `/src` 设置为 `@`，之后可以直接使用`@/api`来代替`portal-frontend/src/api`。
//================================================================










//================================================================
//2，代理配置（主要问题是区分多后端，比如对于java有一个login，对于python也有一个login，不用代理就会导致混乱）
//================================================================
  server: {
    port: 5173,
    proxy: {
      // Java 后端代理 (Spring Boot)
      '/api/java': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/java/, '') 
        // 例子: 前端发 /api/java/auth/login -> 后端收 /api/auth/login
      },
      // Python 后端代理 (FastAPI)
      '/api/python': {
        target: 'http://localhost:8000',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/python/, '') 
        // 例子: 前端发 /api/python/analyze -> 后端收 /analyze
      }
    }
  }

  // port: 5173,表示前端运行在5173端口
  // `/api/java`:表示前端发送的请求以/api/java开头,target: 'http://localhost:8080',表示将请求转发到8080端口,
  // changeOrigin: true,表示改变请求的源头，rewrite: (path) => path.replace(/^\/api\/java/, '') 
  //例子: 前端发 /api/java/auth/login -> 后端收 /auth/login，这个相当于是重写。

  //`/api/python`:表示前端发送的请求以/api/python开头,target: 'http://localhost:8000',表示将请求转发到8000端口,
  // changeOrigin: true,表示改变请求的源头，rewrite: (path) => path.replace(/^\/api\/python/, '') 
  //例子: 前端发 /api/python/analyze -> 后端收 /analyze，这个相当于是重写。
  //所以，综上所述，前端发送的请求有两种：
  //1，`/api/java/auth/login` -> 后端收 `/auth/login`
  //2，`/api/python/analyze` -> 后端收 `/analyze`
  //综上所述，统一撕掉了/api/java和/api/python的标签。



  //虽然本来我可以直接在后端写@PostMapping("/api/java/login")，但是这样会暴露技术栈，同时不利于后期维护。
//================================================================
})