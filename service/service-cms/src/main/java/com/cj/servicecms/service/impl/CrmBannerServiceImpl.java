package com.cj.servicecms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cj.servicecms.entity.CrmBanner;
import com.cj.servicecms.mapper.CrmBannerMapper;
import com.cj.servicecms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author cj
 * @since 2023-02-04
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    @Cacheable(value = "banner", key = "'selectIndexList'")
    public List<CrmBanner> selectIndexList() {
        QueryWrapper<CrmBanner> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    @CacheEvict(value = "banner",allEntries = true)
    public boolean save(CrmBanner entity) {
        return super.save(entity);
    }

    @Override
    @CacheEvict(value = "banner",allEntries = true)
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    @CacheEvict(value = "banner",allEntries = true)
    public boolean updateById(CrmBanner entity) {
        return super.updateById(entity);
    }
}
