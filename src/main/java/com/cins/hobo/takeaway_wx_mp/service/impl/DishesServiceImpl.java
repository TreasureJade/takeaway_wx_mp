package com.cins.hobo.takeaway_wx_mp.service.impl;

import com.cins.hobo.takeaway_wx_mp.dao.DishesDetailDao;
import com.cins.hobo.takeaway_wx_mp.dao.DishesTypeDao;
import com.cins.hobo.takeaway_wx_mp.entry.DishesDetail;
import com.cins.hobo.takeaway_wx_mp.entry.DishesType;
import com.cins.hobo.takeaway_wx_mp.enums.ResultEnum;
import com.cins.hobo.takeaway_wx_mp.form.InsertDishForm;
import com.cins.hobo.takeaway_wx_mp.form.UpdateDishDetailForm;
import com.cins.hobo.takeaway_wx_mp.service.DishesService;
import com.cins.hobo.takeaway_wx_mp.util.RedisUtils;
import com.cins.hobo.takeaway_wx_mp.util.UploadFileUtil;
import com.cins.hobo.takeaway_wx_mp.vo.DishesDetailListVO;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author : hobo
 * @className : DishesServiceImpl
 * @date: 2021/1/21
 * @description: TODO
 */
@Service
@Slf4j
public class DishesServiceImpl implements DishesService {

    @Autowired
    private DishesDetailDao dishesDetailDao;

    @Autowired
    private DishesTypeDao dishesTypeDao;

    @Autowired
    private RedisUtils redisUtils;

    @Value("${file.dish_pic}")
    private String path;

    private final String DISH_LIST_KEY = "dish_list_by_type_id:";

    @Override
    public ResultVO insertDishesType(String dishTypeName) {
        if (dishesTypeDao.selectByTypeName(dishTypeName) != null) {
            return ResultVO.error(ResultEnum.DISH_TYPE_ALREADY_EXIST);
        }
        DishesType dishesType = new DishesType();
        dishesType.setTypeName(dishTypeName);
        if (dishesTypeDao.insert(dishesType) == 1) {
            removeRedisKey(dishesType.getId());
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO deleteDishesType(Integer id) {
        List<DishesDetail> details = getDishesByTypeId(id);
        if (!details.isEmpty()) {
            DishesType defaultType = dishesTypeDao.selectByTypeName("default");
            for (DishesDetail detail : details
            ) {
                // 将要删除的菜品种类下的所有菜品放入默认分组
                detail.setDishTypeId(defaultType.getId());
            }
        }
        if (dishesTypeDao.deleteByPrimaryKey(id) == 1) {
            removeRedisKey(id);
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO updateDishesTypeName(Integer id, String dishTypeName) {
        DishesType dishesType = dishesTypeDao.selectByPrimaryKey(id);
        if (dishesType == null) {
            return ResultVO.error(ResultEnum.DISH_TYPE_NOT_EXIST);
        }
        dishesType.setTypeName(dishTypeName);
        if (dishesTypeDao.updateByPrimaryKeySelective(dishesType) == 1) {
            removeRedisKey(id);
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO getDishesTypeList() {
        List<DishesType> dishesTypeList = dishesTypeDao.getDishesTypeList();
        return ResultVO.success(dishesTypeList);
    }

    @Override
    @Transactional
    public ResultVO insertDish(InsertDishForm insertDishForm, MultipartFile file) {
        if (dishesDetailDao.selectByDishName(insertDishForm.getDishName()) != null) {
            return ResultVO.error(ResultEnum.DISH_ALREADY_EXIST);
        }
        DishesDetail detail = new DishesDetail();
        BeanUtils.copyProperties(insertDishForm, detail);
        if (detail.getPrePrice()==null){
            detail.setPrePrice(BigDecimal.valueOf(-1));
        }
        if (file != null) {
            String tempUrl = path + detail.getDishName() + UploadFileUtil.getFileType(file);
            String url = UploadFileUtil.uploadPic(tempUrl, file);
            log.info(url);
            detail.setDishPicUrl(url);
        }
        if (dishesDetailDao.insert(detail) == 1) {
            removeRedisKey(detail.getDishTypeId());
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    @Transactional
    public ResultVO updateDish(UpdateDishDetailForm updateDishDetailForm, MultipartFile file) {
        if (dishesDetailDao.selectByPrimaryKey(updateDishDetailForm.getId()) == null) {
            return ResultVO.error(ResultEnum.DISH_NOT_EXIST);
        }
        DishesDetail detail = dishesDetailDao.selectByPrimaryKey(updateDishDetailForm.getId());
        BeanUtils.copyProperties(updateDishDetailForm, detail);
        if (file != null) {
            File oldFile = new File(detail.getDishPicUrl());
            oldFile.deleteOnExit();
            String tempUrl = path + detail.getDishName() + UploadFileUtil.getFileType(file);
            String url = UploadFileUtil.uploadPic(tempUrl, file);
            if (!"".equals(url)) {
                detail.setDishPicUrl(url);
            }
        }
        if (dishesDetailDao.updateByPrimaryKeySelective(detail) == 1) {
            removeRedisKey(detail.getDishTypeId());
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO deleteDish(Integer id) {
        DishesDetail detail = dishesDetailDao.selectByPrimaryKey(id);
        if (detail == null) {
            return ResultVO.error(ResultEnum.DISH_NOT_EXIST);
        }
        File file = new File(detail.getDishPicUrl());
        file.deleteOnExit();
        removeRedisKey(detail.getDishTypeId());
        if (dishesDetailDao.deleteByPrimaryKey(id) == 1) {
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO getDishesByType(Integer typeId) {
        DishesType type = dishesTypeDao.selectByPrimaryKey(typeId);
        if (type == null) {
            return ResultVO.error(ResultEnum.DISH_TYPE_NOT_EXIST);
        }
        if (redisUtils.get(DISH_LIST_KEY + typeId) != null) {
            DishesDetailListVO vo = (DishesDetailListVO) redisUtils.get(DISH_LIST_KEY + typeId);
            return ResultVO.success(vo);
        }
        DishesDetailListVO vo = new DishesDetailListVO();
        vo.setTypeId(typeId);
        vo.setTypeName(type.getTypeName());
        List<DishesDetail> detailList = getDishesByTypeId(typeId);
        vo.setDishesDetailList(detailList);
        redisUtils.set(DISH_LIST_KEY + typeId, vo, 300);
        return ResultVO.success(vo);
    }

    @Override
    public ResultVO getDishDetailById(Integer id) {
        DishesDetail detail = dishesDetailDao.selectByPrimaryKey(id);
        if (detail == null) {
            return ResultVO.error(ResultEnum.DISH_NOT_EXIST);
        }
        return ResultVO.success(detail);
    }

    @Override
    public ResultVO getAll() {
        List<DishesDetailListVO> result = dishesTypeDao.getDishesTypeList2();
        for (DishesDetailListVO vo : result) {
            List<DishesDetail> details = dishesDetailDao.getDishesByTypeId(vo.getTypeId());
            vo.setDishesDetailList(details);
        }
        return ResultVO.success(result);
    }

    private List<DishesDetail> getDishesByTypeId(Integer typeId) {
        return dishesDetailDao.getDishesByTypeId(typeId);
    }


    private void removeRedisKey(Integer typeId) {
        if (redisUtils.hasKey(DISH_LIST_KEY + typeId)) {
            redisUtils.del(DISH_LIST_KEY + typeId);
        }
    }
}
