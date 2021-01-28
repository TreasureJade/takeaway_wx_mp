package com.cins.hobo.takeaway_wx_mp.controller;

import com.cins.hobo.takeaway_wx_mp.config.WxMpConfiguration;
import com.cins.hobo.takeaway_wx_mp.enums.ResultEnum;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : hobo
 * @className : WxMenuController
 * @date: 2021/1/28
 * @description: TODO
 */
@RestController
@RequestMapping("/wx/menu")
@CrossOrigin
@Slf4j
@Api(tags = "微信菜单接口")
public class WxMenuController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpConfiguration wxMpConfiguration;

    @GetMapping("/create")
    public ResultVO createMenu(){
        WxMenu wxMenu = new WxMenu();
        WxMenuButton button1 = new WxMenuButton();
        button1.setType(WxConsts.MenuButtonType.VIEW);
        button1.setName("点外卖");
        button1.setUrl("https://www.baidu.com");
        wxMenu.getButtons().add(button1);

        WxMenuButton button2 = new WxMenuButton();
        button2.setType(WxConsts.MenuButtonType.VIEW);
        button2.setName("留言建议");
        button2.setUrl("https://www.baidu.com");
        wxMenu.getButtons().add(button2);

        wxMpService.switchover(wxMpConfiguration.getAppId());
        try {
            return ResultVO.success(wxMpService.getMenuService().menuCreate(wxMenu));
        } catch (WxErrorException e) {
            log.error("菜单创建失败，失败原因: {}", e.getMessage());
            return ResultVO.error(ResultEnum.WX_MENU_CREAT_FILED);
        }
    }

}
