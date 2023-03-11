package com.cj.service_order.client;

import com.cj.commonutils.entity.User_vo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    @PostMapping(value = "/U_center/getOrder_user/{id}")
    public User_vo getOrder_user(@PathVariable("id") String id);
}
