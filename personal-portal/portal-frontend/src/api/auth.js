import request from './request'

// 这里的 /api/java 会被 vite.config.js 转发到 8080
export const login = (data) => {
  return request({
    url: '/api/java/auth/login',
    method: 'post',
    data
  })
}

export const register = (data) => {
  return request({
    url: '/api/java/auth/register',
    method: 'post',
    data
  })
}