import request from '@/utils/request'

export function saveComment(visitorNickname, visitorEmail, content, articleId) {
  return request({
    url: '/web/comment',
    method: 'post',
    data: {
      visitorNickname,
      visitorEmail,
      content,
      articleId,
      createTime: Date.now(),
      enabled: true
    }
  })
}

export function getCommentsByArticleId(id) {
  return request({
    url: `/web/comment/${id}`,
    method: 'get'
  })
}