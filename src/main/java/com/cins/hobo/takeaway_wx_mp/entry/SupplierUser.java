package com.cins.hobo.takeaway_wx_mp.entry;

import java.io.Serializable;
import lombok.Data;

/**
 * supplier_user
 * @author 
 */
@Data
public class SupplierUser implements Serializable {
    private Integer id;

    /**
     * 供货商手机号码
     */
    private String phoneNum;

    /**
     * 店名
     */
    private String tradeName;

    /**
     * 备用联系方式
     */
    private String remark;

    /**
     * 微信openid
     */
    private String openId;

    private static final long serialVersionUID = 1L;
}