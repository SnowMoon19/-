package com.myweb.fxj.pojo.status;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Clock {
    private int id;
    private boolean isClock;
    private Date date;
}
