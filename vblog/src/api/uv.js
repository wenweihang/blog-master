import request from '@/utils/request'

export function getCurrentDay() {
  return request({
    url: '/web/uv/current',
    method: 'get'
  })
}

export function getUvAmount() {
  return request({
    url: '/web/uv/amount',
    method: 'get'
  })
}
