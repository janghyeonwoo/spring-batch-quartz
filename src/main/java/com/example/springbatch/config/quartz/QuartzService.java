package com.example.springbatch.config.quartz;

import com.sun.tools.javac.jvm.ByteCodes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuartzService {
    private static final String JOB_NM = "jobNm";

    private final SchedulerFactoryBean schedulerFactoryBean;
    private Scheduler scheduler;


    @PostConstruct
    public void init() {
        scheduler = schedulerFactoryBean.getScheduler();
        //앱이 실행된 서버(스케쥴러) 내의 STARTED 상태 JOB들을 제거
//      quartzDAO.removeStartedJobs();
    }


    // 스케줄러에 스케줄 등록
    public void register() throws Exception {
        String jobName = "myJobOne_Job1";
        String cron = "0/5 * * * * ?";
        scheduler.scheduleJob(this.createJobDeatail(jobName), this.createCronTrigger(jobName, cron));


//        jobName = "create_job1";
//        cron = "0/15 * * * * ?";
//        scheduler.scheduleJob(this.createJobDeatail(jobName), this.createCronTrigger(jobName, cron));

    }

    // JobDetail 생성
    private JobDetail createJobDeatail(String jobNm) {
        JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class)
                .withIdentity(jobNm)
                .build();
        jobDetail.getJobDataMap().put(JOB_NM, jobNm);
        return jobDetail;
    }


    //cronTrigger 생성
    private CronTrigger createCronTrigger(String jobNm, String cron) {
        return TriggerBuilder.newTrigger()
                .withIdentity(new JobKey(jobNm).getName())
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
    }

    // 스케줄러 시작
    public void start() throws SchedulerException {
        if (scheduler != null && !scheduler.isStarted()) {
            scheduler.start();
        }
    }

    /**
     * 스케쥴러 종료
     */
    public void shutdown() throws SchedulerException, InterruptedException {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

    /**
     * 스케쥴러 클리어
     */
    public void clear() throws SchedulerException {
        scheduler.clear();
    }

    public void addListener(JobListener jobListener) throws SchedulerException {
        scheduler.getListenerManager().addJobListener(jobListener);
    }


}
