package com.liangbx.practice.thread;

import java.util.concurrent.TimeUnit;

/**
 * <p>Title: 烤羊肉串店 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Company: 17173</p>
 *
 * @author liangbx
 * @version 2018/8/9
 */
public class MuttonSkewersShop {

    // 可出售的羊肉串数
    private int muttonSkewersCount;

    // 每次烤制的串数
    private int eachTimeBakeCount = 10;

    /**
     * 开始烤制
     * 每次烤制需要10秒
     */
    public void startBake() {
        while (true) {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("烤制流程出错，不烤了，拜拜~");
                break;
            }

            // 等待得到锁
            synchronized (this) {
                muttonSkewersCount += eachTimeBakeCount;
                System.out.println("烤制完" + eachTimeBakeCount + "个，通知在等待的顾客");
                try {
                    // 通知并放弃当前锁
                    notify();
                } catch (IllegalMonitorStateException e) {
                    System.out.println("无顾客等待中");
                }
            }
        }
    }

    /**
     * 销售
     * 由于每次只能服务一位顾客，所以需要使用同步锁
     */
    public void sale(int buyCount) {
        System.out.println(Thread.currentThread().getName());
        System.out.println("我要购买" + buyCount + "个");

        // 等待得到锁
        synchronized (this) {
            if (muttonSkewersCount >= buyCount) {
                muttonSkewersCount -= buyCount;
                System.out.println("出售" + buyCount + "个");
            } else {
                while (true) {
                    try {
                        System.out.println("数量不足，等待烤制");
                        // 释放锁，阻塞当前线程
                        wait();
                    } catch (InterruptedException e) {
                        if (muttonSkewersCount >= buyCount) {
                            muttonSkewersCount -= buyCount;
                            System.out.println("出售" + buyCount + "个");
                            break;
                        }
                    } catch (IllegalMonitorStateException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setEachTimeBakeCount(int eachTimeBakeCount) {
        this.eachTimeBakeCount = eachTimeBakeCount;
    }
}
