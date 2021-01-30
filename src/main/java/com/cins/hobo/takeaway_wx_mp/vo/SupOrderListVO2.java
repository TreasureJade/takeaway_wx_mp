package com.cins.hobo.takeaway_wx_mp.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : hobo
 * @className : SupOrderListVO2
 * @date: 2021/1/30
 * @description: TODO
 */
@Data
@ApiModel
public class SupOrderListVO2 {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("订单id")
    private String orderId;

    @ApiModelProperty("供货商id")
    private String supUserId;

    @ApiModelProperty("供货商店铺名")
    private String tradeName;

    @ApiModelProperty("订单总价")
    private BigDecimal totalPrice;

    @ApiModelProperty("订单状态 1：新订单 2：已被供货商处理 3：订单已完成")
    private Integer orderStatus;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("更新时间")
    private String updateTime;


}
