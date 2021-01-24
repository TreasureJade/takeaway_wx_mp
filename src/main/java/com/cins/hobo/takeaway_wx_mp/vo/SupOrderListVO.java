package com.cins.hobo.takeaway_wx_mp.vo;

import lombok.Data;

/**
 * @author : hobo
 * @className : SupOrderListVO
 * @date: 2021/1/24
 * @description: 供货商查询未处理订单相关vo
 */
@Data
public class SupOrderListVO {

    private Integer id;

    private String  orderId;

    private Integer materialId;

    private String materialsName;

    /**
     * 原材料数量，单位kg
     */
    private Double materialQuantity;

}
