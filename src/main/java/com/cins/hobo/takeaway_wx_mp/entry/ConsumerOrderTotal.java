package com.cins.hobo.takeaway_wx_mp.entry;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * consumer_order_total
 * @author 
 */
@Data
public class ConsumerOrderTotal implements Serializable {
    private Integer id;

    /**
     * 微信openid
     */
    private String openId;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 消费者用户名
     */
    private String userName;

    /**
     * 消费者电话号码
     */
    private String userPhoneNum;

    /**
     * 消费者收货地址
     */
    private String userAddress;

    /**
     * 订单总价
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 支付状态
     */
    private Integer payStatus;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;
}