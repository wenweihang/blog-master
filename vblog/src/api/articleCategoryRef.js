import request from '@/utils/request'

export function getCategoryByArticleId(id) {
  return request({
    url: `/assign/category/${id}`,
    method: 'get'
  })
}

export function addCategoryForArticle(articleId, categoryName) {
  return request({
    url: '/assign/category',
    method: 'post',
    data: {
      articleId,
      categoryName
    }
  })
}
