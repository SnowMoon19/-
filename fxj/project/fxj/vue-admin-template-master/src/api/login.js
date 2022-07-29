import request from '@/utils/request'

/**
 * 登录接口，发送用户民和密码
 */
export function login(username, password) {
  return request({
    url: `/index/login/${username}/${password}`,
    method: 'get'
    // data: {
    //   username,
    //   password
    // }
  })
}

// /**
//  * 获取用户信息接口
//  */
// export function getInfo(token) {
//   return request({
//     url: '/index/info',
//     method: 'get',
//     params: { token }
//   })
// }

/**
 * 登出接口
 */
export function logout(id) {
  return request({
    url: `/index/logout/${id}`,
    method: 'post'
  })
}
