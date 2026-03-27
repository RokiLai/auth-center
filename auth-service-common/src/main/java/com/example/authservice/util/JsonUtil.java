package com.example.authservice.util;

import com.example.authservice.exception.AuthErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roki.exception.BusinessException;


public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtil() {
        // 工具类禁止实例化
    }

    /**
     * 对象转 JSON 字符串
     */
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new BusinessException(AuthErrorCode.JSON_PROCESS_ERROR);
        }
    }

    /**
     * JSON 字符串转对象
     */
    public static <T> T toObj(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new BusinessException(AuthErrorCode.JSON_PROCESS_ERROR);
        }
    }

    /**
     * JSON 字符串转复杂容器对象（如 List、Map）
     */
    public static <T> T toObj(String json, TypeReference<T> typeRef) {
        try {
            return objectMapper.readValue(json, typeRef);
        } catch (JsonProcessingException e) {
            throw new BusinessException(AuthErrorCode.JSON_PROCESS_ERROR);
        }
    }

    /**
     * 判断 JSON 是否有效
     */
    public static boolean isValidJson(String json) {
        try {
            objectMapper.readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
