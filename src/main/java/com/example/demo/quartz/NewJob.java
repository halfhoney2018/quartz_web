package com.example.demo.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @program:
 * @description:
 * @author: halfhoney2018@gmail.com
 * @create: 2020/4/13 11:19
 */
@Slf4j
public class NewJob implements IBaseJob {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      log.error("New Job:执行时间："+new Date());
    }
}
