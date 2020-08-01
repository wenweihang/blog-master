import axios from 'axios'
import store from '../store'
import router from '../router'
import {
  getToken
} from '@/utils/auth'
import {
  message
} from 'ant-design-vue'
// 导入NProgress
import NProgress from 'nprogress'

// 创建axios实例
const service = axios.create({
  baseURL: 'http://127.0.0.1:8081/blog/v1/',
  timeout: 15000
})

// request 拦截器
service.interceptors.request.use(config => {
  NProgress.start() // 进度条显示
  if (store.getters.token) {
    config.headers.Authorization = getToken()
  }
  return config
}, error => {
  Promise.reject(error)
})

// response 拦截器
service.interceptors.response.use(response => {
  NProgress.done() // 隐藏进度条
  // code 为非200是抛错
  const res = response.data
  if (res.code !== 200) {
    // 未登录或token过期
    if (res.code === 401) {
      message.error('登录过期')
      router.replace('/login')
    } else if (res.code === 403) {
      router.replace('/403')
    } else if (res.code === 404) {
      router.replace('/404')
    } else if (res.code === 400) {
      message.error(res.msg);
    } else {
      message.error('未知错误，请联系管理员');
    }
  } else {
    return response.data
  }
}, error => {
  return Promise.reject(error)
})

export default service
