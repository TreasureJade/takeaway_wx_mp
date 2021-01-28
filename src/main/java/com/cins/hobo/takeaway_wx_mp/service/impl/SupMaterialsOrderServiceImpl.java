package com.cins.hobo.takeaway_wx_mp.service.impl;

import com.cins.hobo.takeaway_wx_mp.dao.SupMaterialOrderDetailDao;
import com.cins.hobo.takeaway_wx_mp.dao.SupMaterialOrderTotalDao;
import com.cins.hobo.takeaway_wx_mp.dto.CreateMaterialsOrderDTO;
import com.cins.hobo.takeaway_wx_mp.entry.SupMaterialOrderDetail;
import com.cins.hobo.takeaway_wx_mp.entry.SupMaterialOrderTotal;
import com.cins.hobo.takeaway_wx_mp.entry.SupplierUser;
import com.cins.hobo.takeaway_wx_mp.enums.OrderStatusEnum;
import com.cins.hobo.takeaway_wx_mp.enums.ResultEnum;
import com.cins.hobo.takeaway_wx_mp.form.material_form.CreateMaterialsOrderForm;
import com.cins.hobo.takeaway_wx_mp.form.material_form.UpdateMaterialOrderPriceForm;
import com.cins.hobo.takeaway_wx_mp.service.SupMaterialsOrderService;
import com.cins.hobo.takeaway_wx_mp.service.SupplierUserService;
import com.cins.hobo.takeaway_wx_mp.util.TimeUtils;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import com.cins.hobo.takeaway_wx_mp.vo.SupOrderListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @author : hobo
 * @className : SupMaterialsOrderServiceImpl
 * @date: 2021/1/24
 * @description: TODO
 */
@Service
@Slf4j
public class SupMaterialsOrderServiceImpl implements SupMaterialsOrderService {

    @Autowired
    private SupMaterialOrderDetailDao orderDetailDao;

    @Autowired
    private SupMaterialOrderTotalDao orderTotalDao;


    @Autowired
    private SupplierUserService userService;

    @Override
    public ResultVO createNewMaterialsOrder(List<CreateMaterialsOrderForm> materialsOrders) {
        final String nowTime = TimeUtils.getOrderTime();
        for (CreateMaterialsOrderForm order : materialsOrders) {
            String orderId = nowTime + "_" + order.getSubUserId();
            for (CreateMaterialsOrderDTO dto : order.getOrderDTOList()) {
                SupMaterialOrderDetail detail = new SupMaterialOrderDetail();
                BeanUtils.copyProperties(dto, detail);
                detail.setOrderId(orderId);
                detail.setCreateTime(TimeUtils.getTimeCN());
                detail.setOrderStatus(OrderStatusEnum.NEW.getCode());
                detail.setSubUserId(order.getSubUserId());
                if (orderDetailDao.insertSelective(detail) != 1) {
                    return ResultVO.error(ResultEnum.CREATE_ORDER_ERROR);
                }
            }
            SupMaterialOrderTotal orderTotal = new SupMaterialOrderTotal();
            orderTotal.setOrderId(orderId);
            orderTotal.setSupUserId(order.getSubUserId());
            orderTotal.setTotalPrice(BigDecimal.valueOf(0));
            orderTotal.setOrderStatus(OrderStatusEnum.NEW.getCode());
            orderTotal.setCreateTime(TimeUtils.getTimeCN());
            if (orderTotalDao.insertSelective(orderTotal) != 1) {
                // TODO 添加消息通知（from admin to sup_user）
                return ResultVO.error(ResultEnum.CREATE_ORDER_ERROR);
            }
        }
        return ResultVO.success();
    }

    @Override
    public ResultVO supUserUpdateMaterialOrder(String openId, List<UpdateMaterialOrderPriceForm> priceForms) {
        SupplierUser user = userService.getSupUserByOpenId(openId);
        if (user == null) {
            return ResultVO.error(ResultEnum.AUTHENTICATION_ERROR);
        }
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        String orderId = "";
        for (UpdateMaterialOrderPriceForm form :
                priceForms) {
            SupMaterialOrderDetail detail = orderDetailDao.selectByPrimaryKey(form.getId());
            detail.setMaterialPrice(form.getPrice());
            orderAmount = detail.getMaterialPrice()
                    .multiply(BigDecimal.valueOf(detail.getMaterialQuantity()))
                    .add(orderAmount);
            orderId = detail.getOrderId();
            detail.setUpdateTime(TimeUtils.getTimeCN());
            detail.setOrderStatus(OrderStatusEnum.HAS_BEEN_PROCESSED.getCode());
            orderDetailDao.updateByPrimaryKeySelective(detail);
        }
        SupMaterialOrderTotal orderTotal = orderTotalDao.selectOrderByOrderId(orderId);
        orderTotal.setTotalPrice(orderAmount);
        orderTotal.setUpdateTime(TimeUtils.getTimeCN());
        orderTotal.setOrderStatus(OrderStatusEnum.HAS_BEEN_PROCESSED.getCode());
        if (orderTotalDao.updateByPrimaryKeySelective(orderTotal) == 1) {
            // TODO 添加消息通知（from sup_user to admin）
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO getSupUserAllNewOrder(String openId) {
        SupplierUser user = userService.getSupUserByOpenId(openId);
        if (user == null) {
            return ResultVO.error(ResultEnum.AUTHENTICATION_ERROR);
        }
        List<SupOrderListVO> orderList = orderDetailDao.getSupUserAllNewOrder(user.getId(),
                OrderStatusEnum.NEW.getCode());
        return ResultVO.success(orderList);
    }

}
