package com.cins.hobo.takeaway_wx_mp.dao;

import com.cins.hobo.takeaway_wx_mp.entry.MaterialsDetail;

import java.util.List;

public interface MaterialsDetailDao {
    int deleteByPrimaryKey(Integer id);

    int insert(MaterialsDetail record);

    int insertSelective(MaterialsDetail record);

    MaterialsDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MaterialsDetail record);

    int updateByPrimaryKey(MaterialsDetail record);

    MaterialsDetail selectByMetarialsName(String metarialsName);

    List<MaterialsDetail> getDetailListByTypeId(Integer typeId);
}