package com.myweb.fxj.pojo.memory;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("memory_record")
public class Record {
    private String type; // 记录类型
    private Integer recordId; // 记录id
    private Integer id; // 记录属于的id
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date date; // 记录日期
    private String content; // 记录内容
    private String tag;
    private Integer isDeleted;
}
