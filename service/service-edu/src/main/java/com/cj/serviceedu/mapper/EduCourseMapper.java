package com.cj.serviceedu.mapper;

import com.cj.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cj.serviceedu.entity.front_vo.CourseWebVo;
import com.cj.serviceedu.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author cj
 * @since 2023-01-18
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublishVo getPublishCourseInfo(String id);
//============================front==================================================
    CourseWebVo selectInfoWebById(String courseId);

}
