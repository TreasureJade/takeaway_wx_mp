package com.cins.hobo.takeaway_wx_mp.dao;

import com.cins.hobo.takeaway_wx_mp.entry.DishesType;
import com.cins.hobo.takeaway_wx_mp.vo.DishesDetailListVO;

import java.util.List;

public interface DishesTypeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(DishesType record);

    int insertSelective(DishesType record);

    DishesType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DishesType record);

    int updateByPrimaryKey(DishesType record);

    DishesType selectByTypeName(String dishTypeName);

    List<DishesType> getDishesTypeList();

    List<DishesDetailListVO> getDishesTypeList2();
}