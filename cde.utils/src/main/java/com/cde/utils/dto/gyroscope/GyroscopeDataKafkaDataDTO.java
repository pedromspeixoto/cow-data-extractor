package com.cde.utils.dto.gyroscope;

import java.time.LocalDateTime;

import com.cde.utils.models.Gyroscope;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class GyroscopeDataKafkaDataDTO {

    @JsonProperty(value = "device_id", required = true)
    private String deviceId;   

    @JsonProperty(value = "timestamp", required = true)
    private LocalDateTime timestamp;    

    @JsonProperty(value = "gyroscope", required = true)
    private Gyroscope gyroscope;

    public GyroscopeDataKafkaDataDTO() {

    }

    public GyroscopeDataKafkaDataDTO(String deviceId, LocalDateTime timestamp, Gyroscope gyroscope) {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.gyroscope = gyroscope;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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