package com.cins.hobo.takeaway_wx_mp.entry;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * sup_material_order_detail
 * @author 
 */
@Data
public class SupMaterialOrderDetail implements Serializable {
    private Integer id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 原材料id
     */
    private Integer materialId;

    /**
     * 供货商id
     */
    private Integer subUserId;

    /**
     * 原材料数量，单位kg
     */
    private Double materialQuantity;

    /**
     * 原材料单价
     */
    private BigDecimal materialPrice;

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