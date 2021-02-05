package com.cins.hobo.takeaway_wx_mp.service.impl;

import com.cins.hobo.takeaway_wx_mp.dao.ConsumerOrderDetailDao;
import com.cins.hobo.takeaway_wx_mp.dao.ConsumerOrderTotalDao;
import com.cins.hobo.takeaway_wx_mp.dao.DishesDetailDao;
import com.cins.hobo.takeaway_wx_mp.dto.ConsumerOrderDetailDTO;
import com.cins.hobo.takeaway_wx_mp.dto.ConsumerOrderDishesDetailDTO;
import com.cins.hobo.takeaway_wx_mp.entry.ConsumerOrderDetail;
import com.cins.hobo.takeaway_wx_mp.entry.ConsumerOrderTotal;
import com.cins.hobo.takeaway_wx_mp.entry.DishesDetail;
import com.cins.hobo.takeaway_wx_mp.enums.OrderStatusEnum;
import com.cins.hobo.takeaway_wx_mp.enums.PayStatusEnum;
import com.cins.hobo.takeaway_wx_mp.enums.ResultEnum;
import com.cins.hobo.takeaway_wx_mp.form.ConsumerCreateOrderForm;
import com.cins.hobo.takeaway_wx_mp.service.ConsumerOrderService;
import com.cins.hobo.takeaway_wx_mp.util.RandomUtils;
import com.cins.hobo.takeaway_wx_mp.util.TimeUtils;
import com.cins.hobo.takeaway_wx_mp.vo.ConsumerOrderDetailVO;
import com.cins.hobo.takeaway_wx_mp.vo.ConsumerOrderListVO;
import com.cins.hobo.takeaway_wx_mp.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @author : hobo
 * @className : ConsumerOrderServiceImpl
 * @date: 2021/2/1
 * @description: TODO
 */
@Service
@Slf4j
public class ConsumerOrderServiceImpl implements ConsumerOrderService {

    @Autowired
    private ConsumerOrderDetailDao orderDetailDao;

    @Autowired
    private ConsumerOrderTotalDao orderTotalDao;

    @Autowired
    private DishesDetailDao dishesDetailDao;

    @Override
    public ResultVO createOrder(ConsumerCreateOrderForm orderForm) {
        String orderNo = RandomUtils.getRandomStringByLength(TimeUtils.getOrderTime(),18);
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for (ConsumerOrderDishesDetailDTO dto : orderForm.getDetailList()){
            DishesDetail dishesDetail = dishesDetailDao.selectByPrimaryKey(dto.getDishesId());
            BigDecimal dishPrice = dishesDetail.getPrePrice();
            if (dishPrice.compareTo(BigDecimal.valueOf(-1))==0){
                dishPrice = dishesDetail.getOriPrice();
            }
            orderAmount = dishPrice
                    .multiply(new BigDecimal(dto.getDishesQuantity()))
                    .add(orderAmount);
            ConsumerOrderDetail detail = new ConsumerOrderDetail();
            BeanUtils.copyProperties(dto,detail);
            detail.setOrderId(orderNo);
            detail.setDishesPrice(dishPrice);
            detail.setCreateTime(TimeUtils.getTimeCN());
            insert(detail);
        }
        ConsumerOrderTotal orderTotal = new ConsumerOrderTotal();
        BeanUtils.copyProperties(orderForm,orderTotal);
        orderTotal.setOrderId(orderNo);
        orderTotal.setOrderAmount(orderAmount);
        orderTotal.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderTotal.setPayStatus(PayStatusEnum.WAIt.getPayStatu());
        orderTotal.setCreateTime(TimeUtils.getTimeCN());
        log.info("openId:{} 的用户下单，订单号为:{},订单总价为:{}",orderTotal.getOpenId()
                ,orderTotal.getOrderId(),orderAmount);
        if (orderTotalDao.insert(orderTotal)==1){
            return ResultVO.success();
        }
        return ResultVO.error(ResultEnum.SERVER_ERROR);
    }

    @Override
    public ResultVO getOrderDetail(String orderId) {
        ConsumerOrderDetailVO result = orderTotalDao.getOrderDetailByOrderId(orderId);
        List<ConsumerOrderDetailDTO> detailList = orderDetailDao.selectByOrderId(orderId);
        result.setDetailList(detailList);
        return ResultVO.success(result);
    }

    @Override
    public ResultVO getAllOrder(String openId) {
        List<ConsumerOrderListVO> result = orderTotalDao.getAllOrderByOpenId(openId);
        return ResultVO.success(result);
    }

    private int insert(ConsumerOrderDetail orderDetail){
        return orderDetailDao.insertSelective(orderDetail);
    }
}
