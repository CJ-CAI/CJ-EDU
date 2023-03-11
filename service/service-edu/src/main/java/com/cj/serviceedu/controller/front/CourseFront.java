package com.cj.serviceedu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cj.commonutils.R;
import com.cj.commonutils.entity.Order_Course_vo;
import com.cj.serviceedu.entity.EduCourse;
import com.cj.serviceedu.entity.front_vo.CourseQueryVo;
import com.cj.serviceedu.entity.front_vo.CourseWebVo;
import com.cj.serviceedu.entity.vo.ChapterVo;
import com.cj.serviceedu.service.EduChapterService;
import com.cj.serviceedu.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("front/EduService/course")
public class CourseFront {
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private EduCourseService eduCourseService;
    @PostMapping(value = "/getList/{page}/{limit}")
    public R getCList(@PathVariable Long page, @PathVariable Long limit, @RequestBody CourseQueryVo courseQueryVo){
        Page<EduCourse> pageParam=new Page<>(page,limit);
        Map<String,Object> map=eduCourseService.pageListWeb(pageParam,courseQueryVo);
        return R.ok().data(map);
    }

    @GetMapping("getByID/{id}")
    public R getByID(@PathVariable String id){
        CourseWebVo courseWebVo=eduCourseService.selectInfoWebById(id);
        List<ChapterVo> chapterVos=eduChapterService.nestedList(id);
        return R.ok().data("course", courseWebVo).data("chapterVoList", chapterVos);
    }

    @GetMapping("getOrder_Course/{CourseId}")
    public Order_Course_vo getOrder_Course(@PathVariable String CourseId){
        EduCourse eduCourse=eduCourseService.getById(CourseId);
        Order_Course_vo order_course_vo=new Order_Course_vo();
        BeanUtils.copyProperties(eduCourse,order_course_vo);
        return order_course_vo;
    }

}
