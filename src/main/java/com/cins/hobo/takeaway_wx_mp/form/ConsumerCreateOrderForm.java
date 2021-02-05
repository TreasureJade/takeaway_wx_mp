package com.cins.hobo.takeaway_wx_mp.form;

import com.cins.hobo.takeaway_wx_mp.dto.ConsumerOrderDishesDetailDTO;
import lombok.Data;

import java.util.List;

/**
 * @author : hobo
 * @className : ConsumerCreateOrderForm
 * @date: 2021/2/1
 * @description: TODO
 */
@Data
public class ConsumerCreateOrderForm {

    /**
     * 微信openid
     */
    private String openId;

    /**
     * 消费者用户名
     */
    private String userName;

    /**
     * 消费者电话号码
     */
    private String userPhoneNum;

    /**
     * 消费者收货地址
     */
    private String userAddress;

    /**
     * 购物车商品
     */
    private List<ConsumerOrderDishesDetailDTO> detailList;


}
