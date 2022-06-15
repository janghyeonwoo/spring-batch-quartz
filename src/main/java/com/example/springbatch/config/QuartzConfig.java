package com.example.springbatch.config;

import com.example.springbatch.job.quartz.QuartzJob1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class QuartzConfig {

    private final SchedulerFactoryBean schedulerFactory;

    @PostConstruct
    public void scheduled() throws SchedulerException {
        JobDataMap map = new JobDataMap(Collections.singletonMap("num","1"));
        JobDataMap map2 = new JobDataMap(Collections.singletonMap("num","2"));
        JobDetail job  = jobDetail("QuartzJob1", "QuartzJob1_Group", map);
        JobDetail job2  = jobDetail("QuartzJob2", "QuartzJob1_Group", map2);
        SimpleTrigger trigger = trigger("QuartzJob1_Trigger", "QuartzJob1_Trigger_Group");
        SimpleTrigger trigger2 = trigger("QuartzJob2_Trigger", "QuartzJob1_Trigger_Group");

        schedulerFactory.getObject().scheduleJob(job,trigger);
        schedulerFactory.getObject().scheduleJob(job2,trigger2);

    }

    public JobDetail jobDetail(String name, String group , JobDataMap dataMap){
        JobDetail job = JobBuilder.newJob(QuartzJob1.class)
                .withIdentity(name,group)
                .withDescription("simple Quartz Job")
                .usingJobData(dataMap)
                .build();
        return job;
    }

    public SimpleTrigger trigger(String name, String group){
        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(name,group)
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(10))
                .withDescription("Hello My Trigger")
                .build();
        return trigger;
    }

}
