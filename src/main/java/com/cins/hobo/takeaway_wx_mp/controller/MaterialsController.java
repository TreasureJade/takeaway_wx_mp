package com.cins.hobo.takeaway_wx_mp.controller;

import com.cins.hobo.takeaway_wx_mp.accessctro.RoleContro;
import com.cins.hobo.takeaway_wx_mp.enums.RoleEnum;
import com.cins.hobo.takeaway_wx_mp.form.MaterialsForm;
import com.cins.hobo.takeaway_wx_mp.form.MaterialsTypeForm;
import com.cins.hobo.takeaway_wx_mp.form.UpdateMaterialsForm;
import com.cins.hobo.takeaway_wx_mp.form.UpdateMaterialsTypeForm;
import com.cins.hobo.takeaway_wx_mp.service.MaterialsService;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : hobo
 * @className : MaterialsController
 * @date: 2021/1/23
 * @description: TODO
 */
@RestController
@CrossOrigin
@Api(tags = "原材料管理接口")
@RequestMapping("/materials")
public class MaterialsController {


    @Autowired
    private MaterialsService materialsService;

    @ApiOperation("添加原材料种类")
    @PostMapping("/type")
    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    public ResultVO insertMaterialsType(MaterialsTypeForm form){
        return materialsService.insertMetarialsType(form);
    }

    @ApiOperation("修改原材料种类")
    @PutMapping("/type")
    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    public ResultVO updateMaterialsType(UpdateMaterialsTypeForm form){
        return materialsService.updateMetarialsType(form);
    }

    @ApiOperation("删除原材料种类,该操作将会删除该种类下的所有原材料！")
    @DeleteMapping("/type")
    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "原材料种类id",dataType = "INTEGER", required = true,dataTypeClass = Integer.class)})
    public ResultVO deleteMaterialsType(Integer id){
        return materialsService.deleteMetarialsType(id);
    }

    @ApiOperation("获取原材料种类列表")
    @GetMapping("/type")
    @RoleContro(role = RoleEnum.ADMIN)
    public ResultVO getMaterialsTypeList(){
        return materialsService.getMetarialsTypeList();
    }

    @ApiOperation("添加新的原材料")
    @PostMapping("/detail")
    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    public ResultVO insertMaterialsDetail(MaterialsForm form){
        return materialsService.insertMetarialsDetail(form);
    }

    @ApiOperation("更新原材料")
    @PutMapping("/detail")
    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    public ResultVO updateMaterialDetail(UpdateMaterialsForm form){
        return materialsService.updateMetarialsDetail(form);
    }

    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiOperation("删除原材料")
    @DeleteMapping("/detail")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "原材料id",dataType = "INTEGER", required = true,dataTypeClass = Integer.class)})
    public ResultVO deleteMaterialDetail(Integer id){
        return materialsService.deleteMetarialsDetail(id);
    }

    @ApiOperation("获取某个种类下的所有原材料")
    @GetMapping("/detail_list")
    @RoleContro(role = RoleEnum.ADMIN)
    @ApiImplicitParams({@ApiImplicitParam(name = "typeId", value = "菜品种类id",dataType = "INTEGER", required = true,dataTypeClass = Integer.class)})
    public ResultVO getMaterialListByTypeId(Integer typeId){
        return materialsService.getMetarialsDetailByTypeId(typeId);
    }

}
