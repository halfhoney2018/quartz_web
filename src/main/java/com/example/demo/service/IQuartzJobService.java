package com.example.demo.service;

import com.example.demo.quartz.IBaseJob;
import org.quartz.SchedulerException;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @program:
 * @description: 定时器的修改、暂停、删除、添加、查询
 * @author: halfhoney2018@gmail.com
 * @create: 2020/4/13 13:51
 */
public interface IQuartzJobService {
    /**
     * 定时器的添加
     * @param jobClassName 定时器名称
     * @param jobGroupName 定时器分组名称
     * @param cronExpression 定时器表达式
     * @return  返回map数据
     * msg:
     *
     * @throws SchedulerException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Map<String,Object> addJob(String jobClassName,String jobGroupName,String cronExpression) throws SchedulerException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    /**
     * 定时器中断操作
     * @param jobClassName
     * @param jobGroupName
     */
    public void pausejob(String jobClassName,String jobGroupName);

    /**
     * 定时器恢复操作
     * @param jobClassName
     * @param jobGroupName
     */
    public void resumeJob(String jobClassName,String jobGroupName);

    /**
     * 定时器修改操作
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     */
    public void reschedulejob(String jobClassName,String jobGroupName,String cronExpression) throws Exception;

    /**
     * 定时器的删除操作
     * @param jobClassName
     * @param jobGroupName
     */
    public void deletejob(String jobClassName,String jobGroupName);

    /**
     * 查询相关定时器信息
     * @param pageNum  当前页面
     * @param pageSize 每行显示数量
     * @return map
     *JobAndTrigger  定时器查询内容
     *number  总数
     */
    public Map<String,Object> queryjob(Integer pageNum,Integer pageSize);

    /**
     * 查询相关定时器状态
     * @param jobClassName
     * @param jobGroupName
     * @return state：
     *PAUSED  暂停
     * NORMAL 正常
     *
     */
    public Map<String,Object> getState(String jobClassName,String jobGroupName);
}
