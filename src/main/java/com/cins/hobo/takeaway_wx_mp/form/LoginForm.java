package com.cins.hobo.takeaway_wx_mp.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : hobo
 * @className : LoginForm
 * @date: 2021/1/12
 * @description: TODO
 */
@Data
public class LoginForm {

    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("密码")
    private String password;


}
