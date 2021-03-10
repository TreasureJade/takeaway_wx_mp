package com.cins.hobo.takeaway_wx_mp.controller;

import com.cins.hobo.takeaway_wx_mp.config.WxMpConfiguration;
import com.cins.hobo.takeaway_wx_mp.service.WxUserService;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : hobo
 * @className : WxUserController
 * @date: 2021/1/28
 * @description: 微信用户授权接口
 */
@Slf4j
@RestController
@RequestMapping("/wx/auth")
@Api(tags = "微信用户授权接口")
public class WxUserAuthController {

    @Autowired
    protected WxMpService wxService;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private WxMpConfiguration wxMpConfiguration;

    @ApiOperation("微信服务器测试接口")
    @GetMapping(value = "/test")
    public String authGet(
            @RequestParam(name = "signature", required = false) String signature,
            @RequestParam(name = "timestamp", required = false) String timestamp,
            @RequestParam(name = "nonce", required = false) String nonce,
            @RequestParam(name = "echostr", required = false) String echostr) {

        log.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature,
                timestamp, nonce, echostr);
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        if (wxService.switchover(wxMpConfiguration.getAppId())) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", wxMpConfiguration.getAppId()));
        }
        if (wxService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }

        return "非法请求";
    }

    @GetMapping("/authorize")
    @ApiOperation("请求授权")
    public Object authorize(@RequestParam("returnUrl") String returnUrl) {
        String redirectUrl = wxService.oauth2buildAuthorizationUrl(returnUrl, WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
        log.info("【微信网页授权】获取code,redirectURL={}", redirectUrl);
        return ResultVO.success(redirectUrl);
    }

    @GetMapping("/userInfo")
    @ApiOperation("微信授权获取用户信息")
    public Object userInfo(@ApiParam(value = "微信code") String code) throws Exception {
        return wxUserService.getUserInfo(code);
    }

}
