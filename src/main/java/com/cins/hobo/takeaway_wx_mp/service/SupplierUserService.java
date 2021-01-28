package com.cins.hobo.takeaway_wx_mp.service;

import com.cins.hobo.takeaway_wx_mp.entry.SupplierUser;
import com.cins.hobo.takeaway_wx_mp.form.LoginForm;
import com.cins.hobo.takeaway_wx_mp.form.UpdatePwForm;
import com.cins.hobo.takeaway_wx_mp.form.UpdateSupplierInfoForm;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : hobo
 * @interfaceName : SupplierUserService
 * @date: 2021/1/20
 * @description: 供货商用户接口
 */
public interface SupplierUserService {


    SupplierUser getSupUserByUsername(String phoneNum);

    SupplierUser getSupUserByOpenId(String openId);

    ResultVO updateInfo(UpdateSupplierInfoForm form);

}
