package com.cins.hobo.takeaway_wx_mp.entry;

import java.io.Serializable;
import lombok.Data;

/**
 * leave_msg
 * @author 
 */
@Data
public class LeaveMsg implements Serializable {
    private Integer id;

    private Integer pid;

    private String openId;

    private String nickName;

    private String phoneNum;

    private String msg;

    private String createTime;

    private static final long serialVersionUID = 1L;
}