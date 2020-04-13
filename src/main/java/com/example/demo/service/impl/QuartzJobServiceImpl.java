package com.example.demo.service.impl;

import com.example.demo.entity.JobAndTrigger;
import com.example.demo.quartz.IBaseJob;
import com.example.demo.service.IJobAndTriggerService;
import com.example.demo.service.IQuartzJobService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program:
 * @description: 定时器相关操作
 * @author: halfhoney2018@gmail.com
 * @create: 2020/4/13 14:01
 */
@Service
@Slf4j
public class QuartzJobServiceImpl implements IQuartzJobService {
    //通过名称来进行注入bean
    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    @Autowired
    private IJobAndTriggerService jobAndTriggerServiceervice;
    @Override
    public Map<String,Object> addJob(String jobClassName, String jobGroupName, String cronExpression) throws SchedulerException,  NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        HashMap<String, Object> map = new HashMap<>();
        //启动调度器
        scheduler.start();
        //构建job信息
        JobDetail jobDetail= null;
        try {
            jobDetail = JobBuilder.newJob(getClasszz(jobClassName).getClass()).withIdentity(jobClassName,jobGroupName).build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("class类未找到："+e);
            map.put("state","false");
            map.put("msg","当前任务出错，原因为：请求的ClassName不存在");
            return map;
        }
        //任务执行时间
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName).withSchedule(cronScheduleBuilder).build();
        try{
            Date date = scheduler.scheduleJob(jobDetail, trigger);
            log.info(date.toString());
        }catch (SchedulerException e){
            log.error("定时任务创建失败");
//            log.error("class类未找到："+e);
            map.put("state","false");
            map.put("msg",e);
            return map;
//            throw new Exception("创建定时任务失败");
        }
        map.put("state","true");
        map.put("msg","定时任务创建成功");
        return map;
    }

    @Override
    public void pausejob(String jobClassName, String jobGroupName) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jobClassName,jobGroupName));
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
            Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
            log.info("当前定时器状态："+triggerState.name());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resumeJob(String jobClassName, String jobGroupName) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jobClassName,jobGroupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reschedulejob(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            log.error("更新定时任务失败："+e);
            throw new Exception("更新定时任务失败");
        }
    }

    @Override
    public void deletejob(String jobClassName, String jobGroupClassName) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName,jobGroupClassName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName,jobGroupClassName));
            scheduler.deleteJob(JobKey.jobKey(jobClassName,jobGroupClassName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> queryjob(Integer pageNum, Integer pageSize) {

        PageInfo<JobAndTrigger> jobAndTriggerPageInfo=jobAndTriggerServiceervice.getJobAndTriggerDetails(pageNum,pageSize);
        HashMap<String, Object> map = new HashMap<>();
        map.put("JobAndTrigger",jobAndTriggerPageInfo);
        map.put("number",jobAndTriggerPageInfo.getTotal());
        return map;
    }

    @Override
    public Map<String, Object> getState(String jobClassName, String jobGroupName) {
        Map<String,Object> map=new HashMap<>();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
        Trigger.TriggerState triggerState = null;
        try {
            triggerState = scheduler.getTriggerState(triggerKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        log.info("当前定时器状态："+triggerState.name());
        map.put("state",triggerState.name());
        return map;
    }

    public static IBaseJob getClasszz(String jobclassName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> classzz=Class.forName(jobclassName);
        return (IBaseJob)classzz.getDeclaredConstructor().newInstance();
    }
}
