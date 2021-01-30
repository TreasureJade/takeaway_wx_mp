package com.cins.hobo.takeaway_wx_mp.service;

import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;

/**
 * @author : hobo
 * @interfaceName : WxMsgSendService
 * @date: 2021/1/28
 * @description: 微信推送模板消息接口
 */
public interface WxMsgSendService {
    /**
     *
     * @param orderNo 订单号
     * @param createTime 创建时间
     * @param createUsername 创建者
     * @return ResultVO
     */
    ResultVO sendCreateOrderMsg(String openId,String orderNo,String createTime,String createUsername);

}
