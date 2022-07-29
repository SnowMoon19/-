import request from '@/utils/request'

/**
 * 得到record列表
 */
export function getRecordList(id, anotherId) {
  return request({
    url: `/memory/record/${id}/${anotherId}`,
    method: 'get'
  })
}

/**
 * 条件查询record列表
 */
export function getRecordListByQuery(id, anotherId, searchObj) {
  return request({
    url: `/memory/record/query/${id}/${anotherId}`,
    method: 'post',
    data: searchObj
  })
}

export function updateRecord(record) {
  return request({
    url: `/memory/record`,
    method: 'post',
    data: {
      id: record.id,
      recordId: record.recordId,
      date: record.date,
      num: record.num,
      content: record.content,
      type: record.type,
      tag: record.tag,
      isDeleted: record.isDeleted
    }
  })
}

