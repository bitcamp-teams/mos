package com.mos.global.auth.handler.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.Map;

@Getter
public abstract class LoginResponseHandler {
    private Map<String, Object> response;
    private final String token;

    public LoginResponseHandler(String response, String token) {
        try {
            this.token = token;    // 로그아웃 요청 시 엑세스토큰 필요함.
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
