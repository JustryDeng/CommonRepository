package com.pingan.redisutil;

import com.pingan.redisutil.util.RedisUtil;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ScanOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Hash相关的操作测试
 *
 * 注意: 本人在最后, 对RedisUtil中的日志记录格式进行了统一， 所以
 *      小伙伴们在测试时， 输出的日志的格式，可能与此测试类代码行间上注释的日志, 格式不符; 但
 *      也只是格式不符合而已, 并不影响理解。
 *
 * @author JustryDeng
 * @date 2020/3/8 14:43:11
 */
@SpringBootTest
class HashOpsTests {

    @Test
    void hPutTest() {
        // hPut(...) => key -> ds, entryKey -> name, entryValue -> 邓沙利文
        RedisUtil.HashOps.hPut("ds", "name", "邓沙利文");
        // hGetAll(...) => key -> ds, result -> {name=邓沙利文}
        RedisUtil.HashOps.hGetAll("ds");

        // hPut(...) => key -> ds, entryKey -> name, entryValue -> 邓二洋
        RedisUtil.HashOps.hPut("ds", "name", "邓二洋");
        // hGetAll(...) => key -> ds, result -> {name=邓二洋}
        RedisUtil.HashOps.hGetAll("ds");
    }

    @Test
    void hPutAllTest() {
        /// prepare data
        Map<String, String> map = new HashMap<>(4);
        map.put("entryKey1", "entryValue111");
        map.put("entryKey2", "entryValue222");
        map.put("entryKey3", "entryValue333");
        /// test
        // hPutAll(...) => key -> ds, maps -> {entryKey2=entryValue2, entryKey1=entryValue1, entryKey3=entryValue3}
        RedisUtil.HashOps.hPutAll("ds", map);
    }

    @Test
    void hPutIfAbsentTest() {
        /// prepare data
        RedisUtil.HashOps.hPut("ds", "name", "邓沙利文");
        /// test
        // hPutIfAbsent(...) => key -> ds, entryKey -> name, entryValue -> JustryDeng, result -> false
        RedisUtil.HashOps.hPutIfAbsent("ds", "name", "JustryDeng");
        // hPutIfAbsent(...) => key -> ds, entryKey -> gender, entryValue -> 男, result -> true
        RedisUtil.HashOps.hPutIfAbsent("ds", "gender", "男");
    }

    @Test
    void hGetTest() {
        /// prepare data
        RedisUtil.HashOps.hPut("ds", "name", "邓沙利文");
        /// test
        // hGet(...) => key -> ds, got entryValue [邓沙利文] by entryKey [name]
        RedisUtil.HashOps.hGet("ds", "name");
        // hGet(...) => key -> ds, got entryValue [null] by entryKey [non-exist-entryKey]
        RedisUtil.HashOps.hGet("ds", "non-exist-entryKey");
        // hGet(...) => key -> non-exist-key, got entryValue [null] by entryKey [any]
        RedisUtil.HashOps.hGet("non-exist-key", "any");
    }

    @Test
    void hGetAllTest() {
        /// prepare data
        RedisUtil.HashOps.hPut("ds", "name", "邓沙利文");
        RedisUtil.HashOps.hPut("ds", "motto", "我是一只小小小小鸟~");
        /// test
        // get hash result [{name=邓沙利文, motto=我是一只小小小小鸟~}] by key [ds]
        RedisUtil.HashOps.hGetAll("ds");
        // hGetAll(...) => get hash result [{}] by key [non-exist-key]
        RedisUtil.HashOps.hGetAll("non-exist-key");
    }

    @Test
    void hMultiGetTest() {
        /// prepare data
        RedisUtil.HashOps.hPut("ds", "name", "邓沙利文");
        RedisUtil.HashOps.hPut("ds", "birthday", "1994-02-05");
        /// test
        // hMultiGet(...) => key -> ds, entryKeys -> [name, motto, birthday], entryValues -> [邓沙利文, null, 1994-02-05]
        RedisUtil.HashOps.hMultiGet("ds", Lists.newArrayList("name", "motto", "birthday"));
        // hMultiGet(...) => key -> no-exist-key, entryKeys -> [name, motto, birthday], entryValues -> [null, null, null]
        RedisUtil.HashOps.hMultiGet("no-exist-key", Lists.newArrayList("name", "motto", "birthday"));
    }

    @Test
    void hDeleteTest() {
        /// prepare data
        RedisUtil.HashOps.hPut("ds", "name", "邓沙利文");
        RedisUtil.HashOps.hPut("ds", "birthday", "1994-02-05");
        RedisUtil.HashOps.hPut("ds", "hobby", "女");
        /// test
        // key -> ds, entryKeys -> [name, birthday, hobby, non-exist-entryKey], count -> 3
        RedisUtil.HashOps.hDelete("ds", "name", "birthday", "hobby", "non-exist-entryKey");
        // hDelete(...) => key -> non-exist-key, entryKeys -> [any], count -> 0
        RedisUtil.HashOps.hDelete("non-exist-key", "any");

        RedisUtil.HashOps.hPut("jd", "name", "JustryDeng");
        // hasKey(...) => key -> jd  value -> true
        RedisUtil.KeyOps.hasKey("jd");
        // hDelete(...) => key -> jd, entryKeys -> [name], count -> 1
        RedisUtil.HashOps.hDelete("jd", "name");
        // hasKey(...) => key -> jd  value -> false
        RedisUtil.KeyOps.hasKey("jd");
    }

    @Test
    void hExistsTest() {
        /// prepare data
        RedisUtil.HashOps.hPut("ds", "name", "邓沙利文");
        /// test
        // hDelete(...) => key -> ds, entryKeys -> name, exist -> true
        RedisUtil.HashOps.hExists("ds", "name");
        // hDelete(...) => key -> ds, entryKeys -> non-exist-entryKey, exist -> false
        RedisUtil.HashOps.hExists("ds", "non-exist-entryKey");
        // hDelete(...) => key -> non-exist-key, entryKeys -> any, exist -> false
        RedisUtil.HashOps.hExists("non-exist-key", "any");
    }

    @Test
    void hIncrByTest() {
        /// prepare data
        RedisUtil.HashOps.hPut("ds", "age", "26");
        RedisUtil.HashOps.hPut("ds", "name", "邓沙利文");
        /// test
        // hIncrBy(...) => key -> ds, entryKey -> age, increment -> 40, result -> 66
        RedisUtil.HashOps.hIncrBy("ds", "age", 40);
        // hIncrBy(...) => key -> ds, entryKey -> non-exist-entryKey, increment -> 40, result -> 40
        RedisUtil.HashOps.hIncrBy("ds", "non-exist-entryKey", 40);
        // hIncrBy(...) => key -> non-exist-key, entryKey -> any, increment -> 40, result -> 40
        RedisUtil.HashOps.hIncrBy("non-exist-key", "any", 40);

        // org.springframework.data.redis.RedisSystemException
        try {
            RedisUtil.HashOps.hIncrBy("ds", "name", 40);
        } catch (Exception e) {
            System.err.println(e.getClass().getName());
        }
    }

    @Test
    void hIncrByFloatTest() {
        /// prepare data
        RedisUtil.HashOps.hPut("ds", "age", "123");
        RedisUtil.HashOps.hPut("ds", "name", "邓沙利文");
        /// test
        // key -> ds, entryKey -> age, increment -> 100.6, result -> 223.6
        RedisUtil.HashOps.hIncrByFloat("ds", "age", 100.6);
        // hIncrByFloat(...) => key -> ds, entryKey -> non-exist-entryKey, increment -> -100.6, result -> -100.6
        RedisUtil.HashOps.hIncrByFloat("ds", "non-exist-entryKey", -100.6);
        // hIncrByFloat(...) => key -> non-exist-key, entryKey -> any, increment -> 100.6, result -> 100.6
        RedisUtil.HashOps.hIncrByFloat("non-exist-key", "any", 100.6);

        // org.springframework.data.redis.RedisSystemException
        try {
            RedisUtil.HashOps.hIncrByFloat("ds", "name", 6.66);
        } catch (Exception e) {
            System.err.println(e.getClass().getName());
        }
    }

    @Test
    void hKeysTest() {
        /// prepare data
        RedisUtil.HashOps.hPut("ds", "name", "邓沙利文");
        RedisUtil.HashOps.hPut("ds", "age", "62");
        /// test
        // key -> ds, entryKeys -> [name, age]
        RedisUtil.HashOps.hKeys("ds");
        // hKeys(...) => key -> no-exist-key, entryKeys -> []
        RedisUtil.HashOps.hKeys("no-exist-key");
    }

    @Test
    void hValuesTest() {
        /// prepare data
        RedisUtil.HashOps.hPut("ds", "name", "邓沙利文");
        RedisUtil.HashOps.hPut("ds", "age", "62");
        /// test
        // hValues(...) => key -> ds, entryValues -> [邓沙利文, 62]
        RedisUtil.HashOps.hValues("ds");
        // hValues(...) => key -> no-exist-key, entryValues -> []
        RedisUtil.HashOps.hValues("no-exist-key");
    }

    @Test
    void hSizeTest() {
        /// prepare data
        RedisUtil.HashOps.hPut("ds", "name", "邓沙利文");
        RedisUtil.HashOps.hPut("ds", "age", "62");
        /// test
        // hSize(...) => key -> ds, count -> 2
        RedisUtil.HashOps.hSize("ds");
        // key -> no-exist-key, count -> 0
        RedisUtil.HashOps.hSize("no-exist-key");
    }

    @Test
    void hScanTest() {
        /// prepare data
        RedisUtil.HashOps.hPut("ds", "ne", "v0");
        RedisUtil.HashOps.hPut("ds", "name", "v1");
        RedisUtil.HashOps.hPut("ds", "name123", "v2");
        RedisUtil.HashOps.hPut("ds", "name456", "v3");
        RedisUtil.HashOps.hPut("ds", "nameAbc", "v4");
        RedisUtil.HashOps.hPut("ds", "nameXyz", "v5");
        /// test
        // hScan(...) => key -> ds, options -> {}, cursor -> [{"ne":"v0"},{"name":"v1"},{"name123":"v2"},{"name456":"v3"},{"nameAbc":"v4"},{"nameXyz":"v5"}]
        RedisUtil.HashOps.hScan("ds", ScanOptions.NONE);
        // hScan(...) => key -> ds, options -> {"pattern":"name*"}, cursor -> [{"name":"v1"},{"name123":"v2"},{"name456":"v3"},{"nameAbc":"v4"},{"nameXyz":"v5"}]
        RedisUtil.HashOps.hScan("ds", ScanOptions.scanOptions().match("name*").build());
        // hScan(...) => key -> ds, options -> {"pattern":"*a*"}, cursor -> [{"name":"v1"},{"name123":"v2"},{"name456":"v3"},{"nameAbc":"v4"},{"nameXyz":"v5"}]
        RedisUtil.HashOps.hScan("ds", ScanOptions.scanOptions().match("*a*").build());
        // hScan(...) => key -> ds, options -> {"pattern":"n??e"}, cursor -> [{"name":"v1"}]
        RedisUtil.HashOps.hScan("ds", ScanOptions.scanOptions().match("n??e").build());
    }
}