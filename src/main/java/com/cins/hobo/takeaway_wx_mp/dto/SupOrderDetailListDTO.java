package com.cins.hobo.takeaway_wx_mp.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : hobo
 * @className : SupOrderDetailListDTO
 * @date: 2021/1/30
 * @description: TODO
 */
@Data
public class SupOrderDetailListDTO {

    private Integer id;

    private Integer materialId;

    private String materialsName;

    /**
     * 原材料数量，单位kg
     */
    private Double materialQuantity;

    private BigDecimal materialPrice;

    private BigDecimal materialTotalPrice;

    private String createTime;

    private String updateTime;


}
