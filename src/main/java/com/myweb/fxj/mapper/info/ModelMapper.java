package com.myweb.fxj.mapper.info;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myweb.fxj.pojo.info.Model;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelMapper extends BaseMapper<Model> {
    Model getModel(int id);
}
