package com.cins.hobo.takeaway_wx_mp.enums;

import lombok.Getter;

/**
 * @author : hobo
 * @enumName : ResultEnum
 * @date: 2021/1/12
 * @description: TODO
 */
@Getter
public enum ResultEnum {

    AUTHENTICATION_ERROR(401, "用户认证失败,请重新登录"),
    PERMISSION_DENNY(403, "权限不足"),
    NOT_FOUND(404, "url错误,请求路径未找到"),
    SERVER_ERROR(500, "服务器未知错误:%s"),
    BIND_ERROR(511, "参数校验错误:%s"),
    REQUEST_METHOD_ERROR(550, "不支持%s的请求方式"),

    USER_NOT_EXIST(1, "用户不存在"),
    PASSWORD_ERROR(2, "密码错误"),
    USER_ALREADY_EXIST(3,"用户已存在" ),

    DISH_TYPE_ALREADY_EXIST(4,"该菜品种类已存在" ),
    DISH_TYPE_NOT_EXIST(4, "该菜品种类不存在"),

    DISH_NOT_EXIST(5,"该菜品不存在" ),
    DISH_ALREADY_EXIST(5,"该菜品已存在" ),


    METARIALS_TYPE_ALREADY_EXIST(6,"该原材料种类已存在" ),
    METARIALS_TYPE_NOT_EXIST(6,"该原材料种类不存在" ),
    METARIALS_ALREADY_EXIST(7,"该原材料已存在" ),
    METARIALS_NOT_EXIST(7,"该原材料不存在" ),

    CREATE_ORDER_ERROR(20,"创建订单失败" ),


    DIR_NOT_EXIST(50,"服务器文件夹不存在" ),

    WX_MENU_CREAT_FILED(60,"微信菜单创建失败" ),
    WX_AUTHORIZATION_FAILED(61,"微信用户授权失败" ), 
    ORDER_NOT_EXIST(62,"订单不存在" ),
    PAY_ERROR(63,"预支付失败" ),
    PAY_FIlED(63,"支付失败" ),

    PRINT_LENGTH_ERROR(70,"打印长度错误" );
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
