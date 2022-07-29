package com.myweb.fxj.service.impl.main.status;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.myweb.fxj.mapper.status.NotificationMapper;
import com.myweb.fxj.pojo.info.BaseInfo;
import com.myweb.fxj.pojo.status.Notification;
import com.myweb.fxj.service.interfac.main.status.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public List<Notification> getNotificationList(int id) {
        QueryWrapper<Notification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("is_deleted", 0);// 没有删除的通知
        return notificationMapper.selectList(queryWrapper);
    }

    @Override
    public int updateNotification(Notification notification) {
        // 无num则是添加
        if(notification.getNum() == null) return notificationMapper.insert(notification);
        // 有num是更新
        else {
            UpdateWrapper<Notification> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", notification.getId());
            updateWrapper.eq("num", notification.getNum());
            return notificationMapper.update(notification, updateWrapper);
        }
    }
}
