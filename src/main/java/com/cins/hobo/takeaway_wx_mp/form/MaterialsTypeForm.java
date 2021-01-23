package com.cins.hobo.takeaway_wx_mp.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : hobo
 * @className : InsertMetarialsTypeForm
 * @date: 2021/1/23
 * @description: TODO
 */
@Data
public class MaterialsTypeForm {


    @ApiModelProperty("原材料种类名称")
    private String metarialsTypeName;

    @ApiModelProperty("供货商id")
    private Integer supUserId;

}
