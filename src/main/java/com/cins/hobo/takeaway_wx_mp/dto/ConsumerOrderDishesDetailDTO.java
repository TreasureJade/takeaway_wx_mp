package com.cins.hobo.takeaway_wx_mp.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : hobo
 * @className : ConsumerOrderDishesDetailDTO
 * @date: 2021/2/1
 * @description: TODO
 */
@Data
public class ConsumerOrderDishesDetailDTO {

    /**
     * 菜品id
     */
    private Integer dishesId;

    /**
     * 菜品数量
     */
    private Integer dishesQuantity;

}
