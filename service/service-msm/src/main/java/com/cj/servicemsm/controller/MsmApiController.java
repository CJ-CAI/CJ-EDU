package com.cj.servicemsm.controller;

import com.cj.commonutils.R;
import com.cj.commonutils.util.RandomUtil;
import com.cj.servicemsm.service.MsmService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/EduMsm/")
public class MsmApiController {
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping(value = "send/{phone}")
    public R send_code(@PathVariable String phone) throws Exception {
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code))
            return R.ok();
        code= RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);
        boolean isSend=msmService.send(phone,param);
        if (isSend){
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();}
        else
            return R.error().message("发送失败");

    }
}
