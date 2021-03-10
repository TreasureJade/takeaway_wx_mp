package com.cins.hobo.takeaway_wx_mp.service.impl;

import com.cins.hobo.takeaway_wx_mp.enums.ResultEnum;
import com.cins.hobo.takeaway_wx_mp.service.WxMsgSendService;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author : hobo
 * @className : WxMsgSendServiceImpl
 * @date: 2021/1/28
 * @description: TODO
 */
@Service
@Slf4j
public class WxMsgSendServiceImpl implements WxMsgSendService {

    @Autowired
    private WxMpService wxMpService;

    @Value("${wx.mp.template_id.sup_order_create}")
    private String supOrderCreateOrderId;

    @Value("${wx.mp.template_id.adv_order_create}")
    private String advOrderCreateId;

    @Override
    public ResultVO sendCreateOrderMsg(String openId,String orderNo, String createTime, String createUsername) {
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openId)
                .templateId(supOrderCreateOrderId)
                .url("http://a.acgweb.net/index.html#order")
                .build();
        templateMessage.addData(new WxMpTemplateData("keyword1", orderNo));
        templateMessage.addData(new WxMpTemplateData("keyword2", createTime));
        templateMessage.addData(new WxMpTemplateData("keyword3", createUsername));
        templateMessage.addData(new WxMpTemplateData("remark", "请尽快配送"));
        try {
            String result = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            return ResultVO.success(result);
        } catch (WxErrorException e) {
            log.error("消息发送失败，失败原因:{}", e.getMessage());
            return ResultVO.error(ResultEnum.SERVER_ERROR);
        }
    }

    @Override
    public ResultVO sendCreateAdvOrderMsg(String openId, String advTime, String orderId){
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openId)
                .templateId(advOrderCreateId)
                .url("http://a.acgweb.net/index.html#order")
                .build();
        templateMessage.addData(new WxMpTemplateData("keyword1",orderId));
        templateMessage.addData(new WxMpTemplateData("keyword2",advTime));
        templateMessage.addData(new WxMpTemplateData("remark","请尽快支付哦～"));

        try {
            String result = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            return ResultVO.success(result);
        } catch (WxErrorException e) {
            log.error("消息发送失败，失败原因:{}", e.getMessage());
            return ResultVO.error(ResultEnum.SERVER_ERROR);
        }

    }

}
