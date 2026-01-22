import { createApp } from 'vue'         // 导入创建应用的工厂函数
import { createPinia } from 'pinia'     // 导入状态管理工具（全局变量管家）
import App from './App.vue'             // 导入根组件（所有页面的外壳）
import router from './router'           // 导入路由配置（控制页面跳转）
import './assets/main.css'              // 导入全局 CSS 样式

const app = createApp(App)
// 相当于创建了vue实例(相当于创建了操作系统)，初始化了一台计算机。

//========================================
app.use(createPinia())
// 相当于安装了状态管理工具（全局变量管家）
app.use(router)
// 相当于安装了路由配置（控制页面跳转）

//这两个都是vue的官方核心插件
//========================================

app.mount('#app')
// 相当于启动了计算机,`#app`: 对应的是项目根目录下 index.html 文件里的一个 ID 为 app 的 <div> 标签。


//启动浏览器的时候，瞬间会生成一个新的index.html文件，这个文件只有一行:<div id="app"></div>，然后app.mount('#app')这个代码的意思就是将vue实例挂载到这个div上。
//整体来说，就是
// 浏览器打开网页：首先加载 index.html。此时用户只能看到一个白板（因为 <div id="app"> 里面是空的）。

// 加载脚本：index.html 底部通常有一行 <script type="module" src="/src/main.js"></script>。这相当于剧院开门后，立马把导演 (main.js) 叫来了。

// Vue 启动：导演 (main.js) 开始执行：

// createApp(App)：召集演员，准备剧本（初始化 Vue）。

// use(router)：确认演出流程（加载路由）。

// use(pinia)：确认道具清单（加载状态管理）。

// 挂载 (Mount)：最后，导演大喊一声 app.mount('#app')！

// 结果：Vue 瞬间接管了 index.html 里的 <div id="app">，把 App.vue 里的内容塞进去。用户这下终于看到了漂亮的界面。


