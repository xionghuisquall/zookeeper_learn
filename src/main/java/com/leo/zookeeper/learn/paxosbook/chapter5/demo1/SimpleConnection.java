package com.leo.zookeeper.learn.paxosbook.chapter5.demo1;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class SimpleConnection implements Watcher {
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void connect() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, this);

        System.out.println("zookeeper state = " + zooKeeper.getState());
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("connected to zookeeper");
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("get zookeeper state = " + watchedEvent.getState());
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            // client 连上zookeeper服务端后，服务端会发送一个SyncConnected事件，此时客户端才算真正建链成功
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args) {
        SimpleConnection sc = new SimpleConnection();

        try {
            sc.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
