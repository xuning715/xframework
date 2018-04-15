package com.x.framework.bootstrap;

import java.util.stream.IntStream;

/**
 * Created by x on 2018/4/13.
 */
public class ShutdownHook {
    public static void main(String[] args) throws InterruptedException {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("exit");
        }));
        IntStream.range(0, 10).forEach(i -> {
            System.out.println("going");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
