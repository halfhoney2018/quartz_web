package com.example.demo.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;


/**
 * @program: 测试demo
 * @description:
 * @author: halfhoney2018@gmail.com
 * @create: 2020/4/13 11:15
 */
@Slf4j
public class JobDemo implements IBaseJob{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.error("当前Job执行时间："+new Date());
    }
}
