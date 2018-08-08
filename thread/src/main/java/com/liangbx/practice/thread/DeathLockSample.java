package com.liangbx.practice.thread;

/**
 * 死锁
 * 简单描述：占用对方的锁，而造成的无限等待
 * 通常引发问题的原因是锁执行的顺序不一致而引发的
 */
public class DeathLockSample {

    public static class Worker {

        final Object lock1 = new Object();
        final Object lock2 = new Object();

        public void doA() {
            synchronized (lock1) {
                System.out.println("Lock1 to do");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("Lock2 to do");
                }
            }
            System.out.println("doA: complete");
        }

        public void doB() {
            synchronized (lock2) {
                System.out.println("Lock2 to do");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("Lock1 to do");
                }
            }

            System.out.println("doB: complete");
        }
    }

    public static void main(String[] args) {

        final Worker worker = new Worker();

        new Thread(new Runnable() {
            @Override
            public void run() {
                worker.doA();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                worker.doB();
            }
        }).start();
    }
}
