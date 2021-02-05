package com.cins.hobo.takeaway_wx_mp.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : hobo
 * @className : WxPayBodyDTO
 * @date: 2021/2/3
 * @description: TODO
 */
@Data
public class WxPayBodyDTO {

    private String orderId;

    private String openId;

    private BigDecimal orderAmount;

}
