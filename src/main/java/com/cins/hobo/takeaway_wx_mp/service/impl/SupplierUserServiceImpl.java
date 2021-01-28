package com.cins.hobo.takeaway_wx_mp.service.impl;

import com.cins.hobo.takeaway_wx_mp.dao.SupplierUserDao;
import com.cins.hobo.takeaway_wx_mp.entry.SupplierUser;
import com.cins.hobo.takeaway_wx_mp.enums.ResultEnum;
import com.cins.hobo.takeaway_wx_mp.form.UpdateSupplierInfoForm;
import com.cins.hobo.takeaway_wx_mp.service.SupplierUserService;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author : hobo
 * @className : SupplierUserServiceImpl
 * @date: 2021/1/20
 * @description: TODO
 */
@Service
public class SupplierUserServiceImpl implements SupplierUserService {

    @Autowired
    private SupplierUserDao supplierUserDao;


    @Override
    public SupplierUser getSupUserByUsername(String phoneNum) {
        return supplierUserDao.selectByPhoneNum(phoneNum);
    }

    @Override
    public SupplierUser getSupUserByOpenId(String openId) {
        return supplierUserDao.selectByOpenId(openId);
    }

    @Override
    public ResultVO updateInfo(UpdateSupplierInfoForm form) {
        SupplierUser user = getSupUserByUsername(form.getPhoneNum());
        if (user != null) {
            BeanUtils.copyProperties(form, user);
            if (supplierUserDao.updateByPrimaryKeySelective(user) == 1) {
                return ResultVO.success();
            }
            return ResultVO.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVO.error(ResultEnum.AUTHENTICATION_ERROR);
    }

}
