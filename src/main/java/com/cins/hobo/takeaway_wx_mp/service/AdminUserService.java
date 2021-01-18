package com.cins.hobo.takeaway_wx_mp.service;

import com.cins.hobo.takeaway_wx_mp.entry.AdminUser;
import com.cins.hobo.takeaway_wx_mp.form.AddUserForm;
import com.cins.hobo.takeaway_wx_mp.form.LoginForm;
import com.cins.hobo.takeaway_wx_mp.form.UpdatePwForm;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : hobo
 * @interfaceName : AdminUserService
 * @date: 2021/1/12
 * @description: TODO
 */
public interface AdminUserService {

    AdminUser getCurrentUser();

    AdminUser getUserByUsername(String username);

    ResultVO login(LoginForm form, HttpServletResponse response);

    ResultVO addUser(AddUserForm form);

    ResultVO updatePw(UpdatePwForm updatePwForm);

    ResultVO getUserList();

    ResultVO deleteUser(Integer id);
}
