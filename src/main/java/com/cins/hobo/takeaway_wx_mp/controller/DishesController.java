package com.cins.hobo.takeaway_wx_mp.controller;

import com.cins.hobo.takeaway_wx_mp.accessctro.RoleContro;
import com.cins.hobo.takeaway_wx_mp.enums.RoleEnum;
import com.cins.hobo.takeaway_wx_mp.form.InsertDishForm;
import com.cins.hobo.takeaway_wx_mp.form.UpdateDishDetailForm;
import com.cins.hobo.takeaway_wx_mp.service.DishesService;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : hobo
 * @className : DishesController
 * @date: 2021/1/21
 * @description: 菜品管理相关接口
 */
@CrossOrigin
@Api(tags = "菜品管理相关接口")
@RestController
@RequestMapping("/dishes")
public class DishesController {

    @Autowired
    private DishesService dishesService;

    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiOperation("添加新的菜品种类")
    @PostMapping(value = "/dish_type")
    public ResultVO insertDishesType(@ApiParam(value = "菜品种类名称") String dishesTypeName){
        return dishesService.insertDishesType(dishesTypeName);
    }

    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiOperation("更改菜品种类名称")
    @PutMapping(value = "/dish_type")
    public ResultVO updateDishesType(@ApiParam(value = "菜品种类id") Integer id,@ApiParam(value = "菜品种类名称") String dishTypeName){
        return dishesService.updateDishesTypeName(id,dishTypeName);
    }

    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiOperation("删除菜品种类")
    @DeleteMapping(value = "/dish_type")
    public ResultVO deleteDishType(@ApiParam(value = "菜品种类id") Integer id){
        return dishesService.deleteDishesType(id);
    }

    @ApiOperation("获取所有的菜品种类")
    @GetMapping(value = "/dish_type")
    public ResultVO getDishesTypeList(){
        return dishesService.getDishesTypeList();
    }

    @ApiOperation("获取某个菜品种类下的所有菜品")
    @GetMapping(value = "/dish_type/getDishesList")
    public ResultVO getDishesByDishType(@ApiParam(value = "菜品种类id") Integer typeId){
        return dishesService.getDishesByType(typeId);
    }


    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiOperation("添加新的菜品")
    @PostMapping("/dish_detail")
    public ResultVO insertDish(InsertDishForm form, @RequestPart("file") MultipartFile file){
       return dishesService.insertDish(form,file);
    }

    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiOperation("更新新的菜品")
    @PutMapping(value = "/dish_detail")
    public ResultVO updateDish(UpdateDishDetailForm dishDetailForm,@RequestPart("file") MultipartFile file){
        return dishesService.updateDish(dishDetailForm,file);
    }

    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiOperation("删除菜品")
    @DeleteMapping(value = "/dish_detail")
    public ResultVO deleteDish(@ApiParam(value = "菜品id") Integer id){
        return dishesService.deleteDish(id);
    }

    @ApiOperation("获取菜品详情")
    @GetMapping(value = "/dish_detail")
    public ResultVO getDishDetail(@ApiParam(value = "菜品id") Integer id){
        return dishesService.getDishDetailById(id);
    }

}
