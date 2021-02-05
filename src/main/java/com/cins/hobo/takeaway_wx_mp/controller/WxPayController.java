package com.cins.hobo.takeaway_wx_mp.controller;

import com.cins.hobo.takeaway_wx_mp.service.PayService;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : hobo
 * @className : WxPayController
 * @date: 2021/2/1
 * @description: 微信支付相关接口
 */
@Api(tags = "微信支付相关接口")
@RestController
@RequestMapping("/wx/pay")
@CrossOrigin
public class WxPayController {

    @Autowired
    private PayService wxPayService;

    @ApiOperation("预支付请求")
    @PostMapping("/create")
    public ResultVO createPayOrder(String orderId, HttpServletRequest request){
        return wxPayService.create(orderId,request);
    }

    @ApiOperation("微信异步回调接口")
    @PostMapping("/notify")
    public ResultVO wxNotify(@RequestBody String xmlData){
        return wxPayService.wxNotify(xmlData);
    }


}
