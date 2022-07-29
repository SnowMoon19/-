package com.myweb.fxj.service.impl;

import com.myweb.fxj.mapper.LoginInfoMapper;
import com.myweb.fxj.pojo.login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class loginInfoServiceImpl {
    @Autowired
    private LoginInfoMapper loginInfoMapper;

    public void insertLoginInfo(login l) {
        loginInfoMapper.insert(l);
    }
}
