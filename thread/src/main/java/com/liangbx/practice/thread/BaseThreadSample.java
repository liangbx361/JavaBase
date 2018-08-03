package com.liangbx.practice.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程的三种方式介绍
 * 1. 集成Thread
 * 2. 实现Runnable
 * 3. 实现Callable
 */
public class BaseThreadSample {

    static class A extends Thread {

        public A(String name) {
            super(name);
        }

        @Override
        public void run() {
            work();
        }
    }

    static class B implements Runnable {

        @Override
        public void run() {
            work();
        }
    }

    static class C implements Callable<String> {

        @Override
        public String call() throws Exception {
            return work();
        }
    }

    static String work() {
        for(int i=0; i<10; i++) {
            System.out.println(Thread.currentThread().getName() + " work --> " + i);
        }

        return "success";
    }

    public static void main(String[] args) {
        A a = new A("A");
        B b = new B();
        C c = new C();
        FutureTask<String> cTask = new FutureTask<>(c);

        new Thread(cTask, "C").start();
        a.start();
        new Thread(b, "B").start();

        try {
            // 阻塞该线程，等待C执行完毕
            System.out.println(cTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
