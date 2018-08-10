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

    private int productCount;

    private int saleCount;

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
                productCount += muttonSkewersCount;
                System.out.println("烤制完" + eachTimeBakeCount + "个，通知在等待的顾客");
                System.out.println("烤制总计：" + productCount);
                System.out.println("售出总计：" + saleCount);

                try {
                    // 通知并放弃当前锁
                    notify();
                } catch (IllegalMonitorStateException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 销售
     * 由于每次只能服务一位顾客，所以需要使用同步锁
     */
    public void sale(int buyCount) {
        System.out.println(Thread.currentThread().getName() + "：我要购买" + buyCount + "个");
        boolean buyResult;

        buyResult = trySale(buyCount);

        while (!buyResult) {
            try {
                // 得到当前锁
                synchronized (this) {
                    // 释放当前锁，阻塞当前线程
                    wait();
                }
            } catch (InterruptedException | IllegalMonitorStateException e) {
                e.printStackTrace();
            }

            buyResult = trySale(buyCount);
        }

//        // 等待得到锁
//        synchronized (this) {
//            if (muttonSkewersCount >= buyCount) {
//                muttonSkewersCount -= buyCount;
//                System.out.println(Thread.currentThread().getName() + "：购买" + buyCount + "个");
//            } else {
//                while (true) {
//                    try {
//                        System.out.println("数量不足，等待烤制");
//                        // 释放锁，阻塞当前线程
//                        wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (IllegalMonitorStateException e) {
//                        e.printStackTrace();
//                    }
//
//                    // 重新获得锁，进行购买
//                    synchronized (this) {
//                        if (muttonSkewersCount >= buyCount) {
//                            muttonSkewersCount -= buyCount;
//                            System.out.println(Thread.currentThread().getName() + "：购买" + buyCount + "个");
//                            break;
//                        }
//                    }
//                }
//            }
//        }
    }

    public synchronized boolean trySale(int buyCount) {
        if (muttonSkewersCount >= buyCount) {
            muttonSkewersCount -= buyCount;
            saleCount += buyCount;
            System.out.println(Thread.currentThread().getName() + "：购买" + buyCount + "个");
            return true;
        }

        return false;
    }

    public void setEachTimeBakeCount(int eachTimeBakeCount) {
        this.eachTimeBakeCount = eachTimeBakeCount;
    }
}
