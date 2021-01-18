package com.cins.hobo.takeaway_wx_mp.service.impl;

import com.cins.hobo.takeaway_wx_mp.dao.AdminUserDao;
import com.cins.hobo.takeaway_wx_mp.entry.AdminUser;
import com.cins.hobo.takeaway_wx_mp.enums.ResultEnum;
import com.cins.hobo.takeaway_wx_mp.form.AddUserForm;
import com.cins.hobo.takeaway_wx_mp.form.LoginForm;
import com.cins.hobo.takeaway_wx_mp.form.UpdatePwForm;
import com.cins.hobo.takeaway_wx_mp.security.JwtProperties;
import com.cins.hobo.takeaway_wx_mp.security.JwtTokenUtil;
import com.cins.hobo.takeaway_wx_mp.security.JwtUserDetailServiceImpl;
import com.cins.hobo.takeaway_wx_mp.service.AdminUserService;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import com.cins.hobo.takeaway_wx_mp.vo.UserListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * @author : hobo
 * @className : AdminUserServiceImpl
 * @date: 2021/1/12
 * @description: TODO
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserDao adminUserDao;

    @Autowired
    private JwtUserDetailServiceImpl jwtUserDetailService;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;


    @Override
    public AdminUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String key = "anonymousUser";
        if (authentication != null && !username.equals(key)) {
            return getUserByUsername(username);
        }
        return null;
    }

    @Override
    public AdminUser getUserByUsername(String username) {
        return adminUserDao.getUserByUsername(username);
    }

    @Override
    public ResultVO login(LoginForm form, HttpServletResponse response) {
        AdminUser user = getUserByUsername(form.getUserName());
        if (user == null) {
            return ResultVO.error(ResultEnum.USER_NOT_EXIST);
        }
        UserDetails userDetails = jwtUserDetailService.loadUserByUsername(form.getUserName());

        if (!new BCryptPasswordEncoder().matches(form.getPassword(), userDetails.getPassword())) {
            return ResultVO.error(ResultEnum.PASSWORD_ERROR);
        }

        Authentication token = new UsernamePasswordAuthenticationToken(form.getUserName(),
                form.getPassword(), userDetails.getAuthorities());
        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String realToken = jwtTokenUtil.generateToken(userDetails);

        response.addHeader(jwtProperties.getTokenName(), realToken);

        HashMap<String, Object> map = new HashMap<>();
        map.put("username", user.getUserName());
        map.put("role", user.getRole());
        map.put("token", realToken);
        return ResultVO.success(map);
    }

    @Override
    public ResultVO addUser(AddUserForm form) {
        if (getUserByUsername(form.getUserName()) != null) {
            return ResultVO.error(ResultEnum.USER_ALREADY_EXIST);
        }
        String password = new BCryptPasswordEncoder().encode(jwtProperties.getDefaultPassword());
        AdminUser user = new AdminUser();
        user.setUserName(form.getUserName());
        user.setPassword(password);
        user.setRole(0);
        if (adminUserDao.insert(user) == 1) {
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO updatePw(UpdatePwForm updatePwForm) {
        AdminUser user = getCurrentUser();
        if (user == null) {
            return ResultVO.error(ResultEnum.USER_NOT_EXIST);
        }
        if (!new BCryptPasswordEncoder().matches(updatePwForm.getOldPw(), user.getPassword())){
            return ResultVO.error(ResultEnum.PASSWORD_ERROR);

        }
        String newPw = new BCryptPasswordEncoder().encode(updatePwForm.getNewPw());
        user.setPassword(newPw);
        if (adminUserDao.updateByPrimaryKey(user)==1){
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO getUserList() {
        List<UserListVO> userList = adminUserDao.getAdminUserList();

        return ResultVO.success(userList);
    }

    @Override
    public ResultVO deleteUser(Integer id) {
        if (adminUserDao.selectByPrimaryKey(id)==null){
            return ResultVO.error(ResultEnum.USER_NOT_EXIST);
        }else {
            if (adminUserDao.deleteByPrimaryKey(id)==1){
                return ResultVO.success();
            }
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);

    }


}
