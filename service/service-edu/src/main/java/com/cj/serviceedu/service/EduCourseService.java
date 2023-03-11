package com.cj.serviceedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cj.serviceedu.entity.front_vo.CourseQueryVo;
import com.cj.serviceedu.entity.front_vo.CourseWebVo;
import com.cj.serviceedu.entity.query.CourseQuery;
import com.cj.serviceedu.entity.vo.CourseInfoForm;
import com.cj.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cj.serviceedu.entity.vo.CoursePublishVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author cj
 * @since 2023-01-18
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoForm courseInfoForm);

    Boolean updateCourseInfo(CourseInfoForm courseInfoForm);

    CoursePublishVo getCoursePublishVoById(String id);

    Boolean setPublishCourse(String id);

    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    boolean delete_courseById(String id);
//    =======================================front=========================================

    List<EduCourse> selectByTeacherId(String teacherId);

    Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseQueryVo courseQueryVo);
    void updatePageViewCount(String id);
    CourseWebVo selectInfoWebById(String id);
}
