package com.sxt.zookeeper;
import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ZookeeperClientTest {
    private final Logger logger = Logger.getLogger(ZookeeperClientTest.class);

    //服务器地址
    private static final String ZK_ADDRESS="192.168.1.133:2181";

    //连接超时时间 (30s)
    private static final int SESSION_TIMEOUT = 30*1000;

    private static   ZooKeeper zooKeeper;

    private static final String ZK_NODE="/zk‐node";

    @Before
    public void init() throws IOException, InterruptedException {
        final CountDownLatch countDownLatch=new CountDownLatch(1);

        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected && event.getType()== Watcher.Event.EventType.None){
                    countDownLatch.countDown();
                    logger.info("连接成功!");
                }
            }
        };
        zooKeeper = new ZooKeeper(ZK_ADDRESS,SESSION_TIMEOUT,watcher);
        logger.info("连接中....");
        countDownLatch.await();
    }

    /**
     * 测试创建路径
     * @throws InterruptedException
     */
    @Test
    public void createAsycTest() throws InterruptedException {
        zooKeeper.create(ZK_NODE,"data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT,(rc, path, ctx, name)->
                        logger.info("rc {"+rc+"},path {"+path+"},ctx {"+ctx+"},name {"+name+"}"),
                "context");

        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void testGetData(){
        //1、创建watche
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (ZK_NODE.equals(event.getPath())&&event.getType() ==
                Event.EventType.NodeDataChanged){
                    logger.info(" PATH: {"+ZK_NODE+"}  发现变化");
                    try {
                        byte[] data = zooKeeper.getData(ZK_NODE, this, null);
                        logger.info(" data: {"+new String(data)+"}");
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        try {
            byte[] data = zooKeeper.getData(ZK_NODE, watcher, null);  //
            logger.info(" data: {"+new String(data)+"}");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void  test(){
        try {
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static ZooKeeper getZooKeeper() {
        return zooKeeper;
    }
}
