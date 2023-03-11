package com.cj.serviceedu.controller;


import com.cj.commonutils.R;
import com.cj.serviceedu.entity.EduChapter;
import com.cj.serviceedu.entity.vo.ChapterVo;
import com.cj.serviceedu.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/admin/EduService/edu-chapter")
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;
    @GetMapping("/getList/{courseId}")
    public R nestedListByCourseId(@PathVariable String courseId){
        List<ChapterVo> chapterVoList=eduChapterService.nestedList(courseId);
        return R.ok().data("items",chapterVoList).code(20000);
    }
    @DeleteMapping("/delete/{chapterID}")
    public R delete(@PathVariable String chapterID){
        System.out.println("delete——chapter");
        eduChapterService.deleteChapter(chapterID);
        return R.ok().message("删除章节成功");
    }
    @PostMapping("/update/")
    public R update(@RequestBody EduChapter eduChapter){
        Boolean flag=eduChapterService.updateById(eduChapter);
        return flag?R.ok().message("修改章节成功"):R.error().message("修改章节失败");
    }
    @PostMapping("/add/")
    public R add(@RequestBody EduChapter eduChapter){
        Boolean flag=eduChapterService.save(eduChapter);
        return flag?R.ok().message("添加章节成功"):R.error().message("添加章节失败");
    }

}

