package com.cj.serviceedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cj.serviceedu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cj.serviceedu.entity.query.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author cj
 * @since 2023-01-08
 */
public interface EduTeacherService extends IService<EduTeacher> {
    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);
    public Map<String, Object> pageListWeb(Page<EduTeacher> pageParam);

}
