package com.cj.service_uCenter.service;

import com.cj.service_uCenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cj.service_uCenter.entity.vo.LoginVo;
import com.cj.service_uCenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author cj
 * @since 2023-02-09
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    void register(RegisterVo registerVo);

    String login(LoginVo loginVo);

    Integer countRegister(String day);
}
