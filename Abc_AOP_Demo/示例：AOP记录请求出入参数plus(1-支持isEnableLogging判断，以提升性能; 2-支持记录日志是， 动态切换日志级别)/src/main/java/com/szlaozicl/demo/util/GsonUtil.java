package com.szlaozicl.demo.util;

import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.szlaozicl.demo.annotation.GsonIgnore;
import com.szlaozicl.demo.author.JustryDeng;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * GsonUtil
 * <p>
 * <b>Gson的三个常用注解</b>
 * <ul>
 *     <li>自定义的{@link GsonIgnore}: 控制字段/类是否参与序列化、反序列化</li>
 *     <li>{@link SerializedName}: 指定字段序列化后的名字, 指定字段反序列化时采用的名字, 指定字段反序列化时候补的字段名</li>
 *     <li>{@link JsonAdapter}: 支持定制序列化逻辑、反序列化逻辑. 可详见该类的注释说明</li>
 * </ul>
 * <p>
 * <b>注意事项</b>:
 * <li>Gson反序列化时, 是大小写敏感的</li>
 * <li>@SerializedName的value属性控制 1.序列化后的key值。 2.返序列化时首选的key值(若首选的key不存在, 则查找alternate属性指定的候补key)。
 *     <br/>
 *     &emsp;测试(2.8.6版本的)gson时发现: 反序列化时， json中不存在value指定的首选key,但存在多个候补key时， 那么哪一个候补key排在json字符串的后面, 就会以哪一个候补key的值进行相应字段的赋值。
 * </li>
 * <p>
 * 参考链接<a href="https://github.com/craciunbogdangeorge/GsonUtils" />
 * @author {@link JustryDeng}
 * @date 2020/6/1 22:37:27
 */
@Slf4j
@SuppressWarnings("unused")
public class GsonUtil {
    
    private static final Gson GSON_INSTANCE = new GsonBuilder()
            // null也参与序列化
            .serializeNulls()
            // 日期的序列化格式
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            // 增加自定义的忽略策略(即: 被@GsonIgnore标记的类 或 字段不参与序列化、反序列化)
            .addSerializationExclusionStrategy(new GsonIgnoreExclusionStrategy(true))
            .addDeserializationExclusionStrategy(new GsonIgnoreExclusionStrategy(false))
            /// 格式化输出
            ///.setPrettyPrinting()
            .create();
    
    private GsonUtil() {
        throw new UnsupportedOperationException("util-class don't support instance");
    }
    
    /**
     * Returns the JSON String representation of an object.
     *
     * @param object
     *         an arbitrary object.
     *
     * @return a JSON String representation of the object.
     */
    public static String toJson(Object object) {
        return GSON_INSTANCE.toJson(object);
    }
    
    /**
     * @see GsonUtil#fromJson(String, Class)
     */
    public static Object fromJson(@NonNull String json) {
        return GSON_INSTANCE.fromJson(json, Object.class);
    }
    
    /**
     * Returns the JSON object as a plain java object by the specified type.
     *
     * @param json
     *         a JSON String representation of a JSON object.
     * @param clazz
     *         the type of the desired object.
     *
     * @return a plain java object by the specified type.
     */
    public static <T> T fromJson(@NonNull String json, Class<T> clazz) {
        return GSON_INSTANCE.fromJson(json, clazz);
    }
    
    /**
     * map转换为object
     *
     * @param map
     *         a Map representation of an object.
     * @param clazz
     *         the type of the desired object.
     *
     * @return a plain java object by the specified type.
     */
    public static <T> T fromMap(Map<String, Object> map, Class<T> clazz) {
        return GSON_INSTANCE.fromJson(GSON_INSTANCE.toJson(map), clazz);
    }
    
    /**
     * object转换为map
     *
     * @param object
     *         要转换的object
     *
     * @return 该object对应的map
     */
    public static <T> Map<String, T> toMap(Object object) {
        return GSON_INSTANCE.fromJson(GSON_INSTANCE.toJson(object), new TypeToken<Map<String, T>>() {
        }.getType());
    }
    
    /**
     * json转换为list
     *
     * @param jsonArrayString
     *         集合/素组 json字符串
     * @param clazz
     *         list的泛型类型
     *
     * @return list
     */
    public static <T> List<T> toList(String jsonArrayString, Class<T> clazz) {
        List<T> list = new ArrayList<T>(16);
        JsonArray array = JsonParser.parseString(jsonArrayString).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(GSON_INSTANCE.fromJson(elem, clazz));
        }
        return list;
    }
    
    /**
     * Returns a JsonObject object from a String representation of a JSON object.
     *
     * @param jsonObjectString
     *         a String representation of a JSON object.
     *
     * @return a JsonObject object from the specified String.
     */
    public static JsonObject toJsonObject(@NonNull String jsonObjectString) {
        return JsonParser.parseString(jsonObjectString).getAsJsonObject();
    }
    
    /**
     * Returns a JsonArray object from a String representation of a JSON array object.
     *
     * @param jsonArrayString
     *         a String representation of a JSON array object.
     *
     * @return a JsonArray object from the specified String.
     */
    public static JsonArray toJsonArray(@NonNull String jsonArrayString) {
        return JsonParser.parseString(jsonArrayString).getAsJsonArray();
    }
    
    /**
     * Returns a jsonElement from a String representation of a JSON object.
     *
     * @param jsonString
     *         a String representation of a JSON object.
     *
     * @return a jsonElement from the specified String.
     */
    public static JsonElement toJsonElement(@NonNull String jsonString) {
        return JsonParser.parseString(jsonString);
    }
    
    /**
     * Checks if the provided String is a representation of a JSON object.
     *
     * @param jsonStringObject
     *         a possible String representation of a JSON object.
     *
     * @return true if this element is of type JsonObject, false otherwise.
     */
    public static boolean isJsonObject(@NonNull String jsonStringObject) {
        try {
            return toJsonObject(jsonStringObject).isJsonObject();
        } catch (IllegalStateException e) {
            log.warn(" toJsonObject for '{}' warning", e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks if the provided String is a representation of a JSON array.
     *
     * @param jsonStringArray
     *         a possible String representation of a JSON array.
     *
     * @return true if this element is of type JsonArray, false otherwise.
     */
    public static boolean isJsonArray(@NonNull String jsonStringArray) {
        try {
            return toJsonArray(jsonStringArray).isJsonArray();
        } catch (IllegalStateException e) {
            log.warn(" toJsonArray for '{}' warning", e.getMessage());
            return false;
        }
    }
    
    /**
     * 自定义忽略策略
     * <p>
     * 官网demo <u>https://www.javadoc.io/doc/com.google.code.gson/gson/latest/com.google.gson/com/google/gson/ExclusionStrategy.html<u/>
     *
     * @author {@link JustryDeng}
     * @date 2020/6/1 23:50:36
     */
    private static class GsonIgnoreExclusionStrategy implements ExclusionStrategy {
        
        /** 此策略是否是用于序列化， true-用于序列化; false-用于反序列化 */
        private final boolean use4Serialize;
    
        private GsonIgnoreExclusionStrategy(boolean use4Serialize) {
            this.use4Serialize = use4Serialize;
        }
    
        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            GsonIgnore gsonIgnore = clazz.getAnnotation(GsonIgnore.class);
            if (gsonIgnore == null) {
                // 当前类上 不存在@GsonIgnore, 那么是需要参与序列化、反序列化的
                return false;
            }
            if (use4Serialize) {
                return gsonIgnore.serialize();
            } else {
                return gsonIgnore.deserialize();
            }
        }
        
        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            GsonIgnore gsonIgnore = fieldAttributes.getAnnotation(GsonIgnore.class);
            if (gsonIgnore == null) {
                // 当前字段上 不存在@GsonIgnore, 那么是需要参与序列化、反序列化的
                return false;
            }
            if (use4Serialize) {
                return gsonIgnore.serialize();
            } else {
                return gsonIgnore.deserialize();
            }
        }
    }
}