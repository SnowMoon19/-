package com.myweb.fxj.pojo.info;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 用户的基本信息类
 */
@Data
@AllArgsConstructor
@TableName("info_baseinfo")
public class BaseInfo {
    private Integer id;// id
    private String username;// 账号名
    private String name;// 用户名
    private String gender;// 性别
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date birth;// 生日
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date registerDate;// 注册日期
    private Integer age;// 年龄
    private Long phone;// 手机号
    private String email;// 邮箱
    private String sign;// 星座
    private String anotherName;// 另一半的名称
    private Integer anotherId;// 另一半的id
    private Integer modelId;// 当前相处模式
}
