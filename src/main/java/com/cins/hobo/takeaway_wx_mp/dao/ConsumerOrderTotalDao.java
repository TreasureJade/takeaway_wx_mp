package com.cins.hobo.takeaway_wx_mp.dao;

import com.cins.hobo.takeaway_wx_mp.dto.WxPayBodyDTO;
import com.cins.hobo.takeaway_wx_mp.entry.ConsumerOrderTotal;
import com.cins.hobo.takeaway_wx_mp.vo.ConsumerOrderDetailVO;
import com.cins.hobo.takeaway_wx_mp.vo.ConsumerOrderListVO;

import java.util.List;

public interface ConsumerOrderTotalDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ConsumerOrderTotal record);

    int insertSelective(ConsumerOrderTotal record);

    ConsumerOrderTotal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConsumerOrderTotal record);

    int updateByPrimaryKey(ConsumerOrderTotal record);

    List<ConsumerOrderListVO> getAllOrderByOpenId(String openId);

    ConsumerOrderDetailVO getOrderDetailByOrderId(String orderId);

    WxPayBodyDTO getWxPayBodyByOrderId(String orderId);

    ConsumerOrderTotal selectByOrderId(String orderId);

}