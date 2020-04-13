package com.example.demo.mapper;

import com.example.demo.service.IQuartzJobService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @program:
 * @description:
 * @author: halfhoney2018@gmail.com
 * @create: 2020/4/13 13:01
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class JobAndTriggerMapperTest {
    @Autowired
    JobAndTriggerMapper jobAndTriggerMapper;
    @Autowired
    IQuartzJobService iQuartzJobService;
    @Test
    public void test(){
//        System.out.println(jobAndTriggerMapper.getJobAndTriggerDetails());
        System.out.println(iQuartzJobService.getState("com.example.demo.quartz.JobDemo","12"));
    }
}