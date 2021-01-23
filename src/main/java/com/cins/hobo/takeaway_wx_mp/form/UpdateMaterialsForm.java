package com.cins.hobo.takeaway_wx_mp.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : hobo
 * @className : UpdateMetarialsForm
 * @date: 2021/1/23
 * @description: TODO
 */
@Data
public class UpdateMaterialsForm {

    @ApiModelProperty("原材料id")
    private Integer id;

    @ApiModelProperty("原材料名称")
    private String metarialsName;

    @ApiModelProperty("原材料分类id")
    private Integer metarialsTypeId;
}
