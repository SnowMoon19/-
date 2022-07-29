package com.myweb.fxj.pojo.status;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Mood {
    private Integer id;
    private String mood;
    private Date time;
}
