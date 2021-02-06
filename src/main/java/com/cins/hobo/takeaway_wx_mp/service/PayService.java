package com.cins.hobo.takeaway_wx_mp.service;

import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : hobo
 * @interfaceName : PayService
 * @date: 2021/2/1
 * @description: 微信支付
 */
public interface PayService {

    /**
     * 预支付
     *
     * @return
     */
    ResultVO create(String orderId, HttpServletRequest request);

    /**
     * 回调
     *
     * @return
     */
    String wxNotify(String xmlData);

}
