import request from '@/utils/request'

export function getRoles(roleName, pageNum, pageSize) {
  return request({
    url: '/role',
    method: 'get',
    params: {
      roleName,
      pageNum,
      pageSize
    }
  })
}

export function searchRoles(roleName, pageNum, pageSize) {
  return request({
    url: '/role/search',
    method: 'get',
    params: {
      roleName,
      pageNum,
      pageSize
    }
  })
}

export function getRoleById(id) {
  return request({
    url: `/role/${id}`,
    method: 'get'
  })
}

export function saveRole(role) {
  return request({
    url: '/role',
    method: 'post',
    data: {
      name: role.name,
      description: role.description,
      createTime: Date.now()
    }
  })
}

export function updateRole(role) {
  return request({
    url: '/role',
    method: 'put',
    data: {
      id: role.id,
      name: role.name,
      description: role.description
    }
  })
}

export function deleteRoleById(id) {
  return request({
    url: `/role/${id}`,
    method: 'delete'
  })
}
