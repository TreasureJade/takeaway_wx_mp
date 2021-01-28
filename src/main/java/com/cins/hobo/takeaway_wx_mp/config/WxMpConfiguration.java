package com.cins.hobo.takeaway_wx_mp.config;

import com.cins.hobo.takeaway_wx_mp.handler.LogHandler;
import com.cins.hobo.takeaway_wx_mp.handler.MenuHandler;
import com.cins.hobo.takeaway_wx_mp.handler.SubscribeHandler;
import lombok.Data;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static me.chanjar.weixin.common.api.WxConsts.EventType.SUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.EVENT;

/**
 * @author : hobo
 * @className : WxMpConfiguration
 * @date: 2021/1/28
 * @description:  微信配置相关
 */
@Data
@Configuration
public class WxMpConfiguration {

    @Value("${wx.mp.config.appId}")
    private String appId;

    @Value("${wx.mp.config.secret}")
    private String secret;

    @Value("${wx.mp.config.token}")
    private String token;

    @Value("${wx.mp.config.aesKey}")
    private String aesKey;

    @Value("${wx.mp.config.mch_id}")
    private String mchId;

    @Value("${wx.mp.config.mch_key}")
    private String mchKey;

    @Value("${wx.mp.config.key_path}")
    private String keyPath;

    @Autowired
    private MenuHandler menuHandler;

    @Autowired
    private SubscribeHandler subscribeHandler;

    @Autowired
    private LogHandler logHandler;

    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setAppId(this.appId);
        configStorage.setSecret(this.secret);
        configStorage.setToken(this.token);
        configStorage.setAesKey(this.aesKey);
        return configStorage;
    }

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

//    @Bean
//    public WxPayService wxService() {
//        WxPayConfig payConfig = new WxPayConfig();
//        payConfig.setAppId(this.appId);
//        payConfig.setMchId(this.mchId);
//        payConfig.setMchKey(this.mchKey);
//
//        payConfig.setUseSandboxEnv(false);
//        WxPayService wxPayService = new WxPayServiceImpl();
//        wxPayService.setConfig(payConfig);
//        return wxPayService;
//    }


    @Bean
    public WxMpMessageRouter messageRouter(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(this.logHandler).next();

        // 自定义菜单事件
        newRouter.rule().async(false).msgType(EVENT).event(WxConsts.EventType.CLICK).handler(this.menuHandler).end();

        // 关注事件
        newRouter.rule().async(false).msgType(EVENT).event(SUBSCRIBE).handler(this.subscribeHandler).end();

        return newRouter;
    }

}
