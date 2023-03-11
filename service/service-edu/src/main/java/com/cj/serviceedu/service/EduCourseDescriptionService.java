package com.cj.serviceedu.service;

import com.cj.serviceedu.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author cj
 * @since 2023-01-18
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {
    boolean deleteByCourseId(String CourseId);
}
