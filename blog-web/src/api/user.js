import request from '@/utils/request'

export function getUserDetail() {
  return request({
    url: '/web/user',
    method: 'get'
  })
}