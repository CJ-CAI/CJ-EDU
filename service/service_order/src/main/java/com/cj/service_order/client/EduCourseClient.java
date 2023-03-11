package com.cj.service_order.client;

import com.cj.commonutils.entity.Order_Course_vo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient("service-edu")
@Component
public interface EduCourseClient {
    @GetMapping(value = "front/EduService/course/getOrder_Course/{CourseId}")
    public Order_Course_vo getOrder_Course(@PathVariable("CourseId") String CourseId);
}
