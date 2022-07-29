package com.myweb.fxj.service.interfac.main.status;

import com.myweb.fxj.pojo.status.Notification;

import java.util.List;

public interface NotificationService {
    /**
     * 返回对应id的所有未删除的通知
     */
    List<Notification> getNotificationList(int id);


    /**
     * 更新通知
     */
    int updateNotification(Notification notification);
}
