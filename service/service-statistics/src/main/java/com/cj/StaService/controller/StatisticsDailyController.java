package com.cj.StaService.controller;


import com.cj.StaService.service.StatisticsDailyService;
import com.cj.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author cj
 * @since 2023-02-25
 */
@RestController
@RequestMapping("/admin/StaService/statistics-daily/")
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService dailyService;
    @GetMapping("add/{day}")
    public R createStatisticsByDate(@PathVariable String day){
        dailyService.createStatisticsByDay(day);
        return  R.ok();
    }
    @GetMapping("show-chart/{begin}/{end}/{type}")
    public R showCharts(@PathVariable String begin,@PathVariable String end,@PathVariable String type){
        Map<String,Object> map=dailyService.getChartData(begin,end,type);
        return R.ok().data(map);
    }
}

