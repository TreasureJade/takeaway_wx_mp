package com.cins.hobo.takeaway_wx_mp.controller;

import com.cins.hobo.takeaway_wx_mp.form.ConsumerCreateOrderForm;
import com.cins.hobo.takeaway_wx_mp.service.ConsumerOrderService;
import com.cins.hobo.takeaway_wx_mp.service.DishesService;
import com.cins.hobo.takeaway_wx_mp.service.LeaveMsgService;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : hobo
 * @className : ConsumerController
 * @date: 2021/2/1
 * @description: 消费者接口相关
 */
@Api(tags = "消费者接口")
@RestController
@RequestMapping("/wx/consumer")
@CrossOrigin
public class ConsumerController {

    @Autowired
    private ConsumerOrderService orderService;

    @Autowired
    private DishesService dishesService;

    @Autowired
    private LeaveMsgService msgService;

    @ApiOperation("创建订单")
    @PostMapping("/order")
    public ResultVO createOrder(@RequestBody ConsumerCreateOrderForm orderForm) {
        return orderService.createOrder(orderForm);
    }

    @ApiOperation("创建预约订单")
    @PostMapping("/adv_order")
    public ResultVO createAdvOrder(@RequestBody ConsumerCreateOrderForm orderForm) {
        ResultVO vo = orderService.createOrder(orderForm);
        if (vo.getCode() == 0) {
            String orderId = (String) vo.getData();
            return orderService.createAdvOrder(orderId, orderForm.getAdvTime());
        }
        return null;
    }

    @ApiOperation("查看订单详情")
    @GetMapping("/order")
    public ResultVO getOrderDetail(String orderId) {
        return orderService.getOrderDetail(orderId);
    }

    @ApiOperation("获取所有订单")
    @GetMapping("/order_list")
    public ResultVO getAllOrder(String openId) {
        return orderService.getAllOrder(openId);

    }

    @ApiOperation("获取菜单")
    @GetMapping("/dishes")
    public ResultVO getDishesList() {
        return dishesService.getAll();
    }

    @ApiOperation("获取所有留言")
    @GetMapping("/leave_msg")
    public ResultVO getAllLeaveMsg(String openId) {
        return msgService.consumerSelectAllLeaveMsg(openId);
    }
}
