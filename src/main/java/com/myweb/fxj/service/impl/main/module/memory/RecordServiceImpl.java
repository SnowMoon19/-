package com.myweb.fxj.service.impl.main.module.memory;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.myweb.fxj.common.vo.RecordVo;
import com.myweb.fxj.mapper.module.memory.RecordMapper;
import com.myweb.fxj.pojo.memory.Record;
import com.myweb.fxj.service.interfac.main.module.memory.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public List<Record> getRecords(int id) {
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("is_deleted", 0);
        return recordMapper.selectList(queryWrapper);
    }

    @Override
    public List<Record> getRecordsByQuery(RecordVo recordVo) {
        return recordMapper.getRecordByQuery(recordVo);
    }

    @Override
    public int updateRecord(Record record) {
        if(record.getRecordId() == null) return recordMapper.insert(record);
        UpdateWrapper<Record> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("record_id", record.getRecordId());
        return recordMapper.update(record, updateWrapper);
    }
}

