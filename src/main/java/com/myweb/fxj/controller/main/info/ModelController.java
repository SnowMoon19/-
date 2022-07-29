package com.myweb.fxj.controller.main.info;

import com.myweb.fxj.common.utils.result.Result;
import com.myweb.fxj.service.interfac.main.info.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/info")
public class ModelController {
    @Autowired
    private ModelService modelServiceImpl;

    @GetMapping("/model/{id}")
    public Result getModel(@PathVariable("id") int id) {
        return Result.ok(modelServiceImpl.getModel(id));
    }
}
