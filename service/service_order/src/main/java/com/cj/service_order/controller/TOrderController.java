package com.cj.service_order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cj.commonutils.R;
import com.cj.commonutils.util.JwtUtils;
import com.cj.service_order.entity.TOrder;
import com.cj.service_order.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author cj
 * @since 2023-02-22
 */
@RestController
@RequestMapping("/service_order/t-order")
public class TOrderController {
    @Autowired
    private TOrderService tOrderService;

    @PostMapping("add/{CourseId}")
    public R add(@PathVariable String CourseId, HttpServletRequest request){
            String orderId=tOrderService.saveOrder(CourseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId",orderId);
    }

    @GetMapping("getOrder/{orderId}")
    public R getOrder(@PathVariable String orderId){
        QueryWrapper<TOrder> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("order_no",orderId);
        TOrder tOrder=tOrderService.getOne(queryWrapper);
        return R.ok().data("item",tOrder);
    }
    @GetMapping("isBuyCourse/{CourseId}/{userId}")
    public R isBuyCourse(@PathVariable String CourseId,@PathVariable String userId){
        QueryWrapper<TOrder> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id", CourseId).eq("status", 1).eq("member_id", userId);
        Integer count=tOrderService.count(wrapper);
        if(count>0)
            return  R.ok().data("isBuy",true);
        else
            return  R.ok().data("isBuy",false);
    }
}

