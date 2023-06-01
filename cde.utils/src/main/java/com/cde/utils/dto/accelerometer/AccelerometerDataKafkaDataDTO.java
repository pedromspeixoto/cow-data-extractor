package com.cde.utils.dto.accelerometer;

import java.time.LocalDateTime;

import com.cde.utils.models.Accelerometer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class AccelerometerDataKafkaDataDTO {

    @JsonProperty(value = "device_id", required = true)
    private String deviceId;   

    @JsonProperty(value = "timestamp", required = true)
    private LocalDateTime timestamp;    

    @JsonProperty(value = "accelerometer", required = true)
    private Accelerometer accelerometer;

    public AccelerometerDataKafkaDataDTO() {

    }

    public AccelerometerDataKafkaDataDTO(String deviceId, LocalDateTime timestamp, Accelerometer accelerometer) {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.accelerometer = accelerometer;
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

    public Accelerometer getAccelerometer() {
        return this.accelerometer;
    }

    public void setAccelerometer(Accelerometer accelerometer) {
        this.accelerometer = accelerometer;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}