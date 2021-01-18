package com.cins.hobo.takeaway_wx_mp.dao;

import com.cins.hobo.takeaway_wx_mp.entry.AdminUser;
import com.cins.hobo.takeaway_wx_mp.vo.UserListVO;

import java.util.List;

public interface AdminUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);

    AdminUser getUserByUsername(String username);

    List<UserListVO> getAdminUserList();
}