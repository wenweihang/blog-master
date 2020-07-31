import request from '@/utils/request'

export function saveReply(content, enabled, commentId) {
  return request({
    url: '/reply',
    method: 'post',
    data: {
      content,
      enabled,
      createTime: Date.now(),
      commentId
    }
  })
}

export function updateReply(reply) {
  return request({
    url: '/reply',
    method: 'put',
    data: {
      id: reply.id,
      content: reply.content,
      enabled: reply.enabled
    }
  })
}

export function deleteReplyById(id) {
  return request({
    url: `/reply/${id}`,
    method: 'delete'

  })
}
