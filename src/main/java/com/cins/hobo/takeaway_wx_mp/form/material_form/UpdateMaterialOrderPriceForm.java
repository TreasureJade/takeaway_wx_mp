package com.cins.hobo.takeaway_wx_mp.form.material_form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
 * @author : hobo
 * @className : UpdateMaterialOrderPriceForm
 * @date: 2021/1/23
 * @description: TODO
 */
@Data
public class UpdateMaterialOrderPriceForm {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("材料单价")
    private BigDecimal price;

}
