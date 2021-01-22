package com.cins.hobo.takeaway_wx_mp.entry;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * dishes_detail
 * @author 
 */
@Data
public class DishesDetail implements Serializable {
    private Integer id;

    /**
     * 菜品名称
     */
    private String dishName;

    /**
     * 菜品种类
     */
    private Integer dishTypeId;

    /**
     * 基本介绍
     */
    private String basicIntro;

    /**
     * 原价
     */
    private BigDecimal oriPrice;

    /**
     * 特惠价
     */
    private BigDecimal prePrice;

    /**
     * 图片
     */
    private String dishPicUrl;

    private static final long serialVersionUID = 1L;
}