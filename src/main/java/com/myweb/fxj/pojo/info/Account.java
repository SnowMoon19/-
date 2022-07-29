package com.myweb.fxj.pojo.info;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 账号类
 */
@Data
@TableName("info_account")
public class Account {
    private Integer id;
    private Integer anotherId;
    private String username;
    private String password;
    private String name;
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
