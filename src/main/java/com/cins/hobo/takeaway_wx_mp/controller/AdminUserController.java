package com.cins.hobo.takeaway_wx_mp.controller;

import com.cins.hobo.takeaway_wx_mp.accessctro.RoleContro;
import com.cins.hobo.takeaway_wx_mp.enums.RoleEnum;
import com.cins.hobo.takeaway_wx_mp.form.*;
import com.cins.hobo.takeaway_wx_mp.service.AdminUserService;
import com.cins.hobo.takeaway_wx_mp.service.LeaveMsgService;
import com.cins.hobo.takeaway_wx_mp.service.SupplierUserService;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import io.swagger.annotations.*;
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

    @Autowired
    private SupplierUserService supplierUserService;

    @Autowired
    private LeaveMsgService leaveMsgService;

    @ApiOperation("用户登陆")
    @PostMapping(name = "用户登陆", value = "/login")
    public ResultVO login(LoginForm loginForm, HttpServletResponse response) {
        return adminUserService.login(loginForm, response);
    }

    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiOperation("添加用户")
    @PostMapping(name = "添加用户", value = "/insert")
    public ResultVO insert(AddUserForm form) {
        return adminUserService.addUser(form);
    }

    @RoleContro(role = RoleEnum.USER)
    @ApiOperation("修改密码")
    @PostMapping(name = "修改密码", value = "/update")
    public ResultVO updatePw(UpdatePwForm updatePwForm) {
        return adminUserService.updatePw(updatePwForm);
    }


    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiOperation("修改店员密码为默认密码 123456")
    @PostMapping(name = "修改店员密码为默认密码", value = "update_default")
    public ResultVO updateAdminPwToDefaultPw(@ApiParam(value = "用户id") Integer id) {
        return adminUserService.updatePwToDefaultPw(id);
    }


    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiOperation("获取用户列表")
    @GetMapping(name = "获取用户列表", value = "/list")
    public ResultVO getUserList() {
        return adminUserService.getUserList();
    }

    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiOperation("删除用户")
    @GetMapping(name = "删除用户", value = "/delete")
    public ResultVO deleteUser(@ApiParam(value = "用户id") Integer id) {
        return adminUserService.deleteUser(id);
    }

    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiOperation("添加供货商")
    @PostMapping(name = "添加供货商", value = "/insert_supplier")
    public ResultVO insertSupplierUser(AddSupplierUserForm addSupplierUserForm) {
        return adminUserService.insertSupplierUser(addSupplierUserForm);
    }

    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiOperation("修改用户权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true),
            @ApiImplicitParam(name = "role", value = "用户权限，2为店员，3为系统管理员")}
    )
    @PostMapping(name = "修改用户权限", value = "/update_role")
    public ResultVO updateUserRole(Integer id, Integer role) {
        return adminUserService.updateAdminUserRole(id, role);
    }

    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiOperation("获取供货商用户列表")
    @GetMapping(name = "获取供货商用户列表", value = "/get_supplier_list")
    public ResultVO getSupplierUserList() {
        return adminUserService.getSupplierUserList();
    }

    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiOperation("修改供货商信息")
    @PutMapping(value = "/sup_user_info")
    public ResultVO updateSupUserInfo(UpdateSupplierInfoForm form) {
        return supplierUserService.updateInfo(form);
    }

    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiOperation("删除供货商用户")
    @GetMapping(name = "删除供货商用户", value = "/delete_supplier")
    public ResultVO deleteSupplierUser(@ApiParam(value = "供货商用户id") Integer id) {
        return adminUserService.deleteSupplierUser(id);
    }

    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiOperation("获取微信用户列表")
    @GetMapping(value = "/wx_user_list")
    public ResultVO getWxUserList(){
        return adminUserService.getWxUserList();
    }

    @RoleContro(role = RoleEnum.SUPPER_ADMIN)
    @ApiOperation("根据微信昵称查找用户")
    @GetMapping("/wx_user")
    @ApiImplicitParams({@ApiImplicitParam(name = "nickname",value = "微信用户昵称",required = true,dataTypeClass = String.class)})
    public ResultVO getWxUserByNickname(String nickname){
        return adminUserService.getWxUserByNickname(nickname);
    }

    @RoleContro(role = RoleEnum.ADMIN)
    @GetMapping("/leave_msg")
    @ApiOperation("查看所有留言")
    public ResultVO getAllLeaveMsg(){
        return leaveMsgService.adminSelectAllLeaveMsg();
    }
}
