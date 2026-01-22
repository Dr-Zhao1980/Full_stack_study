//==================================================================
import { createRouter, createWebHistory } from 'vue-router'
import { authGuard } from '@/middleware/authGuard'
// 布局
import MainLayout from '@/layouts/MainLayout.vue'
import AuthLayout from '@/layouts/AuthLayout.vue'
import ModuleLayout from '@/layouts/ModuleLayout.vue'

// 视图
import Dashboard from '@/views/Dashboard.vue'
import Login from '@/views/Login.vue'

// 这一部分是导入相关的一些功能，包括视图，布局之类的 
//==================================================================





//==================================================================
// 模块 (懒加载)
const MoodMain = () => import('@/modules/mood/MoodMain.vue')
const SnakeMain = () => import('@/modules/snake-java/SnakeMain.vue')
const TetrisMain = () => import('@/modules/tetris-py/TetrisMain.vue')
const BlogView = () => import('@/modules/blog/BlogView.vue')
// 懒加载的意思是只有在需要的时候才加载模块，而不是在应用启动时加载所有模块，防止打开启动页面的时间过长
//==================================================================











//==================================================================
const routes = [
  // 1. 认证路由 (登录页)
  {
    path: '/login',                   // 当地址栏是 .../login
    component: AuthLayout,            // 1. 先加载“认证布局”这个画框
    children: [                       // 2. 在画框的 <router-view> 位置里...
      { path: '', component: Login }   // 3. 嵌入“Login”这张画
    ]
  },
  // 2. 主平台路由 (仪表盘)
  {
    path: '/',                        // 当地址栏是 .../
    component: MainLayout,            // 1. 先加载“主平台布局”这个画框
    children: [                       // 2. 在画框的 <router-view> 位置里...
      { path: '', component: Dashboard } // 3. 嵌入“Dashboard”这张画
    ]
  },
  // 3. 模块路由 (独立功能)
  {
    path: '/modules',                   // 当地址栏是 .../modules
    component: ModuleLayout,            // 1. 先加载“模块布局”这个画框
    children: [                         // 2. 在画框的 <router-view> 位置里...
      { path: 'mood', component: MoodMain }, // 3. 嵌入“MoodMain”这张画
      { path: 'snake', component: SnakeMain }, // 3. 嵌入“SnakeMain”这张画
      { path: 'tetris', component: TetrisMain }, // 3. 嵌入“TetrisMain”这张画
      { path: 'blog', component: BlogView } // 3. 嵌入“BlogView”这张画
    ]
  }
]
//==================================================================























//==================================================================
// 创建路由实例
const router = createRouter({
  history: createWebHistory(),
  routes
})
//==================================================================













//==================================================================
//激活路由守卫
// 应用守卫
router.beforeEach(authGuard)
export default router
//==================================================================