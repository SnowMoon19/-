package com.myweb.fxj.service.impl.index;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myweb.fxj.mapper.info.AccountMapper;
import com.myweb.fxj.pojo.info.Account;
import com.myweb.fxj.service.interfac.index.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    AccountMapper accountMapper;

    /**
     * 根据账号和密码查询Account
     */
    @Override
    public Account loginCheck(Account account) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", account.getUsername());
        queryWrapper.eq("password", account.getPassword());
        return accountMapper.selectOne(queryWrapper);
    }
}
