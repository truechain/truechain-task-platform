package com.truechain.task.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Json工具类
 */
public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public static ObjectMapper mapper = null;

    static {
        mapper = new ObjectMapper();
        // 允许单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        //忽略json中存在而对应的类中不存在的情况
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * parseObject
     *
     * @param json      需要解析的Json字符串
     * @param valueType 这是需要传入的类，用来在readValue中读取该类类型并返回封装的对象
     * @return 目的是返回该对象
     */
    public static <T> T parseObject(String json, Class<T> valueType) {
        try {
            return mapper.readValue(json, valueType);
        } catch (JsonParseException e) {
            logger.error("--JacksonParse,解析的Json字符串出现JsonParseException", e);
        } catch (JsonMappingException e) {
            logger.error("--JacksonParse,解析的Json字符串出现JsonMappingException", e);
        } catch (IOException e) {
            logger.error("--JacksonParse,解析的Json字符串出现IOException", e);
        }
        throw new RuntimeException("解析json异常");
    }

    /**
     * parseObject
     *
     * @param json
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String json, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (IOException e) {
            logger.error("--JacksonParse,解析的Json字符串出现IOException", e);
            throw new RuntimeException("解析json异常");
        }
    }

    /**
     * toJsonString
     *
     * @param value
     * @return
     */
    public static String toJsonString(Object value) {
        try {
            String json = mapper.writeValueAsString(value);
            return json;
        } catch (JsonProcessingException e) {
            logger.error("--toJsonString,转换Json字符串出现JsonProcessingException", e);
            throw new RuntimeException("转换json异常");
        }
    }



}
