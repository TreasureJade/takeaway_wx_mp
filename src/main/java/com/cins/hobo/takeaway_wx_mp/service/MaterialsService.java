package com.cins.hobo.takeaway_wx_mp.service;

import com.cins.hobo.takeaway_wx_mp.form.MaterialsForm;
import com.cins.hobo.takeaway_wx_mp.form.MaterialsTypeForm;
import com.cins.hobo.takeaway_wx_mp.form.UpdateMaterialsForm;
import com.cins.hobo.takeaway_wx_mp.form.UpdateMaterialsTypeForm;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;

/**
 * @author : hobo
 * @interfaceName : MetarialsService
 * @date: 2021/1/23
 * @description: TODO
 */
public interface MaterialsService {

    /**
     * 添加新的原材料种类
     * @param materialsTypeForm
     * @return
     */
    ResultVO insertMetarialsType(MaterialsTypeForm materialsTypeForm);

    /**
     * 更新原材料种类
     * @param updateMaterialsTypeForm
     * @return
     */
    ResultVO updateMetarialsType(UpdateMaterialsTypeForm updateMaterialsTypeForm);

    /**
     * 删除原材料种类
     * @param id
     * @return
     */
    ResultVO deleteMetarialsType(Integer id);

    /**
     * 获取原材料种类列表
     * @return
     */
    ResultVO getMetarialsTypeList();

    /**
     * 添加原材料
     * @param materialsForm
     * @return
     */
    ResultVO insertMetarialsDetail(MaterialsForm materialsForm);

    /**
     * 更新原材料
     * @param updateMaterialsForm
     * @return
     */
    ResultVO updateMetarialsDetail(UpdateMaterialsForm updateMaterialsForm);

    /**
     * 删除原材料
     * @return
     */
    ResultVO deleteMetarialsDetail(Integer id);

    /**
     * 根据原材料种类id获取原材料列表
     * @param typeId
     * @return
     */
    ResultVO getMetarialsDetailByTypeId(Integer typeId);
}
