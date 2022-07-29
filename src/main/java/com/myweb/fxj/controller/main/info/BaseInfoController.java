package com.myweb.fxj.controller.main.info;

import com.myweb.fxj.common.utils.result.Result;
import com.myweb.fxj.pojo.info.BaseInfo;
import com.myweb.fxj.service.interfac.main.info.BaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/info")
public class BaseInfoController {
    @Autowired
    BaseInfoService baseInfoServiceImpl;

    /**
     * 获取基本信息
     */
    @GetMapping("/baseInfo/{id}")
    public Result getBaseInfo(@PathVariable("id") int id) {
        BaseInfo baseInfo = baseInfoServiceImpl.getBaseInfo(id);
        return Result.ok(baseInfo);
    }

    /**
     * 修改基本信息
     */
    @PostMapping("/baseInfo")
    public Result updateBaseInfo(@RequestBody BaseInfo baseInfo) {
        return Result.ok(baseInfoServiceImpl.updateBaseInfo(baseInfo));
    }

    // todo 根据用户名申请与另一半绑定关系
    @PostMapping("/baseInfo/askForAnother/{username}")
    public Result askForAnother(@PathVariable("username") String username) {
        return null;
    }

}
