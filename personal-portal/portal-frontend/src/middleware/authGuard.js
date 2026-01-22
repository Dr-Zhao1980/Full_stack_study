import { features } from '@/registry/featureList'

export function authGuard(to, from, next) {
  // 1. 检查目标路由是否需要登录
  // 这里通过对比路径来查找 feature 配置
  // 注意：实际项目中也可以直接在 router 的 meta 字段里写
  const targetFeature = features.find(f => to.path.startsWith(f.path))
  
  const requireAuth = targetFeature ? targetFeature.requireAuth : false
  const token = localStorage.getItem('token')

  if (requireAuth && !token) {
    // 需要登录但没 Token -> 去登录页
    next('/login')
  } else {
    // 放行
    next()
  }
}