package com.cj.service_order.service;

import com.cj.service_order.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author cj
 * @since 2023-02-22
 */
public interface TOrderService extends IService<TOrder> {

    String saveOrder(String courseId, String memberId);
}
