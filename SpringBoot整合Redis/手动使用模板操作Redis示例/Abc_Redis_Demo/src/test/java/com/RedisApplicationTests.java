package com;

import com.alibaba.fastjson.JSON;
import com.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    /**
     * 主动将对象转换为json字符串，在使用StringRedisTemplate模板进行存储与读取
     *
     * @date 2019/4/11 15:00
     */
    @Test
    public void originStringRedisTemplateTest() {
        User user = User.builder().name("咿呀咔咔").age(25).motto("我是一只小小小小鸟~").gender("男").build();
        // 将要放入缓存的对象先转换为JSON字符串
        String jsonStr = com.alibaba.fastjson.JSON.toJSONString(user);
        // 放入Redis
        stringRedisTemplate.opsForValue().set("ds-key", jsonStr);
        // 从Redis中获取
        String getstr = stringRedisTemplate.opsForValue().get("ds-key");
        // 将获取到的字符串转换为对应的对象
        // 注意:JSON.parseObject此方法进行字符串对象转换时，依赖于实体模型的构造方法;如果
        //      使用了lombok的@Builder注解，那么最好在补一个全餐构造，否则此步骤可能出现异常
        User getUser = JSON.parseObject(getstr, User.class);
        System.out.println(getUser);
    }

    /**
     * 测试使用 自定义注入的RedisTemplate模板, value为对象
     *
     * @date 2019/4/11 15:00
     */
    @Test
    public void myRedisTemplateTest() {
        User user = User.builder().name("邓二洋").age(25).motto("我是座右铭").gender("男").build();
        redisTemplate.opsForValue().set("b-user-key", user);
        Object obj = redisTemplate.opsForValue().get("b-user-key");
        // 控制台输出: class java.util.LinkedHashMap
        System.out.println(obj == null ? null : obj.getClass());
        // 控制台输出: {name=邓二洋, age=25, gender=男, motto=我是座右铭}
        System.out.println(obj);
    }

    /**
     * 一些常用方法示例
     *
     * @date 2019/4/12 0:19
     */
    public void someNormalMethod() {

        // 向redis里存入数据
        stringRedisTemplate.opsForValue().set("key", "value");

        // 向redis里存入数据并设置缓存时间
        stringRedisTemplate.opsForValue().set("key", "value", 60 * 10, TimeUnit.SECONDS);

        // value做-1操作
        stringRedisTemplate.boundValueOps("key").increment(-1);

        // value做+1操作
        stringRedisTemplate.boundValueOps("key").increment(1);

        // 根据key获取缓存中的value
        stringRedisTemplate.opsForValue().get("key");

        // 根据key获取过期时间
        stringRedisTemplate.getExpire("key");

        // 根据key获取过期时间并换算成指定单位
        stringRedisTemplate.getExpire("key", TimeUnit.SECONDS);

        // 根据key删除缓存
        stringRedisTemplate.delete("key");

        // 检查key是否存在，返回boolean值
        stringRedisTemplate.hasKey("key");

        // 向指定key中存放set集合
        stringRedisTemplate.opsForSet().add("key", "obj1", "obj2", "obj3");

        // 设置过期时间
        stringRedisTemplate.expire("key", 1000, TimeUnit.MILLISECONDS);

        // 根据key查看集合中是否存在指定数据
        stringRedisTemplate.opsForSet().isMember("key", "obj");

        // 根据key获取set集合
        stringRedisTemplate.opsForSet().members("key");
    }
}




