import request from '@/utils/request'

/**
 * 通过id获取用户未删除的所有通知
 */
export function getNotificationList(id) {
  return request({
    url: `/status/notification/${id}`,
    method: 'get'
  })
}

/**
 * 更新通知
 */
export function updateNotification(notification) {
  return request({
    url: `/status/notification`,
    method: 'post',
    data: {
      id: notification.id,
      num: notification.num,
      type: notification.type,
      context: notification.context,
      isClicked: notification.isClicked,
      isDeleted: notification.isDeleted,
      time: notification.time
    }
  })
}

