package com.liangbx.practice.rxjava;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SchedulersSample {

    /**
     * subscribeOn：指定订阅动作执行所在的线程
     * observeOn：指定后面操作符执行所在的线程
     */
    public static void main(String[] args) {

                Observable.create(emitter -> {
                    // 订阅发生的线程由subscribeOn指定
                    System.out.println("subscribe --> " + Thread.currentThread().getName());
                    emitter.onNext("s");
                })
                // 指定订阅发生所在的线程
                .subscribeOn(Schedulers.newThread())
                // 指定后面操作符使用的线程
                .observeOn(Schedulers.io())
                .doOnNext(s -> {
                    System.out.println("doOnNext --> " + Thread.currentThread().getName());
                })
                .map(s -> {
                    System.out.println("map --> " + Thread.currentThread().getName());
                    return s + "s";
                })
                .observeOn(Schedulers.computation())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // 发生订阅动作的线程
                        System.out.println("onSubscribe --> " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("onNext -->" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError -->" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete -->" + Thread.currentThread().getName());
                    }
                });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
