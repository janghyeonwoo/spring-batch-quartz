package com.example.springbatch.job.quartz;


import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
public class QuartzJob1 implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap map = context.getJobDetail().getJobDataMap();
        log.info("RequestContractJob execute invoked, job-detail-key:{}, fired-time:{}, num:{}",
            context.getJobDetail().getKey(), context.getFireTime(), map.getInt("num"));
        log.info("RequestContractJob execute complete");
    }
}
