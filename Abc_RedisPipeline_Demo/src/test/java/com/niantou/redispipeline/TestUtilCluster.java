package com.niantou.redispipeline;

import com.niantou.redispipeline.author.JustryDeng;
import com.niantou.redispipeline.env.RedisClusterEnvSupport;
import com.niantou.redispipeline.util.RedisPipelineUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StopWatch;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * 测试工具类RedisPipelineUtil对集群redis的pipeline操作
 *
 * @author {@link JustryDeng}
 * @since 2020/11/25 19:17:52
 */
@Slf4j
@SpringBootTest
public class TestUtilCluster extends RedisClusterEnvSupport {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    private static final int mount =  10000;
    
    /**
     * 简单测试使用RedisPipelineUtil的集群支持功能 - 简单的pipeline (value)操作
     */
    @Test
    void testOne() {
        StopWatch stopWatch = new StopWatch("普通操作与pipeline操作耗时对比");

        // ********************** 普通操作 **********************
        stopWatch.start("普通操作存" + mount + "条数据");
        for (int i = 0; i < mount; i++) {
            redisTemplate.opsForValue().set("key" + i, "key" + i + "'s value");
        }
        stopWatch.stop();

        stopWatch.start("普通操作取" + mount + "条数据");
        for (int i = 0; i < mount; i++) {
            System.out.println("普通操作\t" + redisTemplate.opsForValue().get("key" + i));
        }
        stopWatch.stop();

        // ********************** pipeline操作 **********************
        stopWatch.start("pipeline存" + mount + "条数据");
        BiFunction<Pipeline, RedisPipelineUtil.PipelineParamSupplier<String>, Response<String>> biFunction4Set = (pipeline, supplier) -> {
            String param = supplier.getParam();
            // 如果对参数有额外的逻辑， 可以在这里do something
            // ...
            // 这里value自己序列化吧
            byte[] redisValue = RedisPipelineUtil.getValueSerializer().serialize(param);
            return pipeline.set(supplier.getRedisKey(), redisValue);
        };
        
        List<String> paramList4Set = new ArrayList<>(16);
        for (int i = 0; i < mount; i++) {
            paramList4Set.add("pipeline-key" + i);
        }
        List<String> resultList4Set = RedisPipelineUtil.pipeline4ClusterSimpleStr(biFunction4Set, paramList4Set);
        resultList4Set.forEach(value ->System.out.println("pipeline set操作\t" + value));
        stopWatch.stop();

        stopWatch.start("pipeline取" + mount + "条数据");
        BiFunction<Pipeline, RedisPipelineUtil.PipelineParamSupplier<String>, Response<byte[]>> biFunction4Get = (pipeline, supplier) -> pipeline.get(supplier.getRedisKey());
        List<String> paramList4Get = new ArrayList<>(paramList4Set);
        List<byte[]> resultList = RedisPipelineUtil.pipeline4ClusterSimpleStr(biFunction4Get, paramList4Get);
        // 这里value自己返序列化吧(注意: 使用的序列化器要和 存时使用的序列化器对应)
        List<String> resultList4Get = resultList.stream()
                .map(x -> String.valueOf(RedisPipelineUtil.getValueSerializer().deserialize(x)))
                .collect(Collectors.toList());
        resultList4Get.forEach(value ->System.out.println("pipeline get操作\t" + value));
        stopWatch.stop();
    
        System.err.println(stopWatch.prettyPrint());
        System.err.println("[验证pipeline是否存在操作丢失] 实际pipeline存了" + resultList4Set.size() + "条");
        System.err.println("[验证pipeline是否存在操作丢失] 实际pipeline取了" + resultList4Get.size() + "条");
    }
    
    /**
     * 测试使用RedisPipelineUtil的集群支持功能 - 稍微复杂一点的pipeline (hash)操作
     */
    @Test
    void testTwo() {
        final String redisKey = "JustryDeng";
        StopWatch stopWatch = new StopWatch("普通操作与pipeline操作耗时对比");
        
        // ********************** 普通操作 **********************
        stopWatch.start("普通操作存" + mount + "条数据");
        for (int i = 0; i < mount; i++) {
            redisTemplate.opsForHash().put(redisKey, "hashKey" + i, "hashValue" + i);
        }
        stopWatch.stop();
        
        stopWatch.start("普通操作取" + mount + "条数据");
        for (int i = 0; i < mount; i++) {
            System.out.println("普通操作\t" + redisTemplate.opsForHash().get(redisKey, "hashKey" + i));
        }
        stopWatch.stop();
        
        // ********************** pipeline操作 **********************
        stopWatch.start("pipeline存" + mount + "条数据");
        RedisSerializer<Object> hashKeySerializer = RedisPipelineUtil.getHashKeySerializer();
        RedisSerializer<Object> hashValueSerializer = RedisPipelineUtil.getHashValueSerializer();
        BiFunction<Pipeline, RedisPipelineUtil.PipelineParamSupplier<TmpDTO>, Response<Long>> biFunction4Set = (pipeline, supplier) -> {
            // 如果对参数有额外的逻辑， 可以在这里do something
            TmpDTO tmpDto = supplier.getParam();
            // 这里value自己序列化吧
            byte[] hashKey = hashKeySerializer.serialize(tmpDto.getHashKey());
            byte[] hashValue = hashValueSerializer.serialize(tmpDto.getHashValue());
            return pipeline.hset(supplier.getRedisKey(), hashKey, hashValue);
        };
        
        List<TmpDTOSupplier> paramList4Set = new ArrayList<>(16);
        for (int i = 0; i < mount; i++) {
            TmpDTO tmpDto = TmpDTO.builder().redisKey(redisKey).hashKey("pipeline-hash-key" + i).hashValue("pipeline"
                    + "-hash-value" + i).build();
            paramList4Set.add(new TmpDTOSupplier(tmpDto, RedisPipelineUtil.getKeySerializer()));
        }
        List<Long> resultList4Set = RedisPipelineUtil.pipeline4Cluster(biFunction4Set, paramList4Set);
        resultList4Set.forEach(value ->System.out.println("pipeline hset操作\t" + value));
        stopWatch.stop();
        
        stopWatch.start("pipeline取" + mount + "条数据");
        BiFunction<Pipeline, RedisPipelineUtil.PipelineParamSupplier<TmpDTO>, Response<byte[]>> biFunction4Get = (pipeline, supplier) ->
                pipeline.hget(supplier.getRedisKey(), hashKeySerializer.serialize(supplier.getParam().getHashKey()));
        List<TmpDTOSupplier> paramList4Get = new ArrayList<>(paramList4Set);
        List<byte[]> resultList = RedisPipelineUtil.pipeline4Cluster(biFunction4Get, paramList4Get);
        // 这里value自己返序列化吧(注意: 使用的序列化器要和 存时使用的序列化器对应)
        List<String> resultList4Get = resultList.stream()
                .map(x -> String.valueOf(hashValueSerializer.deserialize(x)))
                .collect(Collectors.toList());
        resultList4Get.forEach(value ->System.out.println("pipeline hget操作\t" + value));
        stopWatch.stop();
        
        System.err.println(stopWatch.prettyPrint());
        System.err.println("[验证pipeline是否存在操作丢失] 实际pipeline存了" + resultList4Set.size() + "条");
        System.err.println("[验证pipeline是否存在操作丢失] 实际pipeline取了" + resultList4Get.size() + "条");
    }
    
    
    /**
     * 临时类 for {@link this#testTwo()}
     */
    @Data
    @Builder
    public static class TmpDTO {
         private String redisKey;
         private String hashKey;
         private String hashValue;
    }
    
    /**
     * 临时类 for {@link this#testTwo()}
     */
    @Data
    @Builder
    public static class TmpDTOSupplier implements RedisPipelineUtil.PipelineParamSupplier<TmpDTO> {
    
        private final TmpDTO tmpDTO;
    
        private final RedisSerializer<Object> keySerializer;
    
        public TmpDTOSupplier(TmpDTO tmpDTO, RedisSerializer<Object> keySerializer) {
            this.tmpDTO = tmpDTO;
            this.keySerializer = keySerializer;
        }
    
        
        @Override
        public byte[] getRedisKey() {
            return keySerializer.serialize(getParam().getRedisKey());
        }
    
        @Override
        public TmpDTO getParam() {
            return this.tmpDTO;
        }
    }
    
}
