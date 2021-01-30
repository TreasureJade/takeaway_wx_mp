package com.cins.hobo.takeaway_wx_mp.controller;

import com.cins.hobo.takeaway_wx_mp.form.material_form.CreateMaterialsOrderForm;
import com.cins.hobo.takeaway_wx_mp.form.material_form.UpdateMaterialOrderPriceForm;
import com.cins.hobo.takeaway_wx_mp.service.SupMaterialsOrderService;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : hobo
 * @className : MaterialsOrderController
 * @date: 2021/1/23
 * @description: 供货商订单相关接口
 */
@RestController
@Api(tags = "供货商订单相关接口")
@CrossOrigin
@RequestMapping("/materials_order")
public class SupMaterialsOrderController {


    @Autowired
    private SupMaterialsOrderService orderService;

    @ApiOperation("店员创建订单")
    @PostMapping("/admin_order")
    public ResultVO createNewMaterialsOrder(@RequestBody List<CreateMaterialsOrderForm> orderForm){
        return orderService.createNewMaterialsOrder(orderForm);
    }

    @ApiOperation("店员查看所有订单")
    @GetMapping("/admin_order")
    public ResultVO getAllOrders(){
        return orderService.getAllOrders();
    }

    @ApiOperation("店员查看订单详细")
    @GetMapping("/admin_order_detail")
    public ResultVO getOrderDetail(String orderId){
        return orderService.getOrderDetail(orderId);
    }

    @ApiOperation("供货商查看所有未处理订单")
    @GetMapping("/sup_order")
    public ResultVO getSupUserAllNewOrder(@ApiParam(value = "微信用户openId") String openId){
        return orderService.getSupUserAllNewOrder(openId);
    }

    @ApiOperation("供货商修改订单中原材料单价")
    @PutMapping("/sup_order")
    public ResultVO supUserUpdateOrderMaterialsPrice(@RequestBody List<UpdateMaterialOrderPriceForm> priceForms){
        return orderService.supUserUpdateMaterialOrder(priceForms);
    }

}


