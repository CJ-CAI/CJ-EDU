package com.cj.service_order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cj.service_order.entity.TOrder;
import com.cj.service_order.entity.TPayLog;
import com.cj.service_order.mapper.TPayLogMapper;
import com.cj.service_order.service.TPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cj.service_order.utils.HttpClient;
import com.cj.servicebase.exception.GuliException;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author cj
 * @since 2023-02-22
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {

    @Autowired
    private TOrderServiceImpl tOrderService;
    @Override
    public Map createNative(String orderNo) {
        try {
            QueryWrapper<TOrder> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("order_no",orderNo);
            TOrder tOrder=tOrderService.getOne(queryWrapper);
            Map m=new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", tOrder.getCourseTitle());
            m.put("out_trade_no", orderNo);
            m.put("total_fee", tOrder.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            m.put("trade_type", "NATIVE");

            HttpClient client=new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            String result_xml=client.getContent();
            Map<String,String> resultMap=WXPayUtil.xmlToMap(result_xml);

            Map map=new HashMap<>();
            map.put("out_trade_no",orderNo);
            map.put("course_id",tOrder.getCourseId());
            map.put("total_fee",tOrder.getTotalFee());
            map .put("result_code",resultMap.get("result_code"));
            map.put("code_url",resultMap.get("code_url"));
            //微信支付二维码2小时过期，可采取2小时未支付取消订单
            //redisTemplate.opsForValue().set(orderNo, map, 120,TimeUnit.MINUTES);
            return map;
        }catch(Exception e){
            throw  new GuliException(2001,"获取微信支付失败"+e.getMessage());
        }
    }

    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            HttpClient httpClient=new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            httpClient.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            httpClient.setHttps(true);
            httpClient.post();
            String xml=httpClient.getContent();
            Map<String,String> map=WXPayUtil.xmlToMap(xml);
            return map;
        }catch (Exception e){
            throw new GuliException(20001,"查询支付状态失败");
        }
    }

    @Override
    public void updateOrderStatus(Map<String, String> map) {
        String orderNo=map.get("out_trade_no");
        QueryWrapper<TOrder> wrapper=new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        TOrder order=tOrderService.getOne(wrapper);
        if(order.getStatus().intValue()==1)return;
        order.setStatus(1);
        tOrderService.updateById(order);
        TPayLog tPayLog=new TPayLog();
        tPayLog.setOrderNo(order.getOrderNo());
        tPayLog.setPayTime(new Date());
        tPayLog.setPayType(1);
        tPayLog.setTotalFee(order.getTotalFee());
        tPayLog.setTradeState(map.get("trade_state"));
        tPayLog.setTransactionId(map.get("transaction_id"));
        tPayLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(tPayLog);
    }
}
