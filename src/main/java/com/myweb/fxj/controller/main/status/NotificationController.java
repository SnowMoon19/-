package com.myweb.fxj.controller.main.status;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myweb.fxj.common.utils.result.Result;
import com.myweb.fxj.pojo.status.Notification;
import com.myweb.fxj.service.interfac.main.status.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/status")
public class NotificationController {
    @Autowired
    private NotificationService notificationServiceImpl;

    /**
     * 获取通知列表
     */
    @GetMapping("/notification/{id}")
    public Result getNotificationList(@PathVariable("id") int id) {
        List<Notification> notificationList = notificationServiceImpl.getNotificationList(id);
        return Result.ok(notificationList);
    }

    /**
     * 更新通知
     */
    @PostMapping("/notification")
    public Result updateNotification(@RequestBody Notification notification) {
        return Result.ok(notificationServiceImpl.updateNotification(notification));
    }
}
