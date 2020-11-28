//package com.niantou.testcontainer.redis.jedis4cluster.helper;
//
//import com.niantou.testcontainer.author.JustryDeng;
//import org.apache.commons.pool2.PooledObjectFactory;
//import org.apache.commons.pool2.impl.GenericObjectPool;
//import redis.clients.jedis.BinaryJedisCluster;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisClusterConnectionHandler;
//import redis.clients.jedis.JedisClusterInfoCache;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisSlotBasedConnectionHandler;
//import redis.clients.jedis.util.Pool;
//
//import java.lang.reflect.Field;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicReference;
//
///**
// * JedisClusterHelper4Test
// *
// * @author {@link JustryDeng}
// * @since 2020/11/17 13:35:48
// */
//public class JedisClusterHelper4Test {
//
//    private static final Field FIELD_CONNECTION_HANDLER;
//    private static final Field FIELD_CACHE;
//    private static final Field NODES;
//    private static final Field INTERNAL_POOL;
//    private static final Field FACTORY;
//    private static final Field HOST_AND_PORT;
//
//    static {
//        FIELD_CONNECTION_HANDLER = getField(BinaryJedisCluster.class, "connectionHandler");
//        FIELD_CACHE = getField(JedisClusterConnectionHandler.class, "cache");
//        NODES = getField(JedisClusterInfoCache.class, "nodes");
//        INTERNAL_POOL = getField(Pool.class, "internalPool");
//        FACTORY =getField(GenericObjectPool.class, "factory");
//        try {
//            HOST_AND_PORT =getField(ClassLoader.getSystemClassLoader().loadClass("redis.clients.jedis.JedisFactory"), "hostAndPort");
//        } catch (ClassNotFoundException e) {
//           throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * 获取Field
//     */
//    private static Field getField(Class<?> cls, String fieldName) {
//        try {
//            Field field = cls.getDeclaredField(fieldName);
//            field.setAccessible(true);
//            return field;
//        } catch (NoSuchFieldException | SecurityException e) {
//            throw new RuntimeException("cannot find or access field '" + fieldName + "' from " + cls.getName(), e);
//        }
//    }
//
//    /**
//     * 获取值
//     */
//    private static <T> T getValue(Object obj, Field field) {
//        try {
//            //noinspection unchecked
//            return (T)field.get(obj);
//        } catch (IllegalArgumentException | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * 将connectionHandler中对redis的节点连接地址指向localhost，使之能与test-containers关联的docker通信，否者无法直接通信
//     */
//    public static void handleNodeAddress2Localhost(JedisCluster jedisCluster) {
//        JedisSlotBasedConnectionHandler connectionHandler = getValue(jedisCluster, FIELD_CONNECTION_HANDLER);
//        JedisClusterInfoCache clusterInfoCache = getValue(connectionHandler, FIELD_CACHE);
//        Map<String, JedisPool> nodeInfoMap = clusterInfoCache.getNodes();
//        Map<String, JedisPool> newNodeInfoMap = new HashMap<>(16);
//        nodeInfoMap.forEach((k, v) -> {
//            newNodeInfoMap.put("localhost" + k.substring(k.indexOf(":")), v);
//            GenericObjectPool<?> internalPool = getValue(v, INTERNAL_POOL);
//            PooledObjectFactory<?> factory = getValue(internalPool, FACTORY);
//            AtomicReference<HostAndPort> hostAndPortAtomicReference = getValue(factory, HOST_AND_PORT);
//            HostAndPort hostAndPort = hostAndPortAtomicReference.get();
//            hostAndPortAtomicReference.set(new HostAndPort("localhost", hostAndPort.getPort()));
//        });
//        NODES.setAccessible(true);
//        try {
//            NODES.set(clusterInfoCache, newNodeInfoMap);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
