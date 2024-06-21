package com.micropos.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "webpos.auth")
@Component
public class AuthProperties {
    private List<String> includePaths;
    private List<String> excludePaths;
}
