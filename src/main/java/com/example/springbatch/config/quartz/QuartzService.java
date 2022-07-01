package com.example.springbatch.config.quartz;

import com.example.springbatch.job.QuartzBatchJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class QuartzService {
    private final Scheduler scheduler;
    public static final String JOB_ANME = "JOB_NAME";

    @PostConstruct
    public void init() {
        try {
            scheduler.clear();
//            scheduler.getListenerManager().addJobListener(new QuartzJobListner());
//            scheduler.getListenerManager().addTriggerListener(new QuartzTriggerListener());


//            addJob(QuartzJob.class, "QuartzJob", "Quartz Job 입니다", paramsMap, "0/1 * * * * ?");
            addJob(QuartzBatchJob.class, "createJob1", "createJob1 입니다", null , "0/1 * * * * ?");
            addJob(QuartzBatchJob.class, "createJob2", "createJob2 입니다", null , "0/1 * * * * ?");


        } catch (Exception e){
            log.error("addJob error  : {}", e);

        }
    }

    //Job 추가
    public <T extends Job> void addJob(Class<? extends Job> job ,String name, String  dsec, Map paramsMap, String cron) throws SchedulerException {
        JobDetail jobDetail = buildJobDetail(job,name,dsec,paramsMap);
        Trigger trigger = buildCronTrigger(cron);
        if(scheduler.checkExists(jobDetail.getKey())) scheduler.deleteJob(jobDetail.getKey());
        scheduler.scheduleJob(jobDetail,trigger);
    }


    //JobDetail 생성
    public <T extends Job> JobDetail buildJobDetail(Class<? extends Job> job, String name, String desc, Map paramsMap) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(JOB_ANME, name);
        jobDataMap.put("executeCount", 1);


        return JobBuilder
                .newJob(job)
                .withIdentity(name)
                .withDescription(desc)
                .usingJobData(jobDataMap)
                .build();
    }

    //Trigger 생성
    private Trigger buildCronTrigger(String cronExp) {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExp))
                .build();
    }



//
//    public void addSimpleJob(Class job, String name, String desc, Map params, Integer seconds) throws SchedulerException {
//        JobDetail jobDetail = buildJobDetail(job, name, desc, params);
////
////        if (scheduler.checkExists(jobDetail.getKey())) {
////            scheduler.deleteJob(jobDetail.getKey());
////        }
//
//        scheduler.scheduleJob(
//                jobDetail,
//                buildSimpleJobTrigger(seconds)
//        );
//    }
//
//    public void addCronJob(Class job, String name, String desc, Map params, String expression) throws SchedulerException {
//        JobDetail jobDetail = buildJobDetail(job, name, desc, params);
//
//        if (scheduler.checkExists(jobDetail.getKey())) {
//            scheduler.deleteJob(jobDetail.getKey());
//        }
//
//        scheduler.scheduleJob(
//                jobDetail,
//                buildCronJobTrigger(expression)
//        );
//    }
//
////    private JobDetail buildJobDetail(Class job, String name, String desc, Map params) {
////        JobDataMap jobDataMap = new JobDataMap();
////        if(params != null) jobDataMap.putAll(params);
////        return JobBuilder
////                .newJob(job)
////                .withIdentity(name)
////                .withDescription(desc)
////                .usingJobData(jobDataMap)
////                .build();
////    }
//
//    /**
//     * Cron Job Trigger
//     */
//    // *  *   *   *   *   *     *
//    // 초  분  시   일   월  요일  년도(생략가능)
//    private Trigger buildCronJobTrigger(String scheduleExp) {
//        return TriggerBuilder.newTrigger()
//                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp))
//                .build();
//    }
//
//    /**
//     * Simple Job Trigger
//     */
//    private Trigger buildSimpleJobTrigger(Integer seconds) {
//        return TriggerBuilder.newTrigger()
//                .withSchedule(SimpleScheduleBuilder
//                        .simpleSchedule()
//                        .repeatForever()
//                        .withIntervalInSeconds(seconds))
//                .build();
//    }
//
//    public static String buildCronExpression(LocalDateTime time) {
//        LocalDateTime fireTime = time.plusSeconds(10);
//        // 0 0 0 15 FEB ? 2021
//        return String.format("%d %d %d %d %s ? %d", fireTime.getSecond(), fireTime.getMinute(), fireTime.getHour(), fireTime.getDayOfMonth(), fireTime.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase(), fireTime.getYear());
//    }

}
