package com.cj.service_uCenter.controller.WxApi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cj.commonutils.util.JwtUtils;
import com.cj.service_uCenter.entity.UcenterMember;
import com.cj.service_uCenter.service.UcenterMemberService;
import com.cj.service_uCenter.utils.ConstantPropertiesUtil;
import com.cj.service_uCenter.utils.HttpClientUtils;
import com.cj.servicebase.exception.GuliException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/U_center/wx")
public class WxApiController {
    @Autowired
    private UcenterMemberService ucenterMemberService;
    @GetMapping("login")
    public String genQrConnect(HttpSession session){
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        // 回调地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl= URLEncoder.encode(redirectUrl,"UTF-8");
        }catch (UnsupportedEncodingException e){
            throw new GuliException(20001,e.getMessage());
        }
        String state = "localhost";
//        System.out.println("state = " + state);
//        System.out.println(ConstantPropertiesUtil.WX_OPEN_APP_ID);
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state);
        return "redirect:" + qrcodeUrl;
    }
    @GetMapping("callback")
    public String callback(String code,String state,HttpSession session){
//        System.out.println(code);
//        System.out.println(state);
        //向认证服务器发送请求换取access_token
        String token=null;
        String baseAccessTokenUrl =
                "https://api.weixin.qq.com/sns/oauth2/access_token" +
                        "?appid=%s" +
                        "&secret=%s" +
                        "&code=%s" +
                        "&grant_type=authorization_code";
        String accessTokenUrl=String.format(baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);
        String result=null;
        try{
            result= HttpClientUtils.get(accessTokenUrl);
        }catch (Exception e){
            throw new GuliException(20001, "获取access_token失败");
        }
        Gson gson=new Gson();
        HashMap map=gson.fromJson(result, HashMap.class);
        String accessToken=(String)map.get("access_token");
        String openid=(String)map.get("openid");
        UcenterMember ucenterMember=ucenterMemberService.getOne(new QueryWrapper<UcenterMember>().eq("openid", openid));
        if(ucenterMember==null){
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl=String.format(baseUserInfoUrl,accessToken,openid);
            String user_result=null;
            try{
                user_result=HttpClientUtils.get(userInfoUrl);
            }catch (Exception e){
                throw new GuliException(20001,"获取用户信息失败");
            }
            HashMap<String,Object> userInfo_map=gson.fromJson(user_result, HashMap.class);
            String nickname=(String)userInfo_map.get("nickname");
            String headimgurl=(String)userInfo_map.get("headimgurl");
            UcenterMember ucenterMember_add=new UcenterMember();
            ucenterMember_add.setNickname(nickname);
            ucenterMember_add.setOpenid(openid);
            ucenterMember_add.setAvatar(headimgurl);
            ucenterMemberService.save(ucenterMember_add);
            token = JwtUtils.getJwtToken(ucenterMember_add.getId(),ucenterMember_add.getNickname());
        }
        else
            token = JwtUtils.getJwtToken(ucenterMember.getId(),ucenterMember.getNickname());
//        System.out.println("ucenterMember.getId():"+ucenterMember.getId());
//        System.out.println("token:"+token);
        return "redirect:http://localhost:3000?token="+token;
    }
}
