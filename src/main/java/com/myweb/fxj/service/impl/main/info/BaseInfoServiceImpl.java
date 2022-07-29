package com.myweb.fxj.service.impl.main.info;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.myweb.fxj.mapper.info.BaseInfoMapper;
import com.myweb.fxj.pojo.info.BaseInfo;
import com.myweb.fxj.service.interfac.main.info.BaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseInfoServiceImpl implements BaseInfoService {
    @Autowired
    BaseInfoMapper baseInfoMapper;

    @Override
    public BaseInfo getBaseInfo(int id) {
        return baseInfoMapper.selectById(id);
    }

    @Override
    public int updateBaseInfo(BaseInfo baseInfo) {
        UpdateWrapper<BaseInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", baseInfo.getId());
        return baseInfoMapper.update(baseInfo, updateWrapper);
    }

}
