package com.cj.service_uCenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cj.commonutils.util.JwtUtils;
import com.cj.commonutils.util.MD5;
import com.cj.service_uCenter.entity.UcenterMember;
import com.cj.service_uCenter.entity.vo.LoginVo;
import com.cj.service_uCenter.entity.vo.RegisterVo;
import com.cj.service_uCenter.mapper.UcenterMemberMapper;
import com.cj.service_uCenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cj.servicebase.exception.GuliException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author cj
 * @since 2023-02-09
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public void register(RegisterVo registerVo) {
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
        //校验参数
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code))
            throw new GuliException(20001,"注册参数未填写完整");
        String redis_code=redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redis_code))
            throw new GuliException(20001,"验证码错误");
        Integer count=baseMapper.selectCount(new QueryWrapper<UcenterMember>().eq("mobile",mobile));
        if(count>0)
            throw new GuliException(20001,"该手机号已注册");
        UcenterMember ucenterMember= new UcenterMember();
        BeanUtils.copyProperties(registerVo,ucenterMember);
        ucenterMember.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        ucenterMember.setPassword(MD5.encrypt(ucenterMember.getPassword()));
        this.save(ucenterMember);
    }

    @Override
    public String login(LoginVo loginVo) {
        String phoneNumber=loginVo.getMobile();
        String pwd=loginVo.getPassword();
        if(StringUtils.isEmpty(phoneNumber)||StringUtils.isEmpty(pwd))
            throw new GuliException(20001,"手机或密码为空");
        UcenterMember ucenterMember=baseMapper.selectOne(new QueryWrapper<UcenterMember>().eq("mobile",phoneNumber));
        if(null==ucenterMember)
            throw new GuliException(20001,"查询不到此账户");
        if(!MD5.encrypt(pwd).equals(ucenterMember.getPassword()))
            throw new GuliException(20001,"密码错误");
        if(ucenterMember.getIsDeleted())
            throw new GuliException(20001,"此账号已禁用");
        String token= JwtUtils.getJwtToken(ucenterMember.getId(),ucenterMember.getNickname());
        return token;
    }

    @Override
    public Integer countRegister(String day) {
        return baseMapper.selectRegisterCount(day);
    }
}
