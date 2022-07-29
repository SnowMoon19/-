package com.myweb.fxj.mapper.module.memory;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myweb.fxj.common.vo.RecordVo;
import com.myweb.fxj.pojo.memory.Record;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordMapper extends BaseMapper<Record> {

    /**
     * 根据条件查询
     */
    List<Record> getRecordByQuery(RecordVo recordVo);
}
