package com.cins.hobo.takeaway_wx_mp.dao;

import com.cins.hobo.takeaway_wx_mp.entry.ConsumerOrderAdv;

public interface ConsumerOrderAdvDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ConsumerOrderAdv record);

    int insertSelective(ConsumerOrderAdv record);

    ConsumerOrderAdv selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConsumerOrderAdv record);

    int updateByPrimaryKey(ConsumerOrderAdv record);

    ConsumerOrderAdv selectByOrderId(String orderId);
}