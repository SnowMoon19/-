import request from '@/utils/request'

/**
 * 查询当前任务版下所有任务
 * typeId决定任务版
 */
export function getTaskList(typeId) {
  return request({
    url: `/event/task/${typeId}`,
    method: 'get'
  })
}

/**
 * 添加任务
 */

/**
 * 删除任务
 */

/**
 * 修改任务
 */
export function updateTask(task) {
  return request({
    url: `/event/task`,
    method: 'post',
    data: {
      id: task.id,
      typeId: task.typeId,
      num: task.num,
      content: task.content,
      status: task.status,
      startTime: task.startTime,
      endTime: task.endTime,
      creator: task.creator,
      tag: task.tag,
      comment: task.comment,
      isDeleted: task.isDeleted
    }
  })
}

/**
 * 获取任务版列表
 */
export function getTaskTypeList(id, year) {
  return request({
    url: `/event/taskType/${id}/${year}`,
    method: 'get'
  })
}

/**
 * 新建任务板
 */
export function updateTaskType(taskType) {
  return request({
    url: `/event/taskType/`,
    method: 'post',
    data: {
      id: taskType.id,
      createTime: taskType.createTime,
      type: taskType.type,
      year: taskType.year,
      isDeleted: taskType.isDeleted
    }
  })
}

