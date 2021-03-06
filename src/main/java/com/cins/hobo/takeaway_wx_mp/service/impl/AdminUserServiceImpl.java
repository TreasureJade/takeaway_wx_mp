package com.cins.hobo.takeaway_wx_mp.service.impl;

import com.cins.hobo.takeaway_wx_mp.dao.AdminUserDao;
import com.cins.hobo.takeaway_wx_mp.dao.SupplierUserDao;
import com.cins.hobo.takeaway_wx_mp.dao.WxUserInfoDao;
import com.cins.hobo.takeaway_wx_mp.entry.AdminUser;
import com.cins.hobo.takeaway_wx_mp.entry.SupplierUser;
import com.cins.hobo.takeaway_wx_mp.entry.WxUserInfo;
import com.cins.hobo.takeaway_wx_mp.enums.ResultEnum;
import com.cins.hobo.takeaway_wx_mp.enums.RoleEnum;
import com.cins.hobo.takeaway_wx_mp.form.AddSupplierUserForm;
import com.cins.hobo.takeaway_wx_mp.form.AddUserForm;
import com.cins.hobo.takeaway_wx_mp.form.LoginForm;
import com.cins.hobo.takeaway_wx_mp.form.UpdatePwForm;
import com.cins.hobo.takeaway_wx_mp.security.JwtProperties;
import com.cins.hobo.takeaway_wx_mp.security.JwtTokenUtil;
import com.cins.hobo.takeaway_wx_mp.security.JwtUserDetailServiceImpl;
import com.cins.hobo.takeaway_wx_mp.service.AdminUserService;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import com.cins.hobo.takeaway_wx_mp.vo.SupplierUserListVO;
import com.cins.hobo.takeaway_wx_mp.vo.UserListVO;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private SupplierUserDao supplierUserDao;

    @Autowired
    private WxUserInfoDao wxUserInfoDao;

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
        user.setRole(RoleEnum.ADMIN.getValue());
        if (adminUserDao.insert(user) == 1) {
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO updatePw(UpdatePwForm updatePwForm) {
        AdminUser user = getCurrentUser();
        if (user == null) {
            return ResultVO.error(ResultEnum.AUTHENTICATION_ERROR);
        }
        if (!new BCryptPasswordEncoder().matches(updatePwForm.getOldPw(), user.getPassword())) {
            return ResultVO.error(ResultEnum.PASSWORD_ERROR);

        }
        String newPw = new BCryptPasswordEncoder().encode(updatePwForm.getNewPw());
        user.setPassword(newPw);
        if (adminUserDao.updateByPrimaryKeySelective(user) == 1) {
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
        if (adminUserDao.selectByPrimaryKey(id) == null) {
            return ResultVO.error(ResultEnum.USER_NOT_EXIST);
        } else {
            if (adminUserDao.deleteByPrimaryKey(id) == 1) {
                return ResultVO.success();
            }
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);

    }

    @Override
    public ResultVO insertSupplierUser(AddSupplierUserForm addSupplierUserForm) {
        if (supplierUserDao.selectByPhoneNum(addSupplierUserForm.getPhoneNum()) != null) {
            return ResultVO.error(ResultEnum.USER_ALREADY_EXIST);
        }
        SupplierUser user = new SupplierUser();
        BeanUtils.copyProperties(addSupplierUserForm, user);
        if (supplierUserDao.insert(user) == 1) {
            return ResultVO.success(user);
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO updateAdminUserRole(Integer id, Integer role) {
        AdminUser user = adminUserDao.selectByPrimaryKey(id);
        if (user == null) {
            return ResultVO.error(ResultEnum.USER_NOT_EXIST);
        }
        user.setRole(role);
        if (adminUserDao.updateByPrimaryKeySelective(user) == 1) {
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO getSupplierUserList() {
        List<SupplierUserListVO> userListVOList = supplierUserDao.getSupplierUserList();
        return ResultVO.success(userListVOList);
    }

    @Override
    public ResultVO deleteSupplierUser(Integer id) {
        SupplierUser supplierUser = supplierUserDao.selectByPrimaryKey(id);
        if (supplierUser == null) {
            return ResultVO.error(ResultEnum.USER_NOT_EXIST);
        }
        if (supplierUserDao.deleteByPrimaryKey(id) == 1) {
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO updatePwToDefaultPw(Integer id) {
        AdminUser adminUser = adminUserDao.selectByPrimaryKey(id);
        if (adminUser == null) {
            return ResultVO.error(ResultEnum.USER_NOT_EXIST);
        }
        adminUser.setPassword(new BCryptPasswordEncoder().encode(jwtProperties.getDefaultPassword()));
        if (adminUserDao.updateByPrimaryKeySelective(adminUser) == 1) {
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO getWxUserList() {
        List<WxUserInfo> wxUserInfos = wxUserInfoDao.getWxUserList();
        return ResultVO.success(wxUserInfos);
    }

    @Override
    public ResultVO getWxUserByNickname(String nickname) {
        if (wxUserInfoDao.selectByNickname(nickname) == null) {
            return ResultVO.error(ResultEnum.USER_NOT_EXIST);
        }
        return ResultVO.success(wxUserInfoDao.selectByNickname(nickname));
    }


}
