package com.cins.hobo.takeaway_wx_mp.service.impl;

import com.cins.hobo.takeaway_wx_mp.dao.WxUserInfoDao;
import com.cins.hobo.takeaway_wx_mp.entry.WxUserInfo;
import com.cins.hobo.takeaway_wx_mp.enums.ResultEnum;
import com.cins.hobo.takeaway_wx_mp.service.WxUserService;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : hobo
 * @className : WxUserServiceImpl
 * @date: 2021/1/28
 * @description: TODO
 */
@Service
@Slf4j
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxUserInfoDao wxUserInfoDao;

    @Override
    public ResultVO getUserInfo(String code) {
        log.info("【微信网页授权】code={}", code);
        WxMpOAuth2AccessToken oAuth2AccessToken = null;
        try {
            oAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("e:{}", e);
        }
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = wxMpService.oauth2getUserInfo(oAuth2AccessToken, null);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        WxUserInfo wxUserInfo = new WxUserInfo();
        if (wxMpUser != null) {
            wxUserInfo.setOpenId(wxMpUser.getOpenId());
            wxUserInfo.setNickname(wxMpUser.getNickname());
            if (wxUserInfoDao.insert(wxUserInfo)==1){
                return ResultVO.success();
            }
        }
        return ResultVO.error(ResultEnum.WX_AUTHORIZATION_FAILED);
    }
}
