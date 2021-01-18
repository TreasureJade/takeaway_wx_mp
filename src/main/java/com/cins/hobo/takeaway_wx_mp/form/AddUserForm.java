package com.cins.hobo.takeaway_wx_mp.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : hobo
 * @className : AddUserForm
 * @date: 2021/1/14
 * @description: TODO
 */
@Data
public class AddUserForm {
    @ApiModelProperty("用户名")
    private String userName;
}
