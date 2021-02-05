package com.cins.hobo.takeaway_wx_mp.service;

import com.cins.hobo.takeaway_wx_mp.form.InsertDishForm;
import com.cins.hobo.takeaway_wx_mp.form.UpdateDishDetailForm;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : hobo
 * @interfaceName : DishesService
 * @date: 2021/1/21
 * @description: TODO
 */
public interface DishesService {

    /**
     * 新增菜品种类
     * @param dishTypeName
     * @return
     */
    ResultVO insertDishesType(String dishTypeName);

    /**
     * 删除菜品种类
     * @param id
     * @return
     */
    ResultVO deleteDishesType(Integer id);

    /**
     * 删除菜品种类
     * @param id
     * @param dishTypeName
     * @return
     */
    ResultVO updateDishesTypeName(Integer id,String dishTypeName);

    /**
     * 查询所有菜品种类
     * @return
     */
    ResultVO getDishesTypeList();

    /**
     * 添加新的菜品
     * @param insertDishVO
     * @return
     */
    ResultVO insertDish(InsertDishForm insertDishVO, MultipartFile file);

    /**
     * 更新菜品
     * @param updateDishDetailForm
     * @return
     */
    ResultVO updateDish(UpdateDishDetailForm updateDishDetailForm,@Param("file") MultipartFile file);

    /**
     * 删除菜品
     * @param id
     * @return
     */
    ResultVO deleteDish(Integer id);

    /**
     * 获取某个菜品种类下的所有菜品
     * @param typeId
     * @return
     */
    ResultVO getDishesByType(Integer typeId);

    /**
     * 获取菜品详情
     * @param id
     * @return
     */
    ResultVO getDishDetailById(Integer id);

    /**
     * 获取菜单
     * @return
     */
    ResultVO getAll();
}
