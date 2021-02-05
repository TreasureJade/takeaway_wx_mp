package com.cins.hobo.takeaway_wx_mp.service;

import com.cins.hobo.takeaway_wx_mp.form.ConsumerCreateOrderForm;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;

/**
 * @author : hobo
 * @interfaceName : ConsumerOrderService
 * @date: 2021/2/1
 * @description: 消费者相关接口
 */
public interface ConsumerOrderService {


    ResultVO createOrder(ConsumerCreateOrderForm orderForm);

    ResultVO getOrderDetail(String orderId);

    ResultVO getAllOrder(String openId);


}
