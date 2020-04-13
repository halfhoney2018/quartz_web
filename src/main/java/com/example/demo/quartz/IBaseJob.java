package com.example.demo.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @program: 接口描述
 * @description:
 * @author: halfhoney2018@gmail.com
 * @create: 2020/4/13 11:13
 */
public interface IBaseJob extends Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException;
}
