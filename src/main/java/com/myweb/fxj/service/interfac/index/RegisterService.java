package com.myweb.fxj.service.interfac.index;

import com.myweb.fxj.pojo.info.Account;

public interface RegisterService {
    boolean registerCheck(Account account);

    boolean addAccount(Account account);
}
