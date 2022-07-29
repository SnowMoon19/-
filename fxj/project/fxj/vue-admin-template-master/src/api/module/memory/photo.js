import request from '@/utils/request'

/**
 * 得到相册id列表
 */
export function getPhotoIdList(id, anotherId) {
  return request({
    url: `/memory/photograph/${id}/${anotherId}`,
    method: 'get'
  })
}

export function getPhotoList(photographId) {
  return request({
    url: `/memory/photo/${photographId}`,
    method: 'get'
  })
}

/**
 * 条件查询获取相册id
 */
export function getPhotographListByQuery(id, anotherId, searchInline) {
  return request({
    url: `/memory/photo/query/${id}/${anotherId}`,
    method: 'post',
    data: {
      name: searchInline.year
    }
  })
}

