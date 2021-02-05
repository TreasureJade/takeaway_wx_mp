package com.cins.hobo.takeaway_wx_mp.entry;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * consumer_order_detail
 * @author 
 */
@Data
public class ConsumerOrderDetail implements Serializable {
    private Integer id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 菜品id
     */
    private Integer dishesId;

    /**
     * 菜品数量
     */
    private Integer dishesQuantity;

    /**
     * 菜品价格
     */
    private BigDecimal dishesPrice;

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