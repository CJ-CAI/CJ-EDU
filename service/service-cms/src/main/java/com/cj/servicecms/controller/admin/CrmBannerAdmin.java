package com.cj.servicecms.controller.admin;

import com.cj.commonutils.R;
import com.cj.servicecms.client.OssClient;
import com.cj.servicecms.entity.CrmBanner;
import com.cj.servicecms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("EduCms/banner/admin")
public class CrmBannerAdmin {
    @Autowired
    private OssClient ossClient;
    @Autowired
    private CrmBannerService crmBannerService;
    @GetMapping("getById/{id}")
    public R get(@PathVariable String id){
        CrmBanner crmBanner=crmBannerService.getById(id);
        return R.ok().data("banner",crmBanner);
    }
    @PostMapping("add")
    public R add(@RequestBody CrmBanner crmBanner){
        return crmBannerService.save(crmBanner)?R.ok():R.error();
    }
    @PostMapping("update")
    public R update(@RequestBody CrmBanner crmBanner){
        System.out.println("updateCrmBanner: "+crmBanner);
        return crmBannerService.updateById(crmBanner)?R.ok():R.error();
    }
    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable String id){
        ossClient.delete_file(crmBannerService.getById(id).getImageUrl());
        return  crmBannerService.removeById(id)?R.ok():R.error();
    }
}
