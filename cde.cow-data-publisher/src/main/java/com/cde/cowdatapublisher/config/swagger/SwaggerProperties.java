package com.cde.cowdatapublisher.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class SwaggerProperties {

    @Value("${cde.swagger.enabled}")
    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "SwaggerProperties [enabled=" + enabled + "]";
    }

}