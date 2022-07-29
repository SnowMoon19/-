import request from '@/utils/request'

/**
 * 通过id获取当前模式
 */
export function getModel(id) {
  return request({
    url: `/info/model/${id}`,
    method: 'get'
  })
}
