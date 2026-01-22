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