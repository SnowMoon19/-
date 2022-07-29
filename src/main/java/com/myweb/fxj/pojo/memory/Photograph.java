package com.myweb.fxj.pojo.memory;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("memory_photograph")
public class Photograph {
    private Integer id;
    private Integer photographId;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;
    private Integer isDeleted;

    public Photograph(Integer id, Integer photographId, String name, Date createTime, Integer isDeleted) {
        this.id = id;
        this.photographId = photographId;
        this.name = name;
        this.createTime = createTime;
        this.isDeleted = isDeleted;
    }
}
