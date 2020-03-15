package com.pingan.redisutil;

import com.pingan.redisutil.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Lock相关的操作测试
 *
 * @author JustryDeng
 * @date 2020/3/15 18:17:26
 */
@SpringBootTest
class LockOpsTests {

    /**
     * 获取锁 / 释放锁 基本测试
     */
    @Test
    void releaseLockTest() {
        String lockName = "lock:name:abc";
        String lockValue = UUID.randomUUID().toString();
        boolean lock = false;
        try {
            /*
             * releaseLock(...) => key -> lock:name:abc, lockValue -> c185f6bc-cda8-462b-9c33-e2fcae5a0223
             * releaseLock(...) => result -> true
             */
            lock = RedisUtil.LockOps.getLock(lockName, lockValue);
        } finally {

            if (lock) {
                /*
                 * releaseLock(...) => key -> lock:name:abc, lockValue -> c185f6bc-cda8-462b-9c33-e2fcae5a0223
                 * releaseLock(...) => result -> true
                 */
                RedisUtil.LockOps.releaseLock(lockName, lockValue);
            }
        }
    }


    /**
     * 注意: 这里测试时, 主要是为了测试getLockUntilTimeout方法获取锁超时的情景, 所以就没有释放锁。
     *      在实际使用时， 获取锁与释放锁一般都是成对出现的; 有获取就有释放。
     */
    @Test
    void getLockUntilTimeoutTest() {
        final String lockName = "lock:name:xyz";
        final String lockValue = UUID.randomUUID().toString();

        // 先让别人获取锁， 暂不释放, 同时设置锁的最大存活时长大一点 (以便测试下面获取锁超时)
        RedisUtil.LockOps.getLock(lockName, lockValue, 300, TimeUnit.SECONDS);

        // 再次尝试获取锁
        /*
         * getLockUntilTimeout(...) => key -> lock:name:xyz, value -> a6834431-43d3-4782-9eb1-42ae2ac3b248, timeout -> 2, unit -> SECONDS, retryTimeoutLimit -> 5000ms
         * getLockUntilTimeout(...) => consume time -> 5033ms, result -> true
         */
        RedisUtil.LockOps.getLockUntilTimeout(lockName, lockValue, 2, TimeUnit.SECONDS, 5000);
    }
}