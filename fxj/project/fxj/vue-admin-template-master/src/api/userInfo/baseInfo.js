import request from '@/utils/request'

/**
 * 通过id获取用户基本信息
 */
export function getBaseInfo(id) {
  return request({
    url: `/info/baseInfo/${id}`,
    method: 'get'
  })
}

/**
 * 修改基本信息
 */
export function changeBaseInfo(baseInfo) {
  return request({
    url: `/info/baseInfo`,
    method: 'post',
    data: {
      id: baseInfo.id,
      username: baseInfo.username,
      name: baseInfo.name,
      gender: baseInfo.gender,
      birth: baseInfo.birth,
      age: baseInfo.age,
      sign: baseInfo.sign,
      phone: baseInfo.phone,
      email: baseInfo.email
    }
  })
}

/**
 * 修改基本信息
 */
export function submitQuestion(question, user) {
  return request({
    url: `/question`,
    method: 'post',
    data: {
      username: user,
      question: question
    }
  })
}
