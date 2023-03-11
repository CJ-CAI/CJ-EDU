package com.cj.serviceedu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cj.commonutils.R;
import com.cj.serviceedu.entity.EduCourse;
import com.cj.serviceedu.entity.EduTeacher;
import com.cj.serviceedu.service.EduCourseService;
import com.cj.serviceedu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("front/EduService/teacher")
public class TeacherFront {
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;

    @GetMapping(value = "getTeachers/{page}/{limit}")
    public R pageList(@PathVariable Long page, @PathVariable Long limit){
        Page<EduTeacher> pageParam = new Page<EduTeacher>(page, limit);
        Map<String, Object> map = eduTeacherService.pageListWeb(pageParam);
        return R.ok().data(map);
    }
    @GetMapping("getTeacherInfo/{id}")
    public R getTeacherInfo(@PathVariable String id){
        List<EduCourse> eduCourses=eduCourseService.selectByTeacherId(id);
        EduTeacher teacher=eduTeacherService.getById(id);
        return R.ok().data("teacher", teacher).data("courseList", eduCourses);
    }
}
