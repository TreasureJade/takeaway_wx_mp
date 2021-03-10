package com.cins.hobo.takeaway_wx_mp.service.impl;

import com.cins.hobo.takeaway_wx_mp.dao.ConsumerOrderAdvDao;
import com.cins.hobo.takeaway_wx_mp.dao.ConsumerOrderDetailDao;
import com.cins.hobo.takeaway_wx_mp.dao.ConsumerOrderTotalDao;
import com.cins.hobo.takeaway_wx_mp.dao.DishesDetailDao;
import com.cins.hobo.takeaway_wx_mp.dto.ConsumerOrderDetailDTO;
import com.cins.hobo.takeaway_wx_mp.dto.ConsumerOrderDishesDetailDTO;
import com.cins.hobo.takeaway_wx_mp.entry.ConsumerOrderAdv;
import com.cins.hobo.takeaway_wx_mp.entry.ConsumerOrderDetail;
import com.cins.hobo.takeaway_wx_mp.entry.ConsumerOrderTotal;
import com.cins.hobo.takeaway_wx_mp.entry.DishesDetail;
import com.cins.hobo.takeaway_wx_mp.enums.OrderStatusEnum;
import com.cins.hobo.takeaway_wx_mp.enums.PayStatusEnum;
import com.cins.hobo.takeaway_wx_mp.enums.ResultEnum;
import com.cins.hobo.takeaway_wx_mp.form.ConsumerCreateOrderForm;
import com.cins.hobo.takeaway_wx_mp.service.ConsumerOrderService;
import com.cins.hobo.takeaway_wx_mp.task.DelayQueueManager;
import com.cins.hobo.takeaway_wx_mp.task.DelayTask;
import com.cins.hobo.takeaway_wx_mp.task.TaskBase;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Autowired
    private ConsumerOrderAdvDao orderAdvDao;

    @Autowired
    private DelayQueueManager delayQueueManager;

    @Override
    public ResultVO createOrder(ConsumerCreateOrderForm orderForm) {
        String orderNo = RandomUtils.getRandomStringByLength(TimeUtils.getOrderTime(), 18);
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for (ConsumerOrderDishesDetailDTO dto : orderForm.getDetailList()) {
            DishesDetail dishesDetail = dishesDetailDao.selectByPrimaryKey(dto.getDishesId());
            BigDecimal dishPrice = dishesDetail.getPrePrice();
            if (dishPrice.compareTo(BigDecimal.valueOf(-1)) == 0) {
                dishPrice = dishesDetail.getOriPrice();
            }
            orderAmount = dishPrice
                    .multiply(new BigDecimal(dto.getDishesQuantity()))
                    .add(orderAmount);
            ConsumerOrderDetail detail = new ConsumerOrderDetail();
            BeanUtils.copyProperties(dto, detail);
            detail.setOrderId(orderNo);
            detail.setDishesPrice(dishPrice);
            detail.setCreateTime(TimeUtils.getTimeCN());
            insert(detail);
        }
        ConsumerOrderTotal orderTotal = new ConsumerOrderTotal();
        BeanUtils.copyProperties(orderForm, orderTotal);
        orderTotal.setOrderId(orderNo);
        orderTotal.setOrderAmount(orderAmount);
        orderTotal.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderTotal.setPayStatus(PayStatusEnum.WAIt.getPayStatu());
        orderTotal.setCreateTime(TimeUtils.getTimeCN());
        log.info("openId:{} 的用户下单，订单号为:{},订单总价为:{}", orderTotal.getOpenId()
                , orderTotal.getOrderId(), orderAmount);
        if (orderTotalDao.insert(orderTotal) == 1) {
            return ResultVO.success(orderNo);
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

    @Override
    public ResultVO createAdvOrder(String orderId, String advTime) {
        ConsumerOrderAdv consumerOrderAdv = new ConsumerOrderAdv();
        consumerOrderAdv.setOrderId(orderId);
        consumerOrderAdv.setAdvTime(advTime);
        consumerOrderAdv.setAdvStatus(OrderStatusEnum.NEW.getCode());

        if (orderAdvDao.insertSelective(consumerOrderAdv) != 1) {
            return ResultVO.error(ResultEnum.CREATE_ORDER_ERROR);
        }

        //  加入延时队列
        try {
            ConsumerOrderTotal total = orderTotalDao.selectByOrderId(orderId);
            TaskBase taskBase = new TaskBase(orderId, total.getOpenId(), advTime);
            DelayTask task = new DelayTask(taskBase, convert(advTime));
            delayQueueManager.put(task);
            return ResultVO.success();
        } catch (ParseException e) {
            log.error("订单:{}加入延时队列时发生异常, 异常原因:{}", orderId, e.getMessage());
            return ResultVO.error(ResultEnum.SERVER_ERROR);
        }

    }

    private void insert(ConsumerOrderDetail orderDetail) {
        orderDetailDao.insertSelective(orderDetail);
    }

    private long convert(String advTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date advDate = format.parse(advTime);
        return advDate.getTime() - System.currentTimeMillis();
    }
}
