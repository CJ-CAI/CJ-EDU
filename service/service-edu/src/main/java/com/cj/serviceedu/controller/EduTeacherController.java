package com.cj.serviceedu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cj.commonutils.R;
import com.cj.serviceedu.entity.EduTeacher;
import com.cj.serviceedu.entity.query.TeacherQuery;
import com.cj.serviceedu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author cj
 * @since 2023-01-08
 */
@RestController
@RequestMapping("/admin/EduService/edu-teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("findAll")
    public R list(){
        List<EduTeacher> teachers=teacherService.list(null);
        return R.ok().data("items",teachers);
    }
    @DeleteMapping("{id}")
    public R removeById(@PathVariable String id){
        EduTeacher teacher=teacherService.getById(id);
        teacherService.removeById(id);
        return teacher!=null?R.ok().message("成功删除数据"):R.error().message("删除数据失败");
    }
    @PostMapping("/TeacherPageCondition/{page}/{limit}")
    public R pageList(@PathVariable long page, @PathVariable Long limit, @RequestBody TeacherQuery teacherQuery){
//        System.out.println(teacherQuery.toString());
        Page<EduTeacher> teachersPage=new Page<>(page,limit);
        teacherService.pageQuery(teachersPage,teacherQuery);
        List<EduTeacher> teachers=teachersPage.getRecords();
        return R.ok().data("rows",teachers).data("total",teachersPage.getTotal());
    }
    @PostMapping
    public R save(@RequestBody EduTeacher teacher){
        return teacherService.save(teacher)?R.ok().message("保存成功"):R.error().message("保存失败");
    }
    @GetMapping("{id}")
    public R getByID(@PathVariable String id){
        return R.ok().data("item",teacherService.getById(id));
    }
    @PutMapping("{id}")
    public R updateByID(@PathVariable String id,@RequestBody EduTeacher teacher){
        teacher.setId(id);
        return teacherService.updateById(teacher)?R.ok().message("修改数据成功"):R.error().message("修改数据失败");
    }
}

