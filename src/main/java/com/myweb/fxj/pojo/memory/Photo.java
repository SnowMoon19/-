package com.myweb.fxj.pojo.memory;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("memory_photo")
public class Photo {
    private Integer photographId;
    private Integer photoId;
    private String url;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date date;
    private Integer creator;// 创建者的id
    private String tag;
    private String comment;
    private Integer isDeleted;

    public Photo(Integer photographId, Integer photoId,String url, Date date, Integer creator, String tag, String comment, Integer isDeleted) {
        this.photographId = photographId;
        this.photoId = photoId;
        this.url = url;
        this.date = date;
        this.creator = creator;
        this.tag = tag;
        this.comment = comment;
        this.isDeleted = isDeleted;
    }
}
