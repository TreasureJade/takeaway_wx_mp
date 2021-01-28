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

    @ApiModelProperty("供货商手机号")
    private String phoneNum;

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


    @ApiModelProperty("微信用户id")
    private String openId;

}
