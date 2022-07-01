package com.example.springbatch.config.batch;

import com.example.springbatch.job.QuartzJob;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BeanUtil {
    private final ApplicationContext applicationContext;

    public Object getBean(String name){
        return applicationContext.getBean(name);
    }

}
