package com.cj.StaService.Schedule;

import com.cj.StaService.Utils.DateUtil;
import com.cj.StaService.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService dailyService;
    @Scheduled(cron = "0 0 0 1 * ? ")
    public void everydayTask(){
        String day= DateUtil.formatDate(DateUtil.addDays(new Date(),-1));
        dailyService.createStatisticsByDay(day);
    }
}
