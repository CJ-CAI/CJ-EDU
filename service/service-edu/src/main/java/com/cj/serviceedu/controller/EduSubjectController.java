package com.cj.serviceedu.controller;


import com.cj.commonutils.R;
import com.cj.serviceedu.entity.subject.EduSubject_one;
import com.cj.serviceedu.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author cj
 * @since 2023-01-17
 */
@RestController
@RequestMapping("/admin/EduService/subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        eduSubjectService.importSubjectData(file,eduSubjectService);
        return R.ok().message("上传课程成功").code(20000);
    }
    @GetMapping("getTree")
    public R getTree(){
        List<EduSubject_one> lists=eduSubjectService.getTree();
        return R.ok().data("items",lists).code(20000);
    }
}

