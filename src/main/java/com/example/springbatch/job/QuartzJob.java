package com.example.springbatch.job;


import com.example.springbatch.entity.Market;
import com.example.springbatch.repository.MarketRepository;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Slf4j
@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
// @RequiredArgsConstructor 사용 못함
public class QuartzJob implements Job {
//    @Autowired
//    private MarketRepos marketRepo;

    @Autowired
    private MarketRepository marketRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Quartz Job Executed");

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        log.info("dataMap date : {}", dataMap.get("date"));
        log.info("dataMap executeCount  : {}", dataMap.get("executeCount"));

        int cnt = (int) dataMap.get("executeCount");
        dataMap.put("executeCount", ++cnt);

        Market market = new Market();
        market.setName(String.format("pooney_%s", dataMap.get("date")));
        market.setPrice(3000);
        marketRepository.save(market);

    }


}
