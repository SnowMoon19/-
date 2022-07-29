package com.myweb.fxj.mapper.module.memory;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myweb.fxj.common.vo.PhotographVo;
import com.myweb.fxj.pojo.memory.Photograph;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotographMapper extends BaseMapper<Photograph> {
    /**
     * 条件查询相册id
     */
    List<Photograph> getPhotographByQuery(PhotographVo photographVo);
}
