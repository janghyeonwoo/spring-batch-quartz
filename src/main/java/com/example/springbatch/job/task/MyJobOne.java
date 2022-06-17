package com.example.springbatch.job.task;

import lombok.RequiredArgsConstructor;
import org.quartz.JobBuilder;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@RequiredArgsConstructor
public class MyJobOne {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean(name = "launcherTestJob")
    public Job launcherTestJob() {
        return this.jobBuilderFactory.get("launcherTestJob")
                /* step start */
                .start(launcherTestStep1())
                .next(launcherTestStep2())
                .next(launcherTestStep3())
                .build();
    }

    @Bean
    public Step launcherTestStep1() {
        return stepBuilderFactory.get("launcherTestStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("launcherTestStep1");

                        // 동기 : 잡이 모두 끝난 후 결과 리턴
                        // 비동기 : 일단 결과를 내려주고, 내부적으로 배치 실행
                        Thread.sleep(3000);
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step launcherTestStep2() {
        return stepBuilderFactory.get("launcherTestStep2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("launcherTestStep2");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }


    @Bean
    public Step launcherTestStep3(){
        return stepBuilderFactory.get("launcherTestStep3")
                .tasklet(new MyTaskTwo())
                .build();
    }


}
