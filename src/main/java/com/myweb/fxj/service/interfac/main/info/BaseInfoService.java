package com.myweb.fxj.service.interfac.main.info;

import com.myweb.fxj.pojo.info.BaseInfo;

public interface BaseInfoService {
    BaseInfo getBaseInfo(int id);

    int updateBaseInfo(BaseInfo baseInfo);
}
