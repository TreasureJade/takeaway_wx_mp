package com.cins.hobo.takeaway_wx_mp.controller;

import com.cins.hobo.takeaway_wx_mp.accessctro.RoleContro;
import com.cins.hobo.takeaway_wx_mp.enums.RoleEnum;
import com.cins.hobo.takeaway_wx_mp.form.LoginForm;
import com.cins.hobo.takeaway_wx_mp.form.UpdatePwForm;
import com.cins.hobo.takeaway_wx_mp.form.UpdateSupplierInfoForm;
import com.cins.hobo.takeaway_wx_mp.service.SupplierUserService;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : hobo
 * @className : SupplierUserController
 * @date: 2021/1/20
 * @description: TODO
 */
@RestController
@RequestMapping("/supplier")
@Api(tags = "供货商用户接口")
@CrossOrigin
@Slf4j
public class SupplierUserController {


    @Autowired
    public SupplierUserService supplierUserService;


    @ApiOperation("用户登陆")
    @PostMapping(name = "登陆",value = "/login")
    public ResultVO login(LoginForm loginForm, HttpServletResponse response){
        return supplierUserService.login(loginForm,response);
    }

    @ApiOperation("用户修改个人信息")
    @RoleContro(role = RoleEnum.USER)
    @PostMapping(name = "供货商修改个人信息",value = "/update_info")
    public ResultVO updateInfo(UpdateSupplierInfoForm form){
        return supplierUserService.updateInfo(form);
    }

    @ApiOperation("供货商修改密码")
    @RoleContro(role=RoleEnum.USER)
    @PostMapping(name = "供货商修改密码",value = "/update_pw")
    public ResultVO updatePw(UpdatePwForm form){
        return supplierUserService.updatePw(form);
    }

}
