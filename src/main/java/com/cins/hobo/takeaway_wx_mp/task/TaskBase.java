package com.cins.hobo.takeaway_wx_mp.task;

import lombok.Data;

/**
 * @author : hobo
 * @className : TaskBase
 * @date: 2021/3/8
 * @description: 业务数据类
 */
@Data
public class TaskBase {


    private String orderId;
    private String openId;
    private String advTime;

    public TaskBase(String orderId, String openId, String advTime) {
        this.orderId = orderId;
        this.openId = openId;
        this.advTime = advTime;
    }
}
