package com.cins.hobo.takeaway_wx_mp.controller;

import com.cins.hobo.takeaway_wx_mp.form.material_form.CreateMaterialsOrderForm;
import com.cins.hobo.takeaway_wx_mp.form.material_form.UpdateMaterialOrderPriceForm;
import com.cins.hobo.takeaway_wx_mp.service.SupMaterialsOrderService;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @PostMapping("/admin_order")
    public ResultVO createNewMaterialsOrder(@RequestBody List<CreateMaterialsOrderForm> orderForm){
        return orderService.createNewMaterialsOrder(orderForm);
    }

    @ApiOperation("供货商查看所有未处理订单")
    @GetMapping("/sup_order")
    public ResultVO getSupUserAllNewOrder(){
        return orderService.getSupUserAllNewOrder();
    }

    @ApiOperation("供货商修改订单中原材料单价")
    @PutMapping("/sup_order")
    public ResultVO supUserUpdateOrderMaterialsPrice(@RequestBody List<UpdateMaterialOrderPriceForm> priceForms){
        return orderService.supUserUpdateMaterialOrder(priceForms);
    }

}


