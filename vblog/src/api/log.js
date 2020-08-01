import request from '@/utils/request'

export function getLogs(selectLevel, selectDate, pageNum, pageSize) {
  return request({
    url: '/log',
    method: 'get',
    params: {
      level: selectLevel != 'ALL' ? selectLevel : '',
      date: selectDate,
      pageNum,
      pageSize
    }
  })
}

export function getLevels() {
  return request({
    url: '/log/level',
    method: 'get'
  })
}