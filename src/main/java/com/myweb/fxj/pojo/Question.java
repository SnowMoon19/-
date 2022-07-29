package com.myweb.fxj.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 临时保存一些问题
 */
@Data
@TableName("question")
public class Question {
    private String username;
    private String question;
}
