import request from '@/utils/request'

export function getCategories(name, pageNum, pageSize) {
  return request({
    url: '/category',
    method: 'get',
    params: {
      name,
      pageNum,
      pageSize
    }
  })
}

export function searchCategory(name, pageNum, pageSize) {
  return request({
    url: '/category/search',
    method: 'get',
    params: {
      name,
      pageNum,
      pageSize
    }
  })
}

export function getCategoryById(id) {
  return request({
    url: `/category/${id}`,
    method: 'get'
  })
}

export function saveCategory(category) {
  return request({
    url: '/category',
    method: 'post',
    data: {
      name: category.name,
      enabled: category.enabled,
      createTime: Date.now(),
      sort: category.sort
    }
  })
}

export function updateCategory(category) {
  return request({
    url: '/category',
    method: 'put',
    data: {
      id: category.id,
      name: category.name,
      enabled: category.enabled,
      sort: category.sort
    }
  })
}

export function deleteCategoryById(id) {
  return request({
    url: `/category/${id}`,
    method: 'delete'
  })
}
