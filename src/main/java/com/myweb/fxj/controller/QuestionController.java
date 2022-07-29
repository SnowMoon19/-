package com.myweb.fxj.controller;

import com.myweb.fxj.common.utils.result.Result;
import com.myweb.fxj.pojo.Question;
import com.myweb.fxj.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class QuestionController {
    @Autowired
    private QuestionServiceImpl questionServiceImpl;

    @PostMapping("/question")
    public Result checkAccount(@RequestBody Question question) {
        questionServiceImpl.insertQuestion(question);
        return Result.ok(1);
    }

}
