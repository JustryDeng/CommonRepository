package com.niantou.redispipeline.util;

import com.niantou.redispipeline.author.JustryDeng;
import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisCommands;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import redis.clients.jedis.BinaryJedisCluster;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisClusterConnectionHandler;
import redis.clients.jedis.JedisClusterInfoCache;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.exceptions.JedisMovedDataException;
import redis.clients.jedis.exceptions.JedisNoReachableClusterNodeException;
import redis.clients.jedis.util.JedisClusterCRC16;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * redis pipeline 工具类
 *
 * @author {@link JustryDeng}
 * @since 2020/11/13 2:41:40
 */
@Slf4j
@Component
@ThreadSafe
@SuppressWarnings("unused")
public final class RedisPipelineUtil implements ApplicationContextAware {
    
    private static RedisTemplate<Object, Object> defaultRedisTemplate;

    /**
     * 获取key序列化器
     *
     * @return  key序列化器
     */
    @NonNull
    public static RedisSerializer<Object> getKeySerializer() {
        //noinspection unchecked
        return (RedisSerializer<Object>) defaultRedisTemplate.getKeySerializer();
    }

    /**
     * 获取value序列化器
     *
     * @return  value序列化器
     */
    @NonNull
    public static RedisSerializer<Object> getValueSerializer() {
        //noinspection unchecked
        return (RedisSerializer<Object>) defaultRedisTemplate.getValueSerializer();
    }
    
    /**
     * 获取hash-key序列化器
     *
     * @return  hash-key序列化器
     */
    @NonNull
    public static RedisSerializer<Object> getHashKeySerializer() {
        //noinspection unchecked
        return (RedisSerializer<Object>)defaultRedisTemplate.getHashKeySerializer();
    }
    
    /**
     * 获取hash-value序列化器
     *
     * @return  hash-value序列化器
     */
    @NonNull
    public static RedisSerializer<Object> getHashValueSerializer() {
        //noinspection unchecked
        return (RedisSerializer<Object>)defaultRedisTemplate.getHashValueSerializer();
    }
    
    /**
     * 流水线批量操作(单节点)
     * <p>
     * 注: SessionCallback是对RedisCallback的进一步封装， 不过我们都已经使用pipeline了， 那干脆直接用RedisCallback好了。
     *
     * @param biConsumer
     *            批量操作逻辑
     * @param paramList
     *            biConsumer用到的参数
     * @return  结果集
     */
    public static <R, P> List<R> pipeline4Standalone(BiConsumer<RedisCommands, P> biConsumer, final List<P> paramList) {
        //noinspection unchecked
        return (List<R>) defaultRedisTemplate.executePipelined((RedisCallback<R>) connection -> {
            for (P p : paramList) {
                biConsumer.accept(connection, p);
            }
            return null;
        }, defaultRedisTemplate.getValueSerializer());
    }

    /**
     * 流水线批量操作(单节点)
     * <p>
     * 注: SessionCallback是对RedisCallback的进一步封装， 不过我们都已经使用pipeline了， 那干脆直接用RedisCallback好了。
     *
     * @param redisTemplate
     *            操作模板
     * @param biConsumer
     *            批量操作逻辑
     * @param paramList
     *            biConsumer用到的参数集合
     * @return  结果集
     */
    public static <R, P> List<R> pipeline4Standalone(RedisTemplate<?, ?> redisTemplate, BiConsumer<RedisCommands, P> biConsumer, final List<P> paramList) {
        //noinspection unchecked
        return (List<R>) redisTemplate.executePipelined((RedisCallback<R>) connection -> {
            for (P p : paramList) {
                biConsumer.accept(connection, p);
            }
            return null;
        }, redisTemplate.getValueSerializer());
    }
    
    /**
     * 由于字符串使用的相对较多， 这里官(本)方(人)直接对字符串提供出来一个操作
     * <p>
     * @see RedisPipelineUtil#pipeline4ClusterSimpleStr(RedisTemplate, BiFunction, List)
     */
    public static <R> List<R> pipeline4ClusterSimpleStr(BiFunction<Pipeline, PipelineParamSupplier<String>, Response<R>> biFunction,
                                                        List<String> paramList)
                                                        throws JedisMovedDataException {
        return RedisPipelineUtil.pipeline4ClusterSimpleStr(defaultRedisTemplate, biFunction, paramList);
    }
    
    /**
     * @see RedisPipelineUtil#pipeline4Cluster(JedisCluster, BiFunction, List)
     */
    @SuppressWarnings("rawtypes")
    public static <R> List<R>  pipeline4ClusterSimpleStr(@NonNull RedisTemplate redisTemplate,
                                                         BiFunction<Pipeline, PipelineParamSupplier<String>,
                                                         Response<R>> biFunction, List<String> paramList)
                                                         throws JedisMovedDataException {
        RedisConnectionFactory redisConnectionFactory = redisTemplate.getConnectionFactory();
        Assert.notNull(redisConnectionFactory, "redisConnectionFactory cannot be null");
        RedisClusterConnection clusterConnection = redisConnectionFactory.getClusterConnection();
        if (!(clusterConnection instanceof JedisClusterConnection)) {
            throw new UnsupportedOperationException("cannot support RedisClusterConnection [" +  clusterConnection.getClass().getName() + "]");
        }
        JedisCluster jedisCluster = ((JedisClusterConnection) clusterConnection).getNativeConnection();
        @SuppressWarnings("unchecked") RedisSerializer<Object> keySerializer = (RedisSerializer<Object>)redisTemplate.getKeySerializer();
        return RedisPipelineUtil.pipeline4ClusterSimpleStr(jedisCluster, keySerializer, biFunction, paramList);
    }
    
    /**
     * 为保证keySerializer与jedisCluster是配套的，这里将此方法私有化，不对外提供
     * <p>
     * @see RedisPipelineUtil#pipeline4Cluster(JedisCluster, BiFunction, List)
     */
    private static <R> List<R> pipeline4ClusterSimpleStr(@NonNull JedisCluster jedisCluster, RedisSerializer<Object> keySerializer,
                                                         BiFunction<Pipeline, PipelineParamSupplier<String>, Response<R>> biFunction,
                                                         List<String> paramList)
            throws JedisMovedDataException {
        List<StringSelfSupplier> supplierParamList = paramList.stream().map(x -> new StringSelfSupplier(x, keySerializer)).collect(Collectors.toList());
        return RedisPipelineUtil.pipeline4Cluster(jedisCluster, biFunction, supplierParamList);
    }
    
    /**
     * @see RedisPipelineUtil#pipeline4Cluster(RedisTemplate, BiFunction, List)
     */
    public static <P extends PipelineParamSupplier<T>, T, R> List<R> pipeline4Cluster(BiFunction<Pipeline, PipelineParamSupplier<T>,
                                                                                      Response<R>> biFunction, List<P> paramList)
                                                                                      throws JedisMovedDataException {
        return RedisPipelineUtil.pipeline4Cluster(defaultRedisTemplate, biFunction, paramList);
    }
    
    /**
     * @see RedisPipelineUtil#pipeline4Cluster(JedisCluster, BiFunction, List)
     */
    @SuppressWarnings("rawtypes")
    public static <P extends PipelineParamSupplier<T>, T, R> List<R> pipeline4Cluster(@NonNull RedisTemplate redisTemplate,
                                                                                      BiFunction<Pipeline, PipelineParamSupplier<T>, Response<R>> biFunction,
                                                                                      List<P> paramList)
                                                                                      throws JedisMovedDataException {
        RedisConnectionFactory redisConnectionFactory = redisTemplate.getConnectionFactory();
        Assert.notNull(redisConnectionFactory, "redisConnectionFactory cannot be null");
        RedisClusterConnection clusterConnection = redisConnectionFactory.getClusterConnection();
        if (!(clusterConnection instanceof JedisClusterConnection)) {
            throw new UnsupportedOperationException("cannot support RedisClusterConnection [" +  clusterConnection.getClass().getName() + "]");
        }
        return RedisPipelineUtil.pipeline4Cluster(((JedisClusterConnection) clusterConnection).getNativeConnection(), biFunction, paramList);
    }
    
    /**
     * (使用JedisCluster，实现)流水线批量操作(集群)
     *
     * @param jedisCluster
     *            JedisCluster实例
     * 其余参数
     * @see JedisClusterPipeline#pipeline4Cluster(BiFunction, List)
     */
    public static <P extends PipelineParamSupplier<T>, T, R> List<R> pipeline4Cluster(@NonNull JedisCluster jedisCluster,
                                                                                      BiFunction<Pipeline, PipelineParamSupplier<T>,
                                                                                      Response<R>> biFunction, List<P> paramList)
                                                                                      throws JedisMovedDataException {
        JedisClusterPipeline jedisClusterPipeline = new JedisClusterPipeline(jedisCluster);
        return jedisClusterPipeline.pipeline(biFunction, paramList);
    }
    
    @Override
    @SuppressWarnings("rawtypes")
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 初始化
        Map<String, RedisTemplate> beansOfType = applicationContext.getBeansOfType(RedisTemplate.class);
        Map.Entry<String, RedisTemplate> redisTemplateEntry = beansOfType.entrySet().stream()
                .findFirst().orElseThrow(() -> new IllegalArgumentException(" cannot find any RedisTemplate"));
        //noinspection unchecked
        RedisPipelineUtil.defaultRedisTemplate = redisTemplateEntry.getValue();
        log.info(" use [{}] as the default RedisPipelineUtil's RedisTemplate", redisTemplateEntry.getKey());
    }
    
    /**
     * jedis使用pipeline操作redis-cluster辅助类
     *
     * 参考并整理自
     *     <a href="https://blog.csdn.net/youaremoon/article/details/51751991?utm_medium=distribute.pc_relevant.none-task-blog-searchFromBaidu-2.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-searchFromBaidu-2.control"/>
     *     <a href="https://www.cnblogs.com/xiaodf/p/11002184.html"/>
     *     <a href="https://blog.csdn.net/xiaoliu598906167/article/details/82218525?utm_medium=distribute.pc_aggpage_search_result.none-task-blog-2~all~first_rank_v2~rank_v25-10-82218525.nonecase&utm_term=pipeline%20redis%20%E8%BF%94%E5%9B%9E%E5%80%BC&spm=1000.2123.3001.4430"/>
     *
     * @author {@link JustryDeng}
     * @since 2020/11/27 16:29:59
     */
    public static class JedisClusterPipeline {
        
        private final JedisClusterConnectionHandler connectionHandler;
        
        private final JedisClusterInfoCache infoCache;
    
        public JedisClusterPipeline(JedisCluster jedisCluster) {
            try {
                Field connectionHandlerField = BinaryJedisCluster.class.getDeclaredField("connectionHandler");
                boolean accessible = connectionHandlerField.isAccessible();
                connectionHandlerField.setAccessible(true);
                this.connectionHandler = (JedisClusterConnectionHandler) connectionHandlerField.get(jedisCluster);
                connectionHandlerField.setAccessible(accessible);
                
                Field cacheField = JedisClusterConnectionHandler.class.getDeclaredField("cache");
                accessible = cacheField.isAccessible();
                cacheField.setAccessible(true);
                this.infoCache = (JedisClusterInfoCache) cacheField.get(connectionHandler);
                cacheField.setAccessible(accessible);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    
        /**
         * (使用JedisCluster，实现)流水线批量操作(集群)
         * <p>
         * 注: 【据说】对集群redis进行pipeline, Jedis比Lettuce快。
         * <p>
         * 泛型说明
         * <ul>
         *     <li>P：操作参数， 其需要实现{@link PipelineParamSupplier<T>}, 以获得 1.redis-key  2.最终进行pipeline的操作参数</li>
         *     <li>T：最终的pipeline操作参数</li>
         *     <li>R: 为返回的数据集合泛型</li>
         * </ul>
         *
         * @param biFunction
         *            批量操作逻辑
         * @param paramList
         *            biFunction会用到的参数
         * @throws JedisMovedDataException
         *            key对应的slot槽点变化时抛出
         * @return  结果集
         */
        public <P extends PipelineParamSupplier<T>, T, R> List<R> pipeline(BiFunction<Pipeline, PipelineParamSupplier<T>, Response<R>> biFunction,
                                                                           List<P> paramList) throws JedisMovedDataException {
            // 从paramList中抽取到对应的redis-key集合
            Map<byte[], P> redisKeyParamMap = paramList.stream().collect(Collectors.toMap(P::getRedisKey, Function.identity()));
            Set<byte[]> allKeys = redisKeyParamMap.keySet();
            Map<JedisPool, List<byte[]>> poolKeys = new HashMap<>(8);
            // 刷新集群信息
            connectionHandler.renewSlotCache();
            for (byte[] key : allKeys) {
                int slot = JedisClusterCRC16.getSlot(key);
                JedisPool jedisPool = getJedisPoolFromSlot(slot);
                if (poolKeys.containsKey(jedisPool)) {
                    List<byte[]> keys = poolKeys.get(jedisPool);
                    keys.add(key);
                } else {
                    List<byte[]> keys = new ArrayList<>();
                    keys.add(key);
                    poolKeys.put(jedisPool, keys);
                }
            }
            int size = allKeys.size();
            List<R> result = new ArrayList<>(size);
            List<Response<R>> responseList = new ArrayList<>(size);
            poolKeys.forEach((JedisPool jedisPool, List<byte[]> keys) -> {
                Jedis jedis = jedisPool.getResource();
                Pipeline pipeline = jedis.pipelined();
                try {
                    keys.forEach(key -> responseList.add(biFunction.apply(pipeline, redisKeyParamMap.get(key))));
                } finally {
                    try {
                        pipeline.close();
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                    try {
                        jedis.close();
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
            });
            responseList.forEach(response -> result.add(response.get()));
            return result;
        }
    
        /**
         * 根据槽点获取要对应使用的JedisPool
         */
        private JedisPool getJedisPoolFromSlot(int slot) {
            JedisPool connectionPool = infoCache.getSlotPool(slot);
            if (connectionPool != null) {
                // It can't guaranteed to get valid connection because of node assignment
                return connectionPool;
            } else {
                // It's abnormal situation for cluster mode, that we have just nothing for slot, try to rediscover state
                // 刷新集群信息
                connectionHandler.renewSlotCache();
                connectionPool = infoCache.getSlotPool(slot);
                if (connectionPool != null) {
                    return connectionPool;
                } else {
                    throw new JedisNoReachableClusterNodeException("No reachable node in cluster for slot " + slot);
                }
            }
        }
    }
    
    /**
     * Jedis操作redis-cluster时， pipeline操作参数提供器
     *
     * @author {@link JustryDeng}
     * @since 2020/11/28 16:45:40
     */
    public interface PipelineParamSupplier<T> {
        
        /**
         * 获取(序列化后的)redis key
         * <p>
         * P.S. 在使用Pipeline操作集群时，redis key使用这个方法获取。
         * <p>
         * 注: 这里之所以要将【获取redis key】抽取为一个方法，是因为相关逻辑中有多个地方会用到。 如果这些地方在将key对象序列化为byte[]时，
         *     采用了不同的序列化方式， 那么可能存在数据槽slot定位不一致的问题， 进而(因代码不当)引起JedisMovedDataException异常。
         *     为了避免上述问题，这里将获取redis-key的操作，抽取统一。
         *
         * @return  redis key
         */
        byte[] getRedisKey();
    
        /**
         * 获取pipeline操作需要的参数
         * <p>
         *  P.S. 在使用Pipeline操作集群时，redis key使用这个方法获取。
         * @return pipeline操作参数
         */
        T getParam();
    }
    
    
    /**
     * 官(本)方(人)对常用的字符串提供PipelineParamSupplier实现
     *
     * @author {@link JustryDeng}
     * @since 2020/11/25 22:06:23
     */
    public static class StringSelfSupplier implements PipelineParamSupplier<String> {
        
        private final String str;
        
        private final RedisSerializer<Object> keySerializer;
        
        public StringSelfSupplier(String str, RedisSerializer<Object> keySerializer) {
            this.str = str;
            this.keySerializer = keySerializer;
        }
        
        @Override
        public byte[] getRedisKey() {
            return keySerializer.serialize(getParam());
        }
        
        @Override
        public String getParam() {
            return this.str;
        }
    }
}
