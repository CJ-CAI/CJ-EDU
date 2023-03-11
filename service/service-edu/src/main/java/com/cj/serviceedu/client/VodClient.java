package com.cj.serviceedu.client;

import com.cj.commonutils.R;
import com.cj.serviceedu.client.impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
public interface VodClient {
    @DeleteMapping(value = "/admin/EduVod/video/delete/{id}")
    public R removeVideo(@PathVariable String id);
    @DeleteMapping(value = "/admin/EduVod/video/deleteBatch/")
    public R removeVideoList(@RequestParam List<String> videoIdList);
}
