package com.cj.StaService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cj.StaService.client.UcenterClient;
import com.cj.StaService.entity.StatisticsDaily;
import com.cj.StaService.mapper.StatisticsDailyMapper;
import com.cj.StaService.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author cj
 * @since 2023-02-25
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    UcenterClient ucenterClient;
    @Override
    public void createStatisticsByDay(String day) {
        QueryWrapper<StatisticsDaily> wrapper=new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);
        Integer Count=(Integer) ucenterClient.countRegister(day).getData().get("count");
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO
        StatisticsDaily daily=new StatisticsDaily();
        daily.setRegisterNum(Count);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);
        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getChartData(String begin, String end, String type) {
        QueryWrapper<StatisticsDaily> wrapper=new QueryWrapper<>();
        wrapper.select("date_calculated",type);
        wrapper.between("date_calculated",begin,end);
        List<StatisticsDaily> dailies=baseMapper.selectList(wrapper);

        Map<String,Object> map=new HashMap<>();
        List<String> dateList=new ArrayList<>();
        List<Integer> dataList=new ArrayList<>();
        map.put("dateList",dateList);
        map.put("dataList",dataList);
        for (StatisticsDaily daily : dailies) {
            dateList.add(daily.getDateCalculated());
            switch (type){
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        return map;
    }
}
