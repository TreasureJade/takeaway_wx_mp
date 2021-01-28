package com.cins.hobo.takeaway_wx_mp.service;

import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;

/**
 * @author : hobo
 * @interfaceName : WxUserServiceImpl
 * @date: 2021/1/28
 * @description: TODO
 */
public interface WxUserService {


    ResultVO getUserInfo(String code);
}
