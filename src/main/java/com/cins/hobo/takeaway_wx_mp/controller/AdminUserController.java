package com.cins.hobo.takeaway_wx_mp.controller;

import com.cins.hobo.takeaway_wx_mp.accessctro.RoleContro;
import com.cins.hobo.takeaway_wx_mp.enums.RoleEnum;
import com.cins.hobo.takeaway_wx_mp.form.AddUserForm;
import com.cins.hobo.takeaway_wx_mp.form.LoginForm;
import com.cins.hobo.takeaway_wx_mp.form.UpdatePwForm;
import com.cins.hobo.takeaway_wx_mp.service.AdminUserService;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : hobo
 * @className : AdminUserController
 * @date: 2021/1/14
 * @description: TODO
 */
@RestController
@RequestMapping("/admin_user")
@Api(tags = "后台管理员接口")
@CrossOrigin
@Slf4j
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @ApiOperation("用户登陆")
    @PostMapping(name = "用户登陆",value = "/login")
    public ResultVO login(LoginForm loginForm, HttpServletResponse response){
        return adminUserService.login(loginForm,response);
    }

    @RoleContro(role= RoleEnum.SUPPER_ADMIN)
    @ApiOperation("添加用户")
    @PostMapping(name = "添加用户",value = "/insert")
    public ResultVO insert(AddUserForm form){
        return adminUserService.addUser(form);
    }

    @RoleContro(role = RoleEnum.USER)
    @ApiOperation("修改密码")
    @PostMapping(name = "修改密码",value = "/update")
    public ResultVO updatePw(UpdatePwForm updatePwForm){
        return adminUserService.updatePw(updatePwForm);
    }

    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiOperation("获取用户列表")
    @GetMapping(name = "获取用户列表",value = "/list")
    public ResultVO getUserList(){
        return adminUserService.getUserList();
    }

    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiOperation("删除用户")
    @GetMapping(name = "删除用户",value = "/delete")
    public ResultVO deleteUser(@ApiParam(value = "用户id") Integer id){
        return adminUserService.deleteUser(id);
    }

}
