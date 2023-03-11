package com.cj.serviceedu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cj.commonutils.R;
import com.cj.serviceedu.entity.EduCourse;
import com.cj.serviceedu.entity.EduTeacher;
import com.cj.serviceedu.service.EduCourseService;
import com.cj.serviceedu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/admin/EduService/indexFront")
public class IndexFront {
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;
    @GetMapping("index")
    public R index(){
        QueryWrapper<EduCourse> wrapper=new QueryWrapper<>();
        wrapper.orderByAsc("id");
        wrapper.last("limit 8");
        List<EduCourse> eduCourses = eduCourseService.list(wrapper);

        QueryWrapper<EduTeacher> wrapper1=new QueryWrapper<>();
        wrapper.orderByAsc("id");
        wrapper.last("limit 8");
        List<EduTeacher> eduTeachers = eduTeacherService.list(wrapper1);
        return R.ok().data("eduList",eduCourses).data("teacherList",eduTeachers);
    }
}
