package com.example.springbatch.config.quartz;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Slf4j
public class QuartzStarter {

    @Autowired
    private QuartzService quartzService;

    public void init() throws Exception {
        quartzService.clear();
        quartzService.addListener(new QuartzListener());
        quartzService.register();
        quartzService.start();
    }

}
