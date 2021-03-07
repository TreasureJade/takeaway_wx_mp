package com.cins.hobo.takeaway_wx_mp.dao;

import com.cins.hobo.takeaway_wx_mp.entry.LeaveMsg;
import com.cins.hobo.takeaway_wx_mp.vo.LeaveMsgListVO;

import java.util.List;

public interface LeaveMsgDao {
    int deleteByPrimaryKey(Integer id);


    int insertSelective(LeaveMsg record);

    LeaveMsg selectByPrimaryKey(Integer id);

    List<LeaveMsgListVO> selectAllLeaveMsg();

    List<LeaveMsg> selectAllLeaveMsgByPid(Integer pid);

    List<LeaveMsgListVO> selectAllLeaveMsgByOpenId(String openId);
}