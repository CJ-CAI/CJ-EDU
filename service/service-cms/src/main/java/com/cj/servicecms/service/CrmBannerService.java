package com.cj.servicecms.service;

import com.cj.servicecms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author cj
 * @since 2023-02-04
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectIndexList();
}
