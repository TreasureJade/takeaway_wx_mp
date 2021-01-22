package com.cins.hobo.takeaway_wx_mp.dao;

import com.cins.hobo.takeaway_wx_mp.entry.DishesDetail;

import java.util.List;

public interface DishesDetailDao {
    int deleteByPrimaryKey(Integer id);

    int insert(DishesDetail record);

    int insertSelective(DishesDetail record);

    DishesDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DishesDetail record);

    int updateByPrimaryKey(DishesDetail record);

    DishesDetail selectByDishName(String dishName);

    List<DishesDetail> getDishesByTypeId(Integer typeId);
}