package com.example.demo.mapper;

import com.example.demo.entity.JobAndTrigger;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program:
 * @description:
 * @author: halfhoney2018@gmail.com
 * @create: 2020/4/13 12:16
 */
@Mapper
public interface JobAndTriggerMapper {
    /**
     * 获取定时任务信息
     * @return
     */
    public List<JobAndTrigger> getJobAndTriggerDetails();
}
