package com.cins.hobo.takeaway_wx_mp.dao;

import com.cins.hobo.takeaway_wx_mp.entry.MaterialsType;
import com.cins.hobo.takeaway_wx_mp.vo.MetarialsDetailListVO;

import java.util.List;

public interface MaterialsTypeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(MaterialsType record);

    int insertSelective(MaterialsType record);

    MaterialsType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MaterialsType record);

    int updateByPrimaryKey(MaterialsType record);

    MaterialsType selectTypeByTypeName(String metarialsTypeName);

    List<MaterialsType> getMetarialsTypeList();

    MetarialsDetailListVO getDetailListByTypeId(Integer typeId);
}