package com.cde.utils.dto.gyroscope;

import java.time.LocalDateTime;

import com.cde.utils.models.Gyroscope;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class GyroscopeDataDTO {

    @JsonProperty(value = "timestamp", required = true)
    private LocalDateTime timestamp;    

    @JsonProperty(value = "gyroscope", required = true)
    private Gyroscope gyroscope;

    public GyroscopeDataDTO() {

    }

    public GyroscopeDataDTO(LocalDateTime timestamp, Gyroscope gyroscope) {
        this.timestamp = timestamp;
        this.gyroscope = gyroscope;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Gyroscope getGyroscope() {
        return this.gyroscope;
    }

    public void setGyroscope(Gyroscope gyroscope) {
        this.gyroscope = gyroscope;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}