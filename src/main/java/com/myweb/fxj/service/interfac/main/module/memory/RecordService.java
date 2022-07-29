package com.myweb.fxj.service.interfac.main.module.memory;

import com.myweb.fxj.common.vo.RecordVo;
import com.myweb.fxj.pojo.memory.Record;

import java.util.List;

public interface RecordService {
    /**
     * 根据自己和对方的id获取记录列表
     */
    List<Record> getRecords(int id);

    /**
     * 条件查询
     */
    List<Record> getRecordsByQuery(RecordVo recordVo);

    /**
     * 更新记录
     */
    int updateRecord(Record record);
}
