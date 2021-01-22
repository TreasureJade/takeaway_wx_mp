package com.cins.hobo.takeaway_wx_mp.service.impl;

import com.cins.hobo.takeaway_wx_mp.dao.SupplierUserDao;
import com.cins.hobo.takeaway_wx_mp.entry.AdminUser;
import com.cins.hobo.takeaway_wx_mp.entry.SupplierUser;
import com.cins.hobo.takeaway_wx_mp.enums.ResultEnum;
import com.cins.hobo.takeaway_wx_mp.exception.GlobalException;
import com.cins.hobo.takeaway_wx_mp.form.LoginForm;
import com.cins.hobo.takeaway_wx_mp.form.UpdatePwForm;
import com.cins.hobo.takeaway_wx_mp.form.UpdateSupplierInfoForm;
import com.cins.hobo.takeaway_wx_mp.security.JwtProperties;
import com.cins.hobo.takeaway_wx_mp.security.JwtTokenUtil;
import com.cins.hobo.takeaway_wx_mp.security.JwtUserDetailServiceImpl;
import com.cins.hobo.takeaway_wx_mp.service.SupplierUserService;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
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

/**
 * @author : hobo
 * @className : SupplierUserServiceImpl
 * @date: 2021/1/20
 * @description: TODO
 */
@Service
public class SupplierUserServiceImpl implements SupplierUserService {

    @Autowired
    private SupplierUserDao supplierUserDao;

    @Autowired
    private JwtUserDetailServiceImpl jwtUserDetailService;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;


    public SupplierUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String key = "anonymousUser";
        if (authentication != null && !username.equals(key)) {
            return getSupplierUserByUsername(username);
        }
        return null;
    }

    @Override
    public SupplierUser getSupplierUserByUsername(String username) {
        SupplierUser user = supplierUserDao.getSupplierUserByUsername(username);
        if (user == null) {
            return new SupplierUser();
        }
        return user;
    }

    @Override
    public ResultVO login(LoginForm loginForm, HttpServletResponse response) {
        SupplierUser user = getSupplierUserByUsername(loginForm.getUserName());
        if (user == null) {
            return ResultVO.error(ResultEnum.USER_NOT_EXIST);
        }
        UserDetails userDetails = jwtUserDetailService.loadUserByUsername(loginForm.getUserName());

        if (!new BCryptPasswordEncoder().matches(loginForm.getPassword(), userDetails.getPassword())) {
            return ResultVO.error(ResultEnum.PASSWORD_ERROR);
        }

        Authentication token = new UsernamePasswordAuthenticationToken(loginForm.getUserName(),
                loginForm.getPassword(), userDetails.getAuthorities());
        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String realToken = jwtTokenUtil.generateToken(userDetails);

        response.addHeader(jwtProperties.getTokenName(), realToken);

        HashMap<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("token", realToken);
        return ResultVO.success(map);
    }

    @Override
    public ResultVO updateInfo(UpdateSupplierInfoForm form) {
        SupplierUser supplierUser = getCurrentUser();
        if (supplierUser != null) {
            BeanUtils.copyProperties(form, supplierUser);
            if (supplierUserDao.updateByPrimaryKeySelective(supplierUser) == 1) {
                return ResultVO.success();
            }
            return ResultVO.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVO.error(ResultEnum.AUTHENTICATION_ERROR);
    }

    @Override
    public ResultVO updatePw(UpdatePwForm form) {
        SupplierUser supplierUser = getCurrentUser();
        if (supplierUser != null) {
            if (!new BCryptPasswordEncoder().matches(form.getOldPw(), supplierUser.getPassword())) {
                return ResultVO.error(ResultEnum.PASSWORD_ERROR);
            }
            String newPw = new BCryptPasswordEncoder().encode(form.getNewPw());
            supplierUser.setPassword(newPw);
            if (supplierUserDao.updateByPrimaryKeySelective(supplierUser) == 1) {
                return ResultVO.success();
            }
            return ResultVO.error(ResultEnum.SERVER_ERROR);
        }
        return ResultVO.error(ResultEnum.AUTHENTICATION_ERROR);
    }
}
