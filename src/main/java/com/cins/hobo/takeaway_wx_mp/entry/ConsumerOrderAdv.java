package com.cins.hobo.takeaway_wx_mp.entry;

import java.io.Serializable;
import lombok.Data;

/**
 * consumer_order_adv
 * @author 
 */
@Data
public class ConsumerOrderAdv implements Serializable {
    private Integer id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 预约时间
     */
    private String advTime;

    /**
     * 预约订单状态
     */
    private Integer advStatus;

    private static final long serialVersionUID = 1L;
}