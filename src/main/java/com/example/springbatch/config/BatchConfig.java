package com.example.springbatch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

//  private final JobBuilderFactory jobBuilderFactory;
//
//  private final StepBuilderFactory stepBuilderFactory;
//
//
//  @Bean(name="demoJobOne")
//  public Job demoJobOne(){
//      return jobBuilderFactory.get("demoJobOne")
//              .start(stepOne())
//              .next(stepTwo())
//              .build();
//  }
//
//
//  @Bean(name="demoJobTwo")
//  public Job demoJobTwo(){
//      return jobBuilderFactory.get("demoJobTwo")
//              .flow(stepOne())
//              .build()
//              .build();
//  }
//
//
//  @Bean
//  public Step stepOne(){
//      return stepBuilderFactory.get("stepOne")
//              .tasklet(new MyTaskOne())
//              .build();
//  }
//
//  @Bean
//  public Step stepTwo(){
//      return stepBuilderFactory.get("stepTwo")
//              .tasklet(new MyTaskTwo())
//              .build();
//  }


}
