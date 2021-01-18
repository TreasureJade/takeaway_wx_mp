package com.cins.hobo.takeaway_wx_mp.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : hobo
 * @className : UpdatePwForm
 * @date: 2021/1/18
 * @description: TODO
 */
@Data
public class UpdatePwForm {

    @ApiModelProperty("旧密码")
    private String oldPw;
    @ApiModelProperty("新密码")
    private String newPw;

}
