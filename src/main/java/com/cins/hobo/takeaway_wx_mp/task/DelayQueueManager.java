package com.cins.hobo.takeaway_wx_mp.task;

import com.cins.hobo.takeaway_wx_mp.dao.ConsumerOrderAdvDao;
import com.cins.hobo.takeaway_wx_mp.entry.ConsumerOrderAdv;
import com.cins.hobo.takeaway_wx_mp.enums.OrderStatusEnum;
import com.cins.hobo.takeaway_wx_mp.service.WxMsgSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;

/**
 * @author : hobo
 * @className : DelayQueueManager
 * @date: 2021/3/8
 * @description: TODO
 */
@Slf4j
@Component
public class DelayQueueManager implements CommandLineRunner {


    private DelayQueue<DelayTask> delayQueue = new DelayQueue<>();

    @Autowired
    private WxMsgSendService wxMsgSendService;

    @Autowired
    private ConsumerOrderAdvDao consumerOrderAdvDao;

    /**
     * 加入到延时队列中
     * @param task
     */
    public void put(DelayTask task) {
        log.info("加入延时任务：{}", task);
        delayQueue.put(task);
    }

    /**
     * 取消延时任务
     * @param task
     * @return
     */
    public boolean remove(DelayTask task){
        log.info("取消延时任务:{}",task);
        return delayQueue.remove(task);
    }


    @Override
    public void run(String... args) throws Exception {
        log.info("初始化延时队列");
        Executors.newSingleThreadExecutor().execute(new Thread(this::excuteThread));
    }

    private void excuteThread() {
        while (true) {
            try {
                DelayTask task = delayQueue.take();
                processTask(task);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    /**
     * 内部执行延时任务
     * @param task
     */
    private void processTask(DelayTask task) {
        log.info("执行延时任务：{}", task);
        String openId = task.getTaskBase().getOpenId();
        String orderId = task.getTaskBase().getOrderId();
        String advTime = task.getTaskBase().getAdvTime();

        //  发送消息并将订单状态改为已通知
        wxMsgSendService.sendCreateAdvOrderMsg(openId,advTime,orderId);
        ConsumerOrderAdv orderAdv = consumerOrderAdvDao.selectByOrderId(orderId);
        orderAdv.setAdvStatus(OrderStatusEnum.HAS_BEEN_PROCESSED.getCode());
        consumerOrderAdvDao.updateByPrimaryKeySelective(orderAdv);
    }

}
