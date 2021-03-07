package com.cins.hobo.takeaway_wx_mp.vo;

import com.cins.hobo.takeaway_wx_mp.entry.LeaveMsg;
import lombok.Data;

import java.util.List;

/**
 * @author : hobo
 * @className : LeaveMsgListVO
 * @date: 2021/3/6
 * @description: TODO
 */
@Data
public class LeaveMsgListVO {

    private Integer id;

    private Integer pid;

    private String openId;

    private String nickName;

    private String msg;

    private String createTime;

    private String phoneNum;

    private List<LeaveMsg> replyList;

}
