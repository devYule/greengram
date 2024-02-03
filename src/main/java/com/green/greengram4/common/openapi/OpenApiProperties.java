package com.green.greengram4.common.openapi;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "openapi.apartment")
public class OpenApiProperties {
    private final String baseUrl;
    private final String dataUrl;
    private final String serviceKey;

    public OpenApiProperties(String baseUrl, String dataUrl, String serviceKey) {
        this.baseUrl = baseUrl;
        this.dataUrl = dataUrl;
        this.serviceKey = serviceKey;
    }
}
