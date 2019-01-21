package com.examples.demo.common.utils;

import java.util.concurrent.TimeUnit;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZookeeperLockHelper {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperLockHelper.class);

    private CuratorFramework client = null;


    private String lockPrefix;
    private String zookeeperAddress;
    private int retryInterval;
    private int maxRetryCount;
    private int lockTimeWait;

    public ZookeeperLockHelper(String lockPrefix, String zookeeperAddress, int retryInterval, int maxRetryCount, int lockTimeWait) {
        this.lockPrefix = lockPrefix;
        this.zookeeperAddress = zookeeperAddress;
        this.retryInterval = retryInterval;
        this.maxRetryCount = maxRetryCount;
        this.lockTimeWait = lockTimeWait;

        logger.info("==============初始化zookeeper客户端开始==============");
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(this.retryInterval, this.maxRetryCount);
        client = CuratorFrameworkFactory.newClient(this.zookeeperAddress, retryPolicy);
        client.start();
        logger.info("==============初始化zookeeper客户端结束==============");
    }

    /**
     * 创建节点
     *
     * @param key
     * @return
     */
    public InterProcessMutex createPath(String key) {
        try {
            Long start = System.currentTimeMillis();
            InterProcessMutex lock = new InterProcessMutex(client, this.lockPrefix.concat("/").concat(key));
            logger.info("节点createPath成功，key：" + key + ",耗时:" + (System.currentTimeMillis() - start));
            return lock;
        } catch (Exception e) {
            logger.error("orderId:" + key + "创建节点路径出现异常", e);
            return null;
        }
    }

    /**
     * 获取zk锁
     *
     * @param lock
     * @return true 获取到锁
     */
    public boolean lockAcquired(InterProcessMutex lock) {
        try {
            if (lock.acquire(this.lockTimeWait, TimeUnit.MILLISECONDS)) {
                return true;
            }
        } catch (Exception e) {
            logger.error("获取zk锁出现异常", e);
        }
        return false;
    }

    /**
     * 释放zk锁
     */
    public void lockRelease(InterProcessMutex lock, String orderId) {
        Long start = System.currentTimeMillis();
        try {
            if (null != lock) {
                lock.release();
                StringBuilder nodePath = new StringBuilder();
                nodePath.append(this.lockPrefix).append("/").append(orderId);
                if (null != client && null != client.checkExists().forPath(nodePath.toString())) {
                    client.delete().forPath(nodePath.toString());
                    logger.info("删除节点成功，orderId：" + orderId + ",耗时:" + (System.currentTimeMillis() - start));
                }
            }
        } catch (KeeperException e) {
            logger.error("orderId:" + orderId + "忽略删除节点异常", e);
        } catch (Exception e) {
            logger.error("orderId:" + orderId + "忽略删除ZK锁节点异常", e);
        }
    }
}
