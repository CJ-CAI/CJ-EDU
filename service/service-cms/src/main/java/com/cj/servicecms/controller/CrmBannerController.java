package com.cj.servicecms.controller;


import com.cj.commonutils.R;
import com.cj.servicecms.entity.CrmBanner;
import com.cj.servicecms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author cj
 * @since 2023-02-04
 */
@RestController
@RequestMapping("EduCms/banner")
@Api(description = "网站首页Banner列表")
public class CrmBannerController {
    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "获取首页banner")
    @GetMapping("getAllBanner")
    public R index(){
        List<CrmBanner> crmBanners = bannerService.selectIndexList();
        return R.ok().data("bannerList",crmBanners);
    }
}

