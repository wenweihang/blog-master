import request from '@/utils/request'

export function getComments(pageNum, pageSize) {
  return request({
    url: '/comment',
    method: 'get',
    params: {
      pageNum,
      pageSize
    }
  })
}

export function updateEnabled(id, enabled) {
  return request({
    url: '/comment',
    method: 'put',
    data: {
      id,
      enabled
    }
  })
}

export function deleteCommentById(id) {
  return request({
    url: `/comment/${id}`,
    method: 'delete'
  })
}
