package com.cins.hobo.takeaway_wx_mp.dao;

import com.cins.hobo.takeaway_wx_mp.entry.SupplierUser;

public interface SupplierUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SupplierUser record);

    int insertSelective(SupplierUser record);

    SupplierUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SupplierUser record);

    int updateByPrimaryKey(SupplierUser record);

    SupplierUser getSupplierUserByUsername(String username);

}