package com.cins.hobo.takeaway_wx_mp.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : hobo
 * @className : UpdateDishTypeForm
 * @date: 2021/1/28
 * @description: TODO
 */
@Data
public class UpdateDishTypeForm {

    @ApiModelProperty("菜品种类id")
    private Integer id;

    @ApiModelProperty("菜品种类名称")
    private String dishesTypeName;

}
