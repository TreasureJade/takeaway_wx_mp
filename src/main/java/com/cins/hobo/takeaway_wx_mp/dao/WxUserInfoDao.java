package com.cins.hobo.takeaway_wx_mp.dao;

import com.cins.hobo.takeaway_wx_mp.entry.WxUserInfo;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;

import java.util.List;

public interface WxUserInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(WxUserInfo record);

    int insertSelective(WxUserInfo record);

    WxUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WxUserInfo record);

    int updateByPrimaryKey(WxUserInfo record);

    List<WxUserInfo> getWxUserList();

    WxUserInfo selectByNickname(String nickname);
}