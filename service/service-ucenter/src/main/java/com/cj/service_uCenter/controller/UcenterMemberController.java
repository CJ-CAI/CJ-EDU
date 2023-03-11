package com.cj.service_uCenter.controller;


import com.cj.commonutils.R;
import com.cj.commonutils.entity.User_vo;
import com.cj.commonutils.util.JwtUtils;
import com.cj.service_uCenter.entity.UcenterMember;
import com.cj.service_uCenter.entity.vo.LoginVo;
import com.cj.service_uCenter.entity.vo.RegisterVo;
import com.cj.service_uCenter.service.UcenterMemberService;
import com.cj.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author cj
 * @since 2023-02-09
 */
@RestController
@RequestMapping("/U_center/")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo){
        String token=memberService.login(loginVo);
        return R.ok().data("token",token);
    }
    @PostMapping("register")
    public  R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }
    @GetMapping("getLoginInfo")
//    返回数据包括密码未封装，有安全问题,已解决
    public  R getLoginInfo(HttpServletRequest request){
        try{
            String id= JwtUtils.getMemberIdByJwtToken(request);
//            System.out.println("id:"+id);
            UcenterMember ucenterMember=memberService.getById(id);
            User_vo user_vo=new User_vo();
            BeanUtils.copyProperties(ucenterMember,user_vo);
//            System.out.println("getInfo:"+ucenterMember);
            return R.ok().data("item",user_vo);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"error");
        }
    }
    @PostMapping("/getOrder_user/{id}")
    public User_vo getOrder_user(@PathVariable String id){
        UcenterMember ucenterMember=memberService.getById(id);
        User_vo user_vo=new User_vo();
        BeanUtils.copyProperties(ucenterMember,user_vo);
        return  user_vo;
    }
//    ============================admin=====================================================
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day){
        Integer count=memberService.countRegister(day);
        return R.ok().data("count",count);
    }
}

