package com.myweb.fxj.pojo.info;

import lombok.Data;

/**
 * 用户信息类
 */
@Data
public class UserInfo {
    private Account account;
    private Model model;
    private BaseInfo baseInfo;
}
