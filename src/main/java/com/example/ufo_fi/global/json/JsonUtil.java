package com.example.ufo_fi.global.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Arrays;
import java.util.stream.Collectors;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule()) // LocalDateTime 지원
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private static final String PARSE_FAIL_MESSAGE = "메시지 파싱 실패";

    /**
     * 객체를 JSON 문자열로 변환합니다.
     */
    public static String toJson(Object obj) {
        if (obj == null) return "null";
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return PARSE_FAIL_MESSAGE + " (" + obj.getClass().getSimpleName() + ")";
        }
    }

    public static String toPrettyJson(Object obj){
        if (obj == null) return "null";
        try {
            return objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return PARSE_FAIL_MESSAGE + " (" + obj.getClass().getSimpleName() + ")";
        }
    }

    public static String toJsonArray(Object[] args) {
        if (args == null || args.length == 0) return "(없음)";
        return Arrays.stream(args).map(JsonUtil::toJson)
                .collect(Collectors.joining(",\n"));
    }

    /**
     * JSON 문자열을 객체로 역직렬화합니다.
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 역직렬화 실패: " + e.getMessage(), e);
        }
    }
}