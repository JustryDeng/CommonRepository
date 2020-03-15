package com.pingan.redisutil;

import com.pingan.redisutil.util.RedisUtil;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Key相关的操作测试
 *
 * 注意: 本人在最后, 对RedisUtil中的日志记录格式进行了统一， 所以
 *      小伙伴们在测试时， 输出的日志的格式，可能与此测试类代码行间上注释的日志, 格式不符; 但
 *      也只是格式不符合而已, 并不影响理解。
 *
 * @author JustryDeng
 * @date 2020/3/5 14:28:39
 */
@SpringBootTest
class KeyOpsTests {

    @Test
    void deleteTest() {
        // prepare data
        RedisUtil.StringOps.set("ds", "邓沙利文");
        // test
        RedisUtil.KeyOps.delete("ds");
    }

    @Test
    void batchDeleteTest() {
        // prepare data
        RedisUtil.StringOps.set("ds1", "邓沙利文");
        RedisUtil.StringOps.set("ds2", "邓二洋");
        RedisUtil.StringOps.set("ds3", "JustryDeng");
        RedisUtil.StringOps.set("ds4", "dengshuai");
        // test
        RedisUtil.KeyOps.delete(
                Lists.newArrayList("ds1", "ds2", "ds3", "ds4")
        );
    }

    @Test
    void dumpAndRestoreTest() {
        /// prepare data
        RedisUtil.StringOps.set("key-abc", "hello dengshuai~");
        RedisUtil.StringOps.set("12345", "上山打老虎~");

        /// test  dump
        byte[] serializedValue = RedisUtil.KeyOps.dump("key-abc");

        /// test  restore
        // redis中会出现新的key-value, ("new-key", "hello dengshuai~"), 并在60秒后过期
        RedisUtil.KeyOps.restore("new-key", serializedValue, 60, TimeUnit.SECONDS);
        //
        try {
            RedisUtil.KeyOps.restore("12345", serializedValue, 60, TimeUnit.SECONDS);
        } catch (Exception e) {
            // 会出现RedisSystemException异常。 输出: org.springframework.data.redis.RedisSystemException
            System.err.println(e.getClass().getName());
        }
        // redis中原来的key-value ("12345", "上山打老虎~")会被替换为("12345", "hello dengshuai~"), 且该key-value会在60秒后过期
        RedisUtil.KeyOps.restore("12345", serializedValue, 60, TimeUnit.SECONDS, true);
    }

    @Test
    void hasKeyTest() {
        /// prepare data
        RedisUtil.StringOps.set("ds", "hello JustryDeng~");
        /// test
        RedisUtil.KeyOps.hasKey("ds");
        RedisUtil.KeyOps.hasKey("ds123");
    }

    @Test
    void expireAndExpireAtTest() {
        /// prepare data
        RedisUtil.StringOps.set("jd", "hello JustryDeng~");
        RedisUtil.StringOps.set("ds", "hello dengshuai~");

        /// test expire
        // expire(...) => key [jd], timeout -> 100, unit -> SECONDS, result is -> true
        RedisUtil.KeyOps.expire("jd", 100, TimeUnit.SECONDS);
        // expire(...) => key [no-exist-key], timeout -> 100, unit -> SECONDS, result is -> false
        RedisUtil.KeyOps.expire("no-exist-key", 100, TimeUnit.SECONDS);

        /// test expireAt
        long nowTimestamp = new Date().getTime();
        long sixtySeconds = 60 * 1000;
        Date sixtySecondsDate = new Date(nowTimestamp + sixtySeconds);
        // expireAt(...) => key [ds], date -> Sat Mar 14 20:39:50 CST 2020, result is -> true
        RedisUtil.KeyOps.expireAt("ds", sixtySecondsDate);
        // expireAt(...) => key [no-exist-key], date -> Sat Mar 14 20:40:58 CST 2020, result is -> false
        RedisUtil.KeyOps.expireAt("no-exist-key", sixtySecondsDate);
    }

    /**
     * 慎用keys方法！ 该方法性能较低，若redis中键值对较多， 则该方法可能耗时较长。
     */
    @Test
    void keysTest() {
        /// prepare data
        RedisUtil.StringOps.set("key-abc", "hello dengshuai~");
        RedisUtil.StringOps.set("d", "1~");
        RedisUtil.StringOps.set("ds", "2~");
        RedisUtil.StringOps.set("ds123", "3~");
        RedisUtil.StringOps.set("ds456", "4~");

        /// test
        // find match pattern [d*] keys -> [ds456, ds, ds123, d]
        RedisUtil.KeyOps.keys("d*");
        // find match pattern [*s*] keys -> [ds456, ds, ds123]
        RedisUtil.KeyOps.keys("*s*");
        // find match pattern [*s*3*] keys -> [ds123]
        RedisUtil.KeyOps.keys("*s*3*");
        // 全模糊， 如: find match pattern [*] keys -> [key-abc, ds, ds123, ds456]
        RedisUtil.KeyOps.keys("*");
        // find match pattern [d?] keys -> [ds]
        RedisUtil.KeyOps.keys("d?");
    }

    @Test
    void moveTest() {
        /// prepare data
        RedisUtil.StringOps.set("ds", "hello JustryDeng~");
        /// test
        RedisUtil.KeyOps.move("ds", 1);
    }

    @Test
    void persistTest() {
        /// prepare data
        RedisUtil.StringOps.set("jd", "hello JustryDeng~");
        RedisUtil.StringOps.setEx("ds", "hello dengshuai~", 60, TimeUnit.SECONDS);
        /// test
        // persist key[jd] corresponding key-value, result -> false
        RedisUtil.KeyOps.persist("jd");
        // persist key[ds] corresponding key-value, result -> true
        RedisUtil.KeyOps.persist("ds");
        // persist key[no-exist-key] corresponding key-value, result -> false
        RedisUtil.KeyOps.persist("no-exist-key");
    }

    @Test
    void getExpireTest() {
        /// prepare data
        RedisUtil.StringOps.set("jd", "hello JustryDeng~");
        RedisUtil.StringOps.setEx("ey", "hello dengshuai~", 500, TimeUnit.MILLISECONDS);
        RedisUtil.StringOps.setEx("ds", "hello dengshuai~", 1001, TimeUnit.MILLISECONDS);
        RedisUtil.StringOps.setEx("foo", "hello dengshuai~", 1900, TimeUnit.MILLISECONDS);
        RedisUtil.StringOps.setEx("oop", "hello dengshuai~", 2000, TimeUnit.MILLISECONDS);
        /// test getExpire
        // key[jd] corresponding key-value, timeout is -> -1 SECONDS
        RedisUtil.KeyOps.getExpire("jd");
        // key[ey] corresponding key-value, timeout is -> 0 SECONDS
        RedisUtil.KeyOps.getExpire("ey");
        // key[ds] corresponding key-value, timeout is -> 1 SECONDS
        RedisUtil.KeyOps.getExpire("ds");
        // key[foo] corresponding key-value, timeout is -> 2 SECONDS
        RedisUtil.KeyOps.getExpire("foo");
        // key[oop] corresponding key-value, timeout is -> 2 SECONDS
        RedisUtil.KeyOps.getExpire("oop");
        // key[no-exist-key] corresponding key-value, timeout is -> -2 SECONDS
        RedisUtil.KeyOps.getExpire("no-exist-key");

        /// test getExpire
        // key[jd] corresponding key-value, timeout is -> -1 MILLISECONDS
        RedisUtil.KeyOps.getExpire("jd", TimeUnit.MILLISECONDS);
        // key[ey] corresponding key-value, timeout is -> -2 MILLISECONDS
        RedisUtil.KeyOps.getExpire("ey", TimeUnit.MILLISECONDS);
        // key[ds] corresponding key-value, timeout is -> 170 MILLISECONDS
        RedisUtil.KeyOps.getExpire("ds", TimeUnit.MILLISECONDS);
        // key[foo] corresponding key-value, timeout is -> 1070 MILLISECONDS
        RedisUtil.KeyOps.getExpire("foo", TimeUnit.MILLISECONDS);
        // key[oop] corresponding key-value, timeout is -> 1177 MILLISECONDS
        RedisUtil.KeyOps.getExpire("oop", TimeUnit.MILLISECONDS);
        // key[no-exist-key] corresponding key-value, timeout is -> -2 MILLISECONDS
        RedisUtil.KeyOps.getExpire("no-exist-key", TimeUnit.MILLISECONDS);
    }

    @Test
    void randomKeyTest() {
        RedisUtil.KeyOps.randomKey();
    }

    @Test
    void renameTest() {
        /// prepare data
        RedisUtil.StringOps.set("abc", "hello JustryDeng~");
        RedisUtil.StringOps.set("9527", "你好 华安~");
        RedisUtil.StringOps.set("8888", "你好 8888~");
        /// test
        RedisUtil.KeyOps.rename("abc", "xyz");

        try {
            RedisUtil.KeyOps.rename("qwer", "xxx");
        } catch (Exception e) {
            /* 若oldKey不存在，则抛出org.springframework.data.redis.RedisSystemException:
             *                         Error in execution; nested exception is
             *                         io.lettuce.core.RedisCommandExecutionException:
             *                             ERR no such key
             */
            System.out.println(e.getClass().getName());
        }
        RedisUtil.KeyOps.rename("8888", "9527");

    }

    @Test
    void renameIfAbsentTest() {
        /// prepare data
        RedisUtil.StringOps.set("abc", "hello JustryDeng~");
        RedisUtil.StringOps.set("9527", "你好 华安~");
        RedisUtil.StringOps.set("8888", "你好 8888~");
        /// test
        RedisUtil.KeyOps.renameIfAbsent("abc", "xyz");

        try {
            RedisUtil.KeyOps.renameIfAbsent("qwer", "xxx");
        } catch (Exception e) {
            /* 若oldKey不存在，则抛出org.springframework.data.redis.RedisSystemException:
             *                         Error in execution; nested exception is
             *                         io.lettuce.core.RedisCommandExecutionException:
             *                             ERR no such key
             */
            System.out.println(e.getClass().getName());
        }
        RedisUtil.KeyOps.renameIfAbsent("8888", "9527");
    }

    @Test
    void typeTest() {
        /// prepare data
        RedisUtil.StringOps.set("ds", "hello JustryDeng~");
        /// test
        // key [ds] corresponding value DataType is -> STRING
        RedisUtil.KeyOps.type("ds");
        RedisUtil.KeyOps.type("no-exist-key");
    }
}