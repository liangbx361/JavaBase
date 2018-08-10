package com.liangbx.practice.thread;

import java.util.Random;

/**
 * <p>Title: 生产者消费者模型 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Company: 17173</p>
 *
 * @author liangbx
 * @version 2018/8/9
 */
public class ProductConsumeSample {

    static int sBuyCount;

    public static void main(String[] args) {
        final MuttonSkewersShop shop = new MuttonSkewersShop();

        // 一个店铺开张
        new Thread(new Runnable() {
            @Override
            public void run() {
                shop.startBake();
            }
        }, "shop").start();

        // N个顾客购买
        final Random random = new Random();
        for(int i=1; i<=10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int buyCount = random.nextInt(20) + 1;
                    sBuyCount += buyCount;
                    System.out.println("共需购买: " + sBuyCount);
                    shop.sale(buyCount);
                }
            }, "customer-" + i).start();
        }
    }
}
