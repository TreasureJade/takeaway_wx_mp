package com.cins.hobo.takeaway_wx_mp.dto;

import lombok.Data;

/**
 * @author : hobo
 * @className : CreateMaterialsOrderDTO
 * @date: 2021/1/24
 * @description: TODO
 */
@Data
public class CreateMaterialsOrderDTO {

    private Integer materialId;

    /**
     * 原材料数量，单位kg
     */
    private Double materialQuantity;


}
