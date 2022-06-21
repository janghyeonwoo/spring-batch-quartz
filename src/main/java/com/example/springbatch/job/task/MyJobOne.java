package com.example.springbatch.job.task;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MyJobOne {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job myJobOne_Job1() {
        return this.jobBuilderFactory.get("myJobOne_Job1")
                /* step start */
                .start(myJobOne_Job1_Step1())
                .next(myJobOne_Job1_Step2())
                .build();
    }

    @Bean
    public Step myJobOne_Job1_Step1() {
        return stepBuilderFactory.get("myJobOne_Job1_Step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("myJobOne_Job1_Step1");

                        // 동기 : 잡이 모두 끝난 후 결과 리턴
                        // 비동기 : 일단 결과를 내려주고, 내부적으로 배치 실행
                        Thread.sleep(3000);
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step myJobOne_Job1_Step2() {
        return stepBuilderFactory.get("myJobOne_Job1_Step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("myJobOne_Job1_Step1");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

}
