package com.cins.hobo.takeaway_wx_mp.service.impl;

import com.cins.hobo.takeaway_wx_mp.dao.MaterialsDetailDao;
import com.cins.hobo.takeaway_wx_mp.dao.MaterialsTypeDao;
import com.cins.hobo.takeaway_wx_mp.entry.MaterialsDetail;
import com.cins.hobo.takeaway_wx_mp.entry.MaterialsType;
import com.cins.hobo.takeaway_wx_mp.enums.ResultEnum;
import com.cins.hobo.takeaway_wx_mp.form.MaterialsForm;
import com.cins.hobo.takeaway_wx_mp.form.MaterialsTypeForm;
import com.cins.hobo.takeaway_wx_mp.form.UpdateMaterialsForm;
import com.cins.hobo.takeaway_wx_mp.form.UpdateMaterialsTypeForm;
import com.cins.hobo.takeaway_wx_mp.service.MaterialsService;
import com.cins.hobo.takeaway_wx_mp.vo.MetarialsDetailListVO;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : hobo
 * @className : MetarialsServiceImpl
 * @date: 2021/1/23
 * @description: 原材料相关service
 */
@Service
public class MaterialsServiceImpl implements MaterialsService {

    @Autowired
    private MaterialsTypeDao materialsTypeDao;

    @Autowired
    private MaterialsDetailDao materialsDetailDao;

    @Override
    public ResultVO insertMetarialsType(MaterialsTypeForm materialsTypeForm) {
        if (materialsTypeDao.selectTypeByTypeName(materialsTypeForm.getMetarialsTypeName())!=null){
            return ResultVO.error(ResultEnum.METARIALS_TYPE_ALREADY_EXIST);
        }
        MaterialsType materialsType = new MaterialsType();
        BeanUtils.copyProperties(materialsTypeForm, materialsType);
        if (materialsTypeDao.insert(materialsType)==1){
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO updateMetarialsType(UpdateMaterialsTypeForm updateMaterialsTypeForm) {
        if (materialsTypeDao.selectByPrimaryKey(updateMaterialsTypeForm.getId())==null){
            return ResultVO.error(ResultEnum.METARIALS_TYPE_NOT_EXIST);
        }
        MaterialsType materialsType = materialsTypeDao.selectByPrimaryKey(updateMaterialsTypeForm.getId());
        BeanUtils.copyProperties(updateMaterialsTypeForm, materialsType);
        if (materialsTypeDao.updateByPrimaryKeySelective(materialsType)==1){
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO deleteMetarialsType(Integer id) {
        if (materialsTypeDao.selectByPrimaryKey(id)==null){
            return ResultVO.error(ResultEnum.METARIALS_TYPE_NOT_EXIST);
        }
        List<MaterialsDetail> details = materialsDetailDao.getDetailListByTypeId(id);
        if (!details.isEmpty()){
            for (MaterialsDetail detail:details
                 ) {
                materialsDetailDao.deleteByPrimaryKey(detail.getId());
            }
        }
        if (materialsTypeDao.deleteByPrimaryKey(id)==1){
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO getMetarialsTypeList() {
        List<MaterialsType> materialsTypes = materialsTypeDao.getMetarialsTypeList();
        return ResultVO.success(materialsTypes);
    }

    @Override
    public ResultVO insertMetarialsDetail(MaterialsForm materialsForm) {
        if (materialsDetailDao.selectByMetarialsName(materialsForm.getMetarialsName())!=null){
            return ResultVO.error(ResultEnum.METARIALS_ALREADY_EXIST);
        }
        MaterialsDetail detail = new MaterialsDetail();
        BeanUtils.copyProperties(materialsForm,detail);
        if (materialsDetailDao.insert(detail)==1){
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO updateMetarialsDetail(UpdateMaterialsForm updateMaterialsForm) {
        if (materialsDetailDao.selectByPrimaryKey(updateMaterialsForm.getId())==null){
            return ResultVO.error(ResultEnum.METARIALS_NOT_EXIST);
        }
        MaterialsDetail detail = materialsDetailDao.selectByPrimaryKey(updateMaterialsForm.getId());
        BeanUtils.copyProperties(updateMaterialsForm,detail);
        if (materialsDetailDao.updateByPrimaryKeySelective(detail)==1){
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO deleteMetarialsDetail(Integer id) {
        if (materialsDetailDao.selectByPrimaryKey(id)==null){
            return ResultVO.error(ResultEnum.METARIALS_NOT_EXIST);
        }
        if (materialsDetailDao.deleteByPrimaryKey(id)==1){
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO getMetarialsDetailByTypeId(Integer typeId) {
        MetarialsDetailListVO vo = materialsTypeDao.getDetailListByTypeId(typeId);
        if (vo == null){
            return ResultVO.error(ResultEnum.METARIALS_TYPE_NOT_EXIST);
        }
        List<MaterialsDetail> details = materialsDetailDao.getDetailListByTypeId(typeId);
        vo.setDetailList(details);
        return ResultVO.success(vo);
    }
}
