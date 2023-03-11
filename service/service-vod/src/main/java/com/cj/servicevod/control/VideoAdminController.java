package com.cj.servicevod.control;

import com.aliyuncs.exceptions.ClientException;
import com.cj.commonutils.R;
import com.cj.servicevod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Api(description = "阿里云视频点播微服务")
@RequestMapping("/admin/EduVod/video")
public class VideoAdminController {
    @Autowired
    private VideoService videoService;

    @PostMapping("upload")
    public R uploadVideo(@ApiParam(name = "file",value = "文件",required = true)@RequestParam("file")MultipartFile file){
        String VideoId=videoService.uploadVideo(file);
        return R.ok().message("视频上传成功").data("videoId",VideoId);
    }
    @DeleteMapping("delete/{id}")
    public R removeVideo(@ApiParam(name = "id",value = "id",required = true)@PathVariable String id){
        videoService.removeVideo(id);
        return R.ok();
    }
    @DeleteMapping("deleteBatch/")
    public R removeVideoList(@RequestParam List<String> videoIdList){
//        System.out.println("deleteBatch:"+videoIdList);
        videoService.removeVideoList(videoIdList);
        return R.ok();
    }
    @GetMapping("getAuth/{id}")
    public R getAuth(@PathVariable String id) throws ClientException {
        return R.ok().data("playAuth", videoService.getAuth(id));
    }
}
