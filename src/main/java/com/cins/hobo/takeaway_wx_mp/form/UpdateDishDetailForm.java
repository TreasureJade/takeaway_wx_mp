package com.cins.hobo.takeaway_wx_mp.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

/**
 * @author : hobo
 * @className : UpdateDishDetailForm
 * @date: 2021/1/21
 * @description: 更新菜品详细相关form
 */
@Data
public class UpdateDishDetailForm {

    @ApiModelProperty("菜品id")
    private Integer id;

    /**
     * 菜品名称
     */
    @ApiModelProperty("菜品名称")
    private String dishName;

    /**
     * 菜品种类
     */
    @ApiModelProperty("菜品种类id")
    private Integer dishTypeId;

    /**
     * 基本介绍
     */
    @ApiModelProperty("菜品基本介绍")
    private String basicIntro;

    /**
     * 原价
     */
    @ApiModelProperty("菜品原价")
    private BigDecimal oriPrice;

    /**
     * 特惠价
     */
    @ApiModelProperty("菜品特惠价")
    private BigDecimal prePrice;


}
