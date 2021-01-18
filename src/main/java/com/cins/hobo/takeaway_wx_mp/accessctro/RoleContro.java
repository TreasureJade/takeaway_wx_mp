package com.cins.hobo.takeaway_wx_mp.accessctro;

import com.cins.hobo.takeaway_wx_mp.enums.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : hobo
 * @interfaceName : RoleControl
 * @date: 2021/1/12
 * @description: TODO
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleContro {
    RoleEnum role();
}
