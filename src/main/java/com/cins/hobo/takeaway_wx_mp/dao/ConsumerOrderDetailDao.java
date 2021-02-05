package com.cins.hobo.takeaway_wx_mp.dao;

import com.cins.hobo.takeaway_wx_mp.dto.ConsumerOrderDetailDTO;
import com.cins.hobo.takeaway_wx_mp.entry.ConsumerOrderDetail;

import java.util.List;

public interface ConsumerOrderDetailDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ConsumerOrderDetail record);

    int insertSelective(ConsumerOrderDetail record);

    ConsumerOrderDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConsumerOrderDetail record);

    int updateByPrimaryKey(ConsumerOrderDetail record);

    List<ConsumerOrderDetailDTO> selectByOrderId(String orderId);
}