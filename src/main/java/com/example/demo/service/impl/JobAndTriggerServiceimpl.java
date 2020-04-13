package com.example.demo.service.impl;

import com.example.demo.entity.JobAndTrigger;
import com.example.demo.mapper.JobAndTriggerMapper;
import com.example.demo.service.IJobAndTriggerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program:
 * @description: 定时任务 从数据库里面进行获取
 * @author: halfhoney2018@gmail.com
 * @create: 2020/4/13 12:03
 */
@Service
public class JobAndTriggerServiceimpl implements IJobAndTriggerService {
    @Autowired
    private JobAndTriggerMapper jobAndTriggerMapper;
    @Override
    public PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize) {
        List<JobAndTrigger> all=this.jobAndTriggerMapper.getJobAndTriggerDetails();
        return new PageInfo<JobAndTrigger>(all);
    }
}
