package com.cins.hobo.takeaway_wx_mp.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : hobo
 * @className : ConsumerOrderDetailDTO
 * @date: 2021/2/3
 * @description: TODO
 */
@Data
public class ConsumerOrderDetailDTO {

    private Integer dishesId;

    private String dishName;

    private Integer dishesQuantity;

    private BigDecimal dishesPrice;

    private String dishPicUrl;

}
