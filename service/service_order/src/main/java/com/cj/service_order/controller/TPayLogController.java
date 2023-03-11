package com.cj.service_order.controller;


import com.cj.commonutils.R;
import com.cj.service_order.service.TPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author cj
 * @since 2023-02-22
 */
@RestController
@RequestMapping("/service_order/t-pay-log")
public class TPayLogController {
    @Autowired
    private TPayLogService tPayLogService;
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        Map map=tPayLogService.createNative(orderNo);
        System.out.println("map: "+map);
        return R.ok().data(map);
    }
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map=tPayLogService.queryPayStatus(orderNo);
        if(map==null)
            return R.error().message("支付出错");
        if(map.get("trade_state").equals("SUCCESS")){
            tPayLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }
}

