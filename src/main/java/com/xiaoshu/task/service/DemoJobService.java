package com.xiaoshu.task.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DemoJobService {

    public void testService(){
        System.out.println("今天是个好日子，明天又是好日子");
    }
}
