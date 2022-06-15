package com.example.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BatchJob2 {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;



    @Bean
    public Job batchJob2_job1(){
        return jobBuilderFactory.get("batchJob2_job1")
                .start(batchJob2_job1_step1(null))
                .build();
    }

    @Bean
    @JobScope
    public Step batchJob2_job1_step1(@Value("#{jobParameters[requestDate]}") String requestDate){
        return stepBuilderFactory.get("batchJob2_job1_step1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is batchJob2_job1_step1");
                    log.info(">>>>> requestDate = {}", requestDate);
                    return RepeatStatus.FINISHED;
                }).build();
    }

}
