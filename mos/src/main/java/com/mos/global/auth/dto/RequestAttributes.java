package com.mos.global.auth.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.Map;

@Getter
public abstract class RequestAttributes {
    private final Map<String, Object> response;

    public RequestAttributes(String response) {
        try {
            ObjectMapper om = new ObjectMapper();
            this.response = om.readValue(response, Map.class);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("요청 파라미터 오류");
        }
    }

    public abstract String getAccessToken();

}
