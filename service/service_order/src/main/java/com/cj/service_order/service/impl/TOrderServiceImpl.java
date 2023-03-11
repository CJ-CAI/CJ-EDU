package com.cj.service_order.service.impl;

import com.cj.commonutils.entity.Order_Course_vo;
import com.cj.commonutils.entity.User_vo;
import com.cj.service_order.client.EduCourseClient;
import com.cj.service_order.client.UcenterClient;
import com.cj.service_order.entity.TOrder;
import com.cj.service_order.mapper.TOrderMapper;
import com.cj.service_order.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cj.service_order.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author cj
 * @since 2023-02-22
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {
    @Autowired
    private EduCourseClient eduCourseClient;
    @Autowired
    private UcenterClient ucenterClient;
    @Override
    public String saveOrder(String courseId, String memberId) {
        Order_Course_vo order_course_vo=eduCourseClient.getOrder_Course(courseId);
        User_vo user_vo=ucenterClient.getOrder_user(memberId);
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(order_course_vo.getTitle());
        order.setCourseCover(order_course_vo.getCover());
        order.setTeacherName("test");
        order.setTotalFee(order_course_vo.getPrice());
        order.setMemberId(memberId);
        order.setMobile(user_vo.getMobile());
        order.setNickname(user_vo.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
