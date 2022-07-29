package com.myweb.fxj.controller.main.module.memory;

import com.myweb.fxj.common.utils.result.Result;
import com.myweb.fxj.common.vo.RecordVo;
import com.myweb.fxj.pojo.memory.Photo;
import com.myweb.fxj.pojo.memory.Record;
import com.myweb.fxj.service.interfac.main.module.memory.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/memory")
public class RecordController {
    @Autowired
    private RecordService recordServiceImpl;
    /**
     * 根据id和anotherId获取记录
     */
    @GetMapping("/record/{id}/{anotherId}")
    public Result getRecords(@PathVariable("id") int id, @PathVariable("anotherId")int anotherId) {
        List<Record> recordList = recordServiceImpl.getRecords(id);
        recordList.addAll(recordServiceImpl.getRecords(anotherId));
        recordList.sort(new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });// 对照片按时间序进行排序
        return Result.ok(recordList);
    }

    /**
     * 条件查询 todo 现在是只有年份信息，需要根据日期进行判断
     */
    @PostMapping("/record/query/{id}/{anotherId}")
    public Result getRecordsByQuery(@PathVariable("id") int id,
                                     @PathVariable("anotherId")int anotherId,
                                     @RequestBody RecordVo recordVo) {
        recordVo.setId(id);
        List<Record> recordList = recordServiceImpl.getRecordsByQuery(recordVo);
        recordVo.setId(anotherId);
        recordList.addAll(recordServiceImpl.getRecordsByQuery(recordVo));
        recordList.sort(new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });// 对照片按时间序进行排序
        return Result.ok(recordList);
    }

    /**
     * 更新记录
     */
    @PostMapping("/record")
    public Result updateRecord(@RequestBody Record record) {
        return Result.ok(recordServiceImpl.updateRecord(record));
    }

}
