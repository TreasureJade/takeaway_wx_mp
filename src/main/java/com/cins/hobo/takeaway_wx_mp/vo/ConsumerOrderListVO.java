package com.cins.hobo.takeaway_wx_mp.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : hobo
 * @className : ConsumerOrderListVO
 * @date: 2021/2/3
 * @description: TODO
 */
@Data
public class ConsumerOrderListVO {

    private String orderId;

    private String openId;

    private BigDecimal orderAmount;

    private Integer orderStatus;

    private Integer payStatus;

    private String createTime;

}
