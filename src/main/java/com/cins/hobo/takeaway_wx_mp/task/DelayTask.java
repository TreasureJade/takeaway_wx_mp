package com.cins.hobo.takeaway_wx_mp.task;

import lombok.Data;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author : hobo
 * @className : DelayTask
 * @date: 2021/3/8
 * @description: 延时队列
 */
@Data
public class DelayTask implements Delayed {


    public DelayTask(TaskBase taskBase, long expire) {
        this.taskBase = taskBase;
        this.expire = expire + System.currentTimeMillis();
    }

    private TaskBase taskBase;
    /**
     * 任务延时时间 单位：ms
     */
    private long expire;



    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expire - System.currentTimeMillis(), unit);
    }

    @Override
    public int compareTo(Delayed o) {
        long delta = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return (int) delta;
    }
}
