import request from '@/utils/request'

export function getRecentlySevenDays() {
  return request({
    url: '/web/data/seven',
    method: 'get'
  })
}

export function getCategoryCount() {
  return request({
    url: '/web/category',
    method: 'get'
  })
}

export function getCommentCount(pageNum, pageSize) {
  return request({
    url: '/web/comment/count',
    method: 'get'
  })
}

export function getCommentsLimitEight() {
  return request({
    url: '/web/comment/eight',
    method: 'get'
  })
}

export function getAticleCount() {
  return request({
    url: '/web/article/count',
    method: 'get'
  })
}