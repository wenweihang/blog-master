import axios from 'axios'
import {
  message
} from 'ant-design-vue'

// 创建axios实例
const service = axios.create({
  baseURL: 'http://127.0.0.1:8081/blog/v1/',
  timeout: 15000
})

// response 拦截器
service.interceptors.response.use(
  response => {
    // code 为非200是抛错
    const res = response.data
    if (res.code !== 200) {
      // 未登录或token过期
      if (res.code === 400) {
        console.log('400: ', res.msg)
        message.error(res.msg);
      } else {
        console.log('500: ', res.msg)
        message.error('未知错误，请联系管理员');
      }
    } else {
      return response.data
    }
  }, error => {
    return Promise.reject(error)
  })

export default service
