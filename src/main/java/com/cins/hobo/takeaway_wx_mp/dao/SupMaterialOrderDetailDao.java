package com.cins.hobo.takeaway_wx_mp.dao;

import com.cins.hobo.takeaway_wx_mp.entry.SupMaterialOrderDetail;
import com.cins.hobo.takeaway_wx_mp.vo.SupOrderListVO;

import java.util.List;

public interface SupMaterialOrderDetailDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SupMaterialOrderDetail record);

    int insertSelective(SupMaterialOrderDetail record);

    SupMaterialOrderDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SupMaterialOrderDetail record);

    int updateByPrimaryKey(SupMaterialOrderDetail record);

    List<SupMaterialOrderDetail> getDetailsBySubUserId(Integer subUserId);

    List<SupOrderListVO> getSupUserAllNewOrder(Integer supUserId, Integer orderStatus);
}