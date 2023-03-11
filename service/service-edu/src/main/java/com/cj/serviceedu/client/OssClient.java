package com.cj.serviceedu.client;

import com.cj.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-oss")
@Component
public interface OssClient {
    @PostMapping(value ="/admin/EduOss/file/delete_file/")
    public R delete_file(@RequestBody String objectName);
}
