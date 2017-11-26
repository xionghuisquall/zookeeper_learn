package com.leo.zookeeper.learn.demo1;

import java.util.Date;

public class DummyProcess {
    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                System.out.println("running:" + new Date());

                try {
                    Thread.sleep(5000);
                } catch (Exception e) {

                }
            }
        }).start();
    }
}
