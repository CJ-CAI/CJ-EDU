package com.cj.serviceedu.controller;

import com.cj.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/EduService/user")
public class EduLoginController {
    @PostMapping("login")
    public R login(){
        System.out.println("客户登录");
        return R.ok().data("token","admin");
    }
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://guli-edu-0114.oss-cn-hangzhou.aliyuncs.com/8xXW.gif");
    }
//    @GetMapping(value = "/img",produces = MediaType.IMAGE_PNG_VALUE)
//    public byte[] getImage() throws Exception {
//        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(new File("E:/Downloads/8xXW.gif")));
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ImageIO.write(bufferedImage, "png", out);
//        return out.toByteArray();
//    }
}
