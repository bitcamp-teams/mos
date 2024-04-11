package com.mos.global.auth.handler.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.Map;

@Getter
public abstract class LoginResponseHandler {
    private Map<String, Object> response;

    public LoginResponseHandler(String response) {
        try {
            ObjectMapper om = new ObjectMapper();
            JsonNode jsonNode = om.readTree(response);
            if (jsonNode.isArray()) {
                this. response = om.readValue(jsonNode.get(0).toString(), Map.class);
            } else if (jsonNode.isObject()) {
                this.response = om.readValue(jsonNode.toString(), Map.class);
            }

        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("로그인 API 응답 결과가 올바르지 않습니다");
        }
    }

    public abstract String getEmail();

    public abstract String getName();

    public abstract String getPassword();

    public abstract String getId();

}
