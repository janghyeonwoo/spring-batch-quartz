package com.example.springbatch.job;

import com.example.springbatch.entity.Market;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Entity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration // Spring batch의 모든 Job은 Configuration으로 등록해야한다.
public class BatchJob1 {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;



    @Bean(name="create_job1")
    Job create_Job1(){
        return jobBuilderFactory.get("create_job1")
                .start(create_Job1_Step1()).build();
    }

    @Bean
    @JobScope
    Step create_Job1_Step1(){
        return stepBuilderFactory.get("create_Job1_Step1")
                .<Market,Market>chunk(10)
                .reader(reader(null))
                .processor(processor(null))
                .writer(writer(null))
                .build();

    }


    @Bean
    @StepScope
    public JpaPagingItemReader<Market> reader(@Value("#{jobParameters[requestDate]}") String requestDate){
      log.info("==========> reader Value : {}" , requestDate );

        Map<String,Object> parameterValues = new HashMap<>();
        parameterValues.put("price", 1000);

        return new JpaPagingItemReaderBuilder<Market>()
                .pageSize(10)
                .parameterValues(parameterValues)
                .queryString("SELECT m FROM Market m WHERE m.price >= : price")
                .entityManagerFactory(entityManagerFactory)
                .name("JpaPagingItemReader")
                .build();
    }

    @Bean
    @JobScope
    public ItemProcessor <Market, Market> processor(@Value("#{jobParameters[requestDate]}") String requestDate){
        return new ItemProcessor<Market, Market>() {
            @Override
            public Market process(Market item) throws Exception {
                log.info("==> processor Market : {} ", item);
                item.setName("new_" + item.getName());
                return item;
            }
        };
    }

    @Bean
    @JobScope
    public JpaItemWriter <Market> writer(@Value("#{jobParameters[requestDate]}") String requestDate){
        log.info("==> writer value : {}", requestDate);
        return new JpaItemWriterBuilder<Market>().entityManagerFactory(entityManagerFactory).build();
    }
}
