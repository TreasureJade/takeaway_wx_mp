package com.cins.hobo.takeaway_wx_mp.enums;

import lombok.Getter;

/**
 * @author : hobo
 * @enumName : PayStatusEnum
 * @date: 2021/2/1
 * @description: TODO
 */
@Getter
public enum PayStatusEnum {

    /**
     * 等待支付
     */
    WAIt(0),
    /**
     * 支付成功
     */
    PAY_SUCCESS(1),
    /**
     * 支付失败
     */
    PAY_FILED(2),
    /**
     * 已退款
     */
    HAVE_REFUND(3)
    ;

    private int payStatu;

    PayStatusEnum(int payStatu) {
        this.payStatu = payStatu;
    }
}
