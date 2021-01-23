package com.cins.hobo.takeaway_wx_mp.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : hobo
 * @className : MaterialsForm
 * @date: 2021/1/23
 * @description: TODO
 */
@Data
public class MaterialsForm {

    @ApiModelProperty("原材料名称")
    private String metarialsName;

    @ApiModelProperty("原材料分类id")
    private Integer metarialsTypeId;
}
