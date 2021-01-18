package com.cins.hobo.takeaway_wx_mp.security;

import com.cins.hobo.takeaway_wx_mp.entry.AdminUser;
import com.cins.hobo.takeaway_wx_mp.enums.RoleEnum;
import com.cins.hobo.takeaway_wx_mp.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @ClassName JwtUserDetailServiceImpl
 * @Author hobo
 * @Date 2021/1/19
 * @Description UserDetailService实现类
 **/
@Service
@Slf4j
public class JwtUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private AdminUserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUser user = userService.getUserByUsername(username);
        if (user == null) {
            log.info("此用户不存在");
            throw new UsernameNotFoundException(String.format("用户名为 %s 的用户不存在", username));
        } else {
            String role = RoleEnum.getRole(user.getRole());
            return new JwtUser(username, user.getPassword(), role);
        }
    }
}
