package com.cins.hobo.takeaway_wx_mp.vo;

import com.cins.hobo.takeaway_wx_mp.dto.ConsumerOrderDetailDTO;
import lombok.Data;

import java.util.List;

/**
 * @author : hobo
 * @className : ConsumerOrderDetailVO
 * @date: 2021/2/3
 * @description: TODO
 */
@Data
public class ConsumerOrderDetailVO {

    private String orderId;

    private String orderAmount;

    private String createTime;

    private List<ConsumerOrderDetailDTO> detailList;


}
