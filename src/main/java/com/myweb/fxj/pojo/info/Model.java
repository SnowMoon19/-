package com.myweb.fxj.pojo.info;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 相处模式类
 */
@Data
@AllArgsConstructor
@TableName("info_model")
public class Model {
    private Integer id;// id
    private Integer modelId;// 模式id
    private String type;// 类型
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date startTime;// 开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date endTime;// 结束时间
    private Integer creator;// 创建者id
    private Integer over_1;// 创建者选择结束
    private Integer over_2;// 另一位选择结束
    private String nextType;// 请求为下一个模式

}


