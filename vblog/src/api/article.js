import request from '@/utils/request'

export function getArticles(title, viewsOrder, pageNum, pageSize) {
  return request({
    url: '/article',
    method: 'get',
    params: {
      title,
      viewsOrder,
      pageNum,
      pageSize
    }
  })
}

export function getArticleById(id) {
  return request({
    url: `/article/${id}`,
    method: 'get'
  })
}

export function saveArticle(article) {
  return request({
    url: '/article',
    method: 'post',
    data: {
      title: article.title,
      content: article.content,
      status: article.status,
      allowComment: article.allowComment,
      createTime: Date.now(),
      lastUpdateTime: Date.now(),
      sort: article.sort
    }
  })
}

export function updateArticle(article) {
  return request({
    url: '/article',
    method: 'put',
    data: {
      id: article.id,
      title: article.title,
      content: article.content,
      status: article.status,
      allowComment: article.allowComment,
      lastUpdateTime: Date.now(),
      sort: article.sort
    }
  })
}

export function deleteArticleById(id) {
  return request({
    url: `/article/${id}`,
    method: 'delete'
  })
}
