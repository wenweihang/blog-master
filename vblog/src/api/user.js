import request from '@/utils/request'

export function getUsers(username, pageNum, pageSize) {
  return request({
    url: '/user',
    method: 'get',
    params: {
      username,
      pageNum,
      pageSize
    }
  })
}

export function searchUsers(username, pageNum, pageSize) {
  return request({
    url: '/user/search',
    method: 'get',
    params: {
      username,
      pageNum,
      pageSize
    }
  })
}

export function getUserById(id) {
  return request({
    url: `/user/${id}`,
    method: 'get'
  })
}

export function saveUser(user) {
  return request({
    url: '/user',
    method: 'post',
    data: {
      username: user.username,
      password: user.password,
      nickname: user.nickname,
      email: user.email,
      intro: user.intro,
      avatar: user.avatar,
      enabled: user.enabled,
      createTime: Date.now()
    }
  })
}

export function updateUser(user) {
  return request({
    url: '/user',
    method: 'put',
    data: {
      id: user.id,
      username: user.username,
      password: user.password,
      nickname: user.nickname,
      email: user.email,
      intro: user.intro,
      avatar: user.avatar,
      enabled: user.enabled
    }
  })
}

export function updateUserPassword(id, rawPassword, newPassword) {
  return request({
    url: '/user/password',
    method: 'post',
    data: {
      id,
      rawPassword,
      newPassword
    }
  })
}

export function deleteUserById(id) {
  return request({
    url: `/user/${id}`,
    method: 'delete'
  })
}
