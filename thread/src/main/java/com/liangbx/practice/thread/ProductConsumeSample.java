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

    public static void main(String[] args) {
        final MuttonSkewersShop shop = new MuttonSkewersShop();

        // 店铺开张
        new Thread(new Runnable() {
            @Override
            public void run() {
                shop.startBake();
            }
        }, "shop").start();

        // 顾客购买
        final Random random = new Random();
        for(int i=0; i<1; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    shop.sale(random.nextInt(20)+1);
                }
            }, "customer - " + i).start();
        }
    }
}
