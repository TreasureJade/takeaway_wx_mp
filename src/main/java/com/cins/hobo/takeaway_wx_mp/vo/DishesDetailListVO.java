package com.cins.hobo.takeaway_wx_mp.vo;

import com.cins.hobo.takeaway_wx_mp.entry.DishesDetail;
import lombok.Data;

import java.util.List;

/**
 * @author : hobo
 * @className : DishesDetailListVO
 * @date: 2021/1/22
 * @description: TODO
 */
@Data
public class DishesDetailListVO {

    private Integer typeId;

    private String typeName;

    private List<DishesDetail> dishesDetailList;
}
