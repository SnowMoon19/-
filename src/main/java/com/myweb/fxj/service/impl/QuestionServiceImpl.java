package com.myweb.fxj.service.impl;

import com.myweb.fxj.mapper.QuestionMapper;
import com.myweb.fxj.pojo.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class QuestionServiceImpl {
    @Autowired
    private QuestionMapper questionMapper;

    public void insertQuestion(Question question) {
        questionMapper.insert(question);
    }
}
