package com.cins.hobo.takeaway_wx_mp.entry;

import java.io.Serializable;
import lombok.Data;

/**
 * wx_user_info
 * @author 
 */
@Data
public class WxUserInfo implements Serializable {
    private Integer id;

    /**
     * 微信用户openid
     */
    private String openId;

    /**
     * 微信用户昵称
     */
    private String nickname;

    /**
     * 微信用户手机号码
     */
    private String phoneNum;

    private static final long serialVersionUID = 1L;
}