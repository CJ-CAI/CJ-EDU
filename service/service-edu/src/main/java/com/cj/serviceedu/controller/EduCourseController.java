package com.cj.serviceedu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cj.commonutils.R;
import com.cj.serviceedu.entity.EduCourse;
import com.cj.serviceedu.entity.EduCourseDescription;
import com.cj.serviceedu.entity.query.CourseQuery;
import com.cj.serviceedu.entity.vo.CourseInfoForm;
import com.cj.serviceedu.entity.vo.CoursePublishVo;
import com.cj.serviceedu.service.EduCourseDescriptionService;
import com.cj.serviceedu.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author cj
 * @since 2023-01-18
 */
@RestController
@RequestMapping("/admin/EduService/edu-course")
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @PostMapping("save")
    public R saveCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        String courseID=eduCourseService.saveCourseInfo(courseInfoForm);
        if(!StringUtils.isEmpty(courseID)){
            return R.ok().data("courseId",courseID).code(20000);
        }
        else {
            return R.error().code(20001).message("保存失败");
        }
    }
    @GetMapping("getCourseById/{id}")
    public R getCouseById(@PathVariable String id){
        CourseInfoForm courseInfoForm=new CourseInfoForm();
        EduCourse eduCourse=eduCourseService.getById(id);
        if(eduCourse==null)
            return R.error().message("没有找到课程id");
        BeanUtils.copyProperties(eduCourse,courseInfoForm);
        EduCourseDescription eduCourseDescription=eduCourseDescriptionService.getById(id);
        BeanUtils.copyProperties(eduCourseDescription,courseInfoForm);
        return R.ok().data("items",courseInfoForm);
    }

    @PostMapping("update")
    public R updateCourse(@RequestBody CourseInfoForm courseInfoForm){
        Boolean flag=eduCourseService.updateCourseInfo(courseInfoForm);
        if(flag){
            return R.ok().code(20000).message("修改成功");
        }
        else {
            return R.error().code(20001).message("修改失败");
        }
    }
//    ==========================================course-publish===========================================
    @GetMapping("/publish_get/{id}")
    public R getCoursePublishVoById(@PathVariable String id){
        CoursePublishVo coursePublishVo=eduCourseService.getCoursePublishVoById(id);
        return R.ok().data("item",coursePublishVo);
    }
    @PutMapping("/publish_p/{id}")
    public R publishCourseById(@PathVariable String id){
        System.out.println(id);
        return eduCourseService.setPublishCourse(id)?R.ok().message("成功发布课程"):R.error().message("发布课程失败");
    }
//    ==========================================course-list===========================================
    @PostMapping("/getList/{page}/{limit}")
    public R pageQuery(@PathVariable Long page, @PathVariable Long limit, @RequestBody CourseQuery courseQuery){
        Page<EduCourse> pageParam = new Page<>(page, limit);
        eduCourseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }
    @DeleteMapping("/delete_Course/{id}")
    public R delete_courseID(@PathVariable String id){
        boolean result=eduCourseService.delete_courseById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }
}

