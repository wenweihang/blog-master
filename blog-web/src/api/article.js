import request from '@/utils/request'

export function searchArticles(title, pageNum, pageSize) {
  return request({
    url: '/web/article/search',
    method: 'get',
    params: {
      title,
      pageNum,
      pageSize
    }
  })
}

export function getArticleCount() {
  return request({
    url: '/web/article/count',
    method: 'get'
  })
}

export function getArticleCategories() {
  return request({
    url: '/web/article/category',
    method: 'get'
  })
}

export function getArticleByCategoryId(id, pageNum, pageSize) {
  return request({
    url: `/web/article/category/${id}`,
    method: 'get',
    params: {
      pageNum,
      pageSize
    }
  })
}

export function getArchive() {
  return request({
    url: '/web/article/archive',
    method: 'get'
  })
}

export function getArticleByArchiveDate(date, pageNum, pageSize) {
  return request({
    url: '/web/article/date',
    method: 'get',
    params: {
      date,
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
