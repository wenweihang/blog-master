import request from '@/utils/request'

export function getRoleByUserId(id) {
  return request({
    url: `/assign/role/${id}`,
    method: 'get'
  })
}

export function addRoleForUser(userId, roleNames) {
  return request({
    url: '/assign/role',
    method: 'post',
    data: {
      userId,
      roleNames
    }
  })
}
