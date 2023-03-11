package com.cj.serviceedu.controller;


import com.cj.commonutils.R;
import com.cj.serviceedu.entity.EduVideo;
import com.cj.serviceedu.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author cj
 * @since 2023-01-18
 */
@RestController
@RequestMapping("/admin/EduService/edu-video")
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    @PostMapping("/add/")
    public R add(@RequestBody EduVideo eduVideo){
//        System.out.println("eduVideoService_add"+eduVideo);
        return  eduVideoService.save(eduVideo)?R.ok().message("添加小节成功"):R.error().message("添加小节失败");
    }
    @PostMapping("/update/")
    public R update(@RequestBody EduVideo eduVideo){
//        System.out.println("eduVideoService_update"+eduVideo);
        return  eduVideoService.updateById(eduVideo)?R.ok().message("修改小节成功"):R.error().message("修改小节失败");
    }
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable String id){

        if(eduVideoService.getById(id)==null)
            return R.error().message("删除小节失败");
        return eduVideoService.removeVideoById(id)?R.ok().message("删除小节成功"):R.error().message("删除小节失败");
    }
}

