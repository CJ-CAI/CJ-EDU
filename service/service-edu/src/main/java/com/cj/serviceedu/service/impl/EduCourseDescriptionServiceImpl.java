package com.cj.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cj.serviceedu.entity.EduCourseDescription;
import com.cj.serviceedu.mapper.EduCourseDescriptionMapper;
import com.cj.serviceedu.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author cj
 * @since 2023-01-18
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    @Override
    public boolean deleteByCourseId(String CourseId) {
        QueryWrapper<EduCourseDescription> eduCourseDescriptionQueryWrapper=new QueryWrapper<>();
        eduCourseDescriptionQueryWrapper.eq("course_id", CourseId);
        Integer count=baseMapper.delete(eduCourseDescriptionQueryWrapper);
        return null != count && count > 0;
    }
}
