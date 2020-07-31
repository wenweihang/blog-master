import request from '@/utils/request'

export function getpolicy() {
  return request({
    url: '/oss/policy',
    method: 'get'
  })
}
