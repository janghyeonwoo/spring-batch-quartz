package com.example.springbatch.config.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;


@Slf4j
public class QuartzJobListner implements JobListener {
    @Override
    public String getName() {
        return this.getClass().getName();
    }


    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        log.info("Job 수행 되기 전");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        log.info("Job 중단");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        log.info("Job 수행 완료 후");
    }
}
