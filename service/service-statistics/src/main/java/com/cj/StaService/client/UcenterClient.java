package com.cj.StaService.client;

import com.cj.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@ComponentScan
@FeignClient("service-ucenter")
public interface UcenterClient {
    @GetMapping("/U_center/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
