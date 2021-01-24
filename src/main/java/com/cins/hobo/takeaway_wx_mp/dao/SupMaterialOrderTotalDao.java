package com.cins.hobo.takeaway_wx_mp.dao;

import com.cins.hobo.takeaway_wx_mp.entry.SupMaterialOrderTotal;

public interface SupMaterialOrderTotalDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SupMaterialOrderTotal record);

    int insertSelective(SupMaterialOrderTotal record);

    SupMaterialOrderTotal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SupMaterialOrderTotal record);

    int updateByPrimaryKey(SupMaterialOrderTotal record);

    SupMaterialOrderTotal selectOrderByOrderId(String orderId);
}