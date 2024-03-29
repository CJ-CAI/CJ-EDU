package com.cj.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cj.serviceedu.entity.EduTeacher;
import com.cj.serviceedu.mapper.EduTeacherMapper;
import com.cj.serviceedu.entity.query.TeacherQuery;
import com.cj.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author cj
 * @since 2023-01-08
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery) {
        QueryWrapper<EduTeacher> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        queryWrapper.orderByDesc("sort");
        if(teacherQuery==null)
        {
            baseMapper.selectPage(pageParam,queryWrapper);
        }
        String name=teacherQuery.getName();
        Integer level=teacherQuery.getLevel();
        String begin=teacherQuery.getBegin();
        String end=teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name))
            queryWrapper.like("name",name);
        if(!StringUtils.isEmpty(level))
            queryWrapper.eq("level",level);
        if (!StringUtils.isEmpty(begin)&&!StringUtils.isEmpty(end))
            queryWrapper.between("gmt_create",begin,end);
        baseMapper.selectPage(pageParam,queryWrapper);
    }

    @Override
    public Map<String, Object> pageListWeb(Page<EduTeacher> pageParam) {
        QueryWrapper<EduTeacher> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        baseMapper.selectPage(pageParam,queryWrapper);
        List<EduTeacher> records=pageParam.getRecords();
        long current=pageParam.getCurrent();
        long pages=pageParam.getPages();
        long size=pageParam.getSize();
        long total=pageParam.getTotal();
        boolean hasNext=pageParam.hasNext();
        boolean hasPrevious=pageParam.hasPrevious();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("items",records);
        map.put("current",current);
        map.put("pages",pages);
        map.put("size",size);
        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        return map;
    }


}
