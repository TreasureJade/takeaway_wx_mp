package com.cins.hobo.takeaway_wx_mp.security;

import com.alibaba.fastjson.JSON;
import com.cins.hobo.takeaway_wx_mp.accessctro.RoleContro;
import com.cins.hobo.takeaway_wx_mp.entry.AdminUser;
import com.cins.hobo.takeaway_wx_mp.entry.SupplierUser;
import com.cins.hobo.takeaway_wx_mp.enums.ResultEnum;
import com.cins.hobo.takeaway_wx_mp.enums.RoleEnum;
import com.cins.hobo.takeaway_wx_mp.service.AdminUserService;
import com.cins.hobo.takeaway_wx_mp.service.SupplierUserService;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName AuthRoleInterceptor
 * @Author hobo
 * @Date 2021/1/19
 * @Description 权限验证
 **/
@Slf4j
@Service
public class AuthRoleInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AdminUserService userService;

    @Autowired
    private SupplierUserService supplierUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String json = JSON.toJSONString(ResultVO.error(ResultEnum.AUTHENTICATION_ERROR));
        AdminUser user = userService.getCurrentUser();
        if (user == null) {
            return true;
        }
        log.info("============执行权限验证============");
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RoleContro roleControl = handlerMethod.getMethodAnnotation(RoleContro.class);
            if (roleControl == null) {
                return true;
            }
            log.warn(user.getUserName());
            Integer userValue ;
            if (supplierUserService.getSupplierUserByUsername(user.getUserName())!=null){
                userValue = RoleEnum.getValue(RoleEnum.USER.getRole());
            }else {
                userValue = user.getRole();
            }
            Integer roleValue = roleControl.role().getValue();
            log.info("RoleValue:{},userRole:{}", roleValue, userValue);
            if (userValue >= roleValue) {
                return true;
            } else {
                json = JSON.toJSONString(ResultVO.error(ResultEnum.PERMISSION_DENNY));
                log.info("============权限不足===============");
            }
        }
        response.getWriter().append(json);
        return false;
    }
}
