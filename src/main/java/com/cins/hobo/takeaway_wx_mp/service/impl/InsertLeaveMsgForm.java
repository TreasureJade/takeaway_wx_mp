package com.cins.hobo.takeaway_wx_mp.service.impl;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : hobo
 * @className : InsertLeaveMsgForm
 * @date: 2021/3/6
 * @description: TODO
 */
@Data
public class InsertLeaveMsgForm {

    @ApiModelProperty("pid 为0为一级评论")
    private Integer pid;

    @ApiModelProperty("openId")
    private String openId;

    @ApiModelProperty("手机号码，非必填")
    private String phoneNum;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("留言内容")
    private String msg;

}
