package com.myweb.fxj.pojo.event;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("event_task_type")
public class TaskType {
    private Integer id;
    private Integer typeId;
    private String type;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;
    private Integer year;
    private Integer isDeleted;

    public TaskType(int id, int year) {
        this.id = id;
        this.year = year;
    }
}
