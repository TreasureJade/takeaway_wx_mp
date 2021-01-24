package com.cins.hobo.takeaway_wx_mp.entry;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * sup_material_order_total
 * @author 
 */
@Data
public class SupMaterialOrderTotal implements Serializable {
    private Integer id;

    /**
     * 订单id

     */
    private String orderId;

    /**
     * 供货商id
     */
    private Integer supUserId;

    /**
     * 订单总价
     */
    private BigDecimal totalPrice;

    /**
     * 订单状态
     */
    private Integer orderStatus;

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