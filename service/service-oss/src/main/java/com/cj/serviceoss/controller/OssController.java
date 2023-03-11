package com.cj.serviceoss.controller;

import com.cj.commonutils.R;
import com.cj.serviceoss.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/EduOss/file")
public class OssController {
    @Autowired
    private FileService fileService;
    @PostMapping("teacher_avatar")
    public R teacher_avatar(MultipartFile file){
//        System.out.println("上传图片");
        String url=fileService.upload(file,"teacher");
        return  R.ok().message("教师头像上传成功").data("url",url).code(20000);
    }
    @PostMapping("course_cover")
    public R course_cover(MultipartFile file){
//        System.out.println("上传图片");
        String url=fileService.upload(file,"course");
        return  R.ok().message("课程封面上传成功").data("url",url).code(20000);
    }
    @PostMapping("banner")
    public R banner(MultipartFile file){
//        System.out.println("上传图片");
        String url=fileService.upload(file,"banner");
        return  R.ok().message("banner上传成功").data("url",url).code(20000);
    }

    @PostMapping("delete_file")
    public R delete_file(@RequestBody String objectName){
//        System.out.println(objectName);
        fileService.delete(objectName);
        return  R.ok().message("文件删除成功");
    }
}
