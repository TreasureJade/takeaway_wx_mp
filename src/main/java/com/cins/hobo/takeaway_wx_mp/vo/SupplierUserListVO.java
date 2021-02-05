package com.cins.hobo.takeaway_wx_mp.vo;

import lombok.Data;

/**
 * @author : hobo
 * @className : SupplierUserListVO
 * @date: 2021/1/20
 * @description: TODO
 */
@Data
public class SupplierUserListVO {

    private Integer id;

    private String phoneNum;

    /**
     * 店名
     */
    private String tradeName;

    /**
     * 备用联系方式
     */
    private String remark;

    private String openId;


}
