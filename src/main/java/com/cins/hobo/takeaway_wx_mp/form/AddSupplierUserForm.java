package com.cins.hobo.takeaway_wx_mp.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : hobo
 * @className : AddSupplierUserForm
 * @date: 2021/1/20
 * @description: TODO
 */
@Data
public class AddSupplierUserForm {
    @ApiModelProperty("用户名")
    private String username;
    /**
     * 店名
     */
    @ApiModelProperty("供货商店铺名称")
    private String tradeName;

    /**
     * 备用联系方式
     */
    @ApiModelProperty("备用联系方式 *非必填项")
    private String remark;
}
