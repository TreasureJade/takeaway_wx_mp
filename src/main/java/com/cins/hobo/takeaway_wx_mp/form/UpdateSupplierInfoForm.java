package com.cins.hobo.takeaway_wx_mp.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : hobo
 * @className : UpdateSupplierInfoForm
 * @date: 2021/1/20
 * @description: TODO
 */
@Data
public class UpdateSupplierInfoForm {

    /**
     * 店名
     */
    @ApiModelProperty("供货商店名")
    private String tradeName;

    /**
     * 备用联系方式
     */
    @ApiModelProperty("备用联系方式")
    private String remark;


}
