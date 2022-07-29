package com.myweb.fxj.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 临时用来记录登录情况
 */
@Data
@TableName("login_info")
public class login {
    private Integer id;
    private Date time;
}

