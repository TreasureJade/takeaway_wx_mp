package com.cins.hobo.takeaway_wx_mp.service.impl;

import com.cins.hobo.takeaway_wx_mp.dao.ConsumerOrderTotalDao;
import com.cins.hobo.takeaway_wx_mp.entry.ConsumerOrderTotal;
import com.cins.hobo.takeaway_wx_mp.enums.OrderStatusEnum;
import com.cins.hobo.takeaway_wx_mp.enums.PayStatusEnum;
import com.cins.hobo.takeaway_wx_mp.service.PayService;
import com.cins.hobo.takeaway_wx_mp.util.TimeUtils;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.cins.hobo.takeaway_wx_mp.enums.ResultEnum;
import com.cins.hobo.takeaway_wx_mp.service.WxMsgSendService;
import com.cins.hobo.takeaway_wx_mp.util.IpUtil;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @author : hobo
 * @className : WxPayServiceImpl
 * @date: 2021/2/1
 * @description: TODO
 */
@Service
@Slf4j
public class WxPayServiceImpl implements PayService {

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private WxMsgSendService msgSendService;

    @Autowired
    private ConsumerOrderTotalDao orderTotalDao;

    @Value("${wx.mp.config.notify_url}")
    private String notifyUrl;

    @Override
    public ResultVO create(String orderId, HttpServletRequest request) {
        ConsumerOrderTotal orderTotal = orderTotalDao.selectByOrderId(orderId);
        if (orderTotal == null) {
            return ResultVO.error(ResultEnum.ORDER_NOT_EXIST);
        }
        String userIp = IpUtil.getIpAddr(request);
        BigDecimal temp = orderTotal.getOrderAmount().multiply(BigDecimal.valueOf(100));
        Integer totalFee = Integer.valueOf(String.valueOf(temp));
        WxPayUnifiedOrderRequest prepayInfo = WxPayUnifiedOrderRequest.newBuilder()
                .openid(orderTotal.getOpenId())
                .outTradeNo(orderTotal.getOrderId())
                .totalFee(totalFee)
                .body("用户下单")
                .tradeType("JSAPI")
                .spbillCreateIp(userIp)
                .notifyUrl(notifyUrl)
                .build();

        try {
            WxPayMpOrderResult result = wxPayService.createOrder(prepayInfo);
            log.info("预支付成功，订单号为:{}", orderId);
            //订单存库
            orderTotal.setUpdateTime(TimeUtils.getTimeCN());
            orderTotalDao.updateByPrimaryKeySelective(orderTotal);
            return ResultVO.success(result);
        } catch (WxPayException e) {
            log.error("预支付失败，失败原因:{}", e.getReturnMsg());
            return ResultVO.error(ResultEnum.PAY_ERROR);
        }
    }

    @Override
    public ResultVO wxNotify(String xmlData) {
        try {
            WxPayOrderNotifyResult notifyResult = this.wxPayService.parseOrderNotifyResult(xmlData);
            ConsumerOrderTotal order = orderTotalDao.selectByOrderId(notifyResult.getOutTradeNo());
            if (order != null) {
                if (!order.getPayStatus().equals(OrderStatusEnum.HAS_BEEN_PROCESSED.getCode())
                        && !order.getPayStatus().equals(PayStatusEnum.PAY_SUCCESS.getPayStatu())) {
                    order.setPayStatus(PayStatusEnum.PAY_SUCCESS.getPayStatu());
                    order.setOrderStatus(OrderStatusEnum.HAS_BEEN_PROCESSED.getCode());
                    orderTotalDao.updateByPrimaryKeySelective(order);
                    log.info("订单号为:{} 支付成功!",notifyResult.getOutTradeNo());
                    return ResultVO.success();
                }
                order.setOrderStatus(OrderStatusEnum.HAS_BEEN_PROCESSED.getCode());
                order.setPayStatus(PayStatusEnum.PAY_FILED.getPayStatu());
                orderTotalDao.updateByPrimaryKeySelective(order);
                return ResultVO.error(ResultEnum.PAY_FIlED);
            }
            return ResultVO.error(ResultEnum.ORDER_NOT_EXIST);
        } catch (WxPayException e) {
            log.error("支付失败，失败原因:{}",e.getReturnMsg());
            return ResultVO.error(ResultEnum.SERVER_ERROR);
        }
    }
}
