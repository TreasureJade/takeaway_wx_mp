package com.cins.hobo.takeaway_wx_mp.enums;

import lombok.Getter;

/**
 * @author : hobo
 * @enumName : OrderStatusEnum
 * @date: 2021/1/24
 * @description: TODO
 */
@Getter
public enum OrderStatusEnum {
    /**
     * 新订单
     */
    NEW(0),

    /**
     * 已被处理
     */
    HAS_BEEN_PROCESSED(1),

    /**
     * 已完成
     */
    FINISHED(2)
    ;

    private Integer code;

    OrderStatusEnum(Integer code) {
        this.code = code;
    }
}
