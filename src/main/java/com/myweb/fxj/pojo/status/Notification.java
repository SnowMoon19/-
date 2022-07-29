package com.myweb.fxj.pojo.status;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@TableName("status_notification")
public class Notification {
    private Integer id;// id
    private Integer num;// 通知编号，现在通知编号为自增键，所有人的通知都共享num，查找的时候只根据id把对应的num发送过去
    private String type;// 通知类型
    private String context;// 内容
    private Integer isClicked;// 是否点击处理过
    private Integer isDeleted;// 是否删除
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date time;// 通知时间
}
