package com.myweb.fxj.pojo.event;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("event_task")
public class Task {
    private Integer typeId;
    private Integer num;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date startTime;
    private Date endTime;
    private String status;
    private Integer creator;// 创建者为自己或对方，0是自己，1是对方
    private String tag;
    private String comment;
    private Integer isDeleted;

}
