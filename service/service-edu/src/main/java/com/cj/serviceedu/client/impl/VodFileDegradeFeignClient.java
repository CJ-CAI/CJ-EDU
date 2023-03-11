package com.cj.serviceedu.client.impl;

import com.cj.commonutils.R;
import com.cj.serviceedu.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeVideo(String id) {
        System.out.println("removeVideo——timeout");
        return R.error().message("time out");
    }

    @Override
    public R removeVideoList(List<String> videoIdList) {
        System.out.println("removeVideoList——timeout");
        return R.error().message("time out");
    }
}
