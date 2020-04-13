package com.example.demo.controller;

import com.example.demo.entity.JobAndTrigger;
import com.example.demo.quartz.IBaseJob;
import com.example.demo.service.IJobAndTriggerService;
import com.example.demo.service.IQuartzJobService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program:
 * @description:
 * @author: halfhoney2018@gmail.com
 * @create: 2020/4/13 11:21
 */
@Slf4j
@RestController
@RequestMapping(value = "/job")
public class JobController {
    @Autowired
    private IQuartzJobService  iQuartzJobService;

    /**
     * 添加任务
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @throws Exception
     */
    @PostMapping(value = "/addjob")
    public void addJob(@RequestParam(value = "jobClassName")String jobClassName,
                       @RequestParam(value = "jobGroupName")String jobGroupName,
                       @RequestParam(value = "cronExpression")String cronExpression) throws Exception {
        iQuartzJobService.addJob(jobClassName,jobGroupName,cronExpression);
    }

    /**
     * 中断操作
     * @param jobClassName
     * @param jobGroupName
     * @throws SchedulerException
     */
    @PostMapping(value="/pausejob")
    public void pauseJob(@RequestParam(value = "jobClassName") String jobClassName,@RequestParam(value = "jobGroupName")String jobGroupName) throws SchedulerException {
        iQuartzJobService.pausejob(jobClassName,jobGroupName);
    }

    /**
     *恢复操作
     * @param jobClassName
     * @param jobGroupName
     * @throws SchedulerException
     */
    @PostMapping(value="/resumejob")
    public void  jobPause(@RequestParam(value = "jobClassName")String jobClassName,
                          @RequestParam(value = "jobGroupName")String jobGroupName) throws SchedulerException {
        iQuartzJobService.resumeJob(jobClassName,jobGroupName);
    }

    /**
     *定时器修改
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @throws Exception
     */
    @PostMapping(value="/reschedulejob")
    public void rescheduleJob(@RequestParam(value = "jobClassName")String jobClassName,
                              @RequestParam(value = "jobGroupName")String jobGroupName,
                              @RequestParam(value = "cronExpression")String cronExpression) throws Exception {
        iQuartzJobService.reschedulejob(jobClassName,jobGroupName,cronExpression);
    }

    /**
     * 删除定时任务
     * @param jobClassName
     * @param jobGroupClassName
     */
    @PostMapping(value = "deletejob")
    public void deletejob(@RequestParam(value = "jobClassName")String jobClassName,@RequestParam(value = "jobGroupName")String jobGroupClassName){
        iQuartzJobService.deletejob(jobClassName,jobGroupClassName);
    }

    @GetMapping(value = "/queryjob")
    public Map<String,Object> queryJob(@RequestParam(value = "pageNum")Integer pageNum,
                                       @RequestParam(value="pageSize")Integer pageSize){
        return iQuartzJobService.queryjob(pageNum,pageSize);
    }






    /**
     *
     * @param jobclassName
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */

    public static IBaseJob getClass(String jobclassName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> classzz=Class.forName(jobclassName);
        return (IBaseJob)classzz.getDeclaredConstructor().newInstance();
    }
}
