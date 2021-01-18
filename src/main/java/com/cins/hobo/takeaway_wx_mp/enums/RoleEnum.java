package com.cins.hobo.takeaway_wx_mp.enums;

import lombok.Getter;

import java.util.HashMap;

/**
 * @author : hobo
 * @enumName : RoleEnum
 * @date: 2021/1/12
 * @description: TODO
 */
@Getter
public enum RoleEnum {
    USER(1, "用户"),
    ADMIN(2, "管理员"),
    SUPPER_ADMIN(3,"系统管理员")
    ;


    private Integer value;
    private String role;

    RoleEnum(Integer value, String role) {
        this.value = value;
        this.role = role;
    }

    public static String getRole(Integer integer) {
        HashMap<Integer, String> hashMap = new HashMap<>();
        hashMap.put(ADMIN.getValue(), ADMIN.getRole());
        hashMap.put(USER.getValue(), USER.getRole());
        hashMap.put(SUPPER_ADMIN.getValue(), SUPPER_ADMIN.getRole());
        return hashMap.get(integer);
    }

    public static Integer getValue(String role) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put(ADMIN.getRole(), ADMIN.getValue());
        hashMap.put(USER.getRole(), USER.getValue());
        hashMap.put(SUPPER_ADMIN.getRole(), SUPPER_ADMIN.getValue());
        return hashMap.get(role);
    }
}
