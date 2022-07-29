package com.myweb.fxj.service.impl.index;

import com.myweb.fxj.mapper.info.AccountMapper;
import com.myweb.fxj.pojo.info.Account;
import com.myweb.fxj.service.interfac.index.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    AccountMapper accountMapper;

    /**
     * 根据账号和密码查询Account
     */
    @Override
    public boolean registerCheck(Account account) {
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("username", account.getUsername());
        List<Account> accountList = accountMapper.selectByMap(columnMap);
        System.out.println(accountList);
        // 如果不存在返回true,存在则返回false
        return accountList.isEmpty();
    }

    /**
     * 添加账号
     * id是自增的，成功返回1
     */
    @Override
    public boolean addAccount(Account account) {
        int operate = accountMapper.insert(account);
        return operate == 1;
    }


}
