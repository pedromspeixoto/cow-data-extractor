package com.cde.utils.dto.motion;

import java.time.LocalDateTime;

import com.cde.utils.models.Accelerometer;
import com.cde.utils.models.Gyroscope;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class MotionKafkaDataDTO {

    @JsonProperty(value = "device_id", required = true)
    private String deviceId;   

    @JsonProperty(value = "timestamp", required = true)
    private LocalDateTime timestamp;

    @JsonProperty(value = "accelerometer", required = true)
    private Accelerometer accelerometer;

    @JsonProperty(value = "gyroscope", required = true)
    private Gyroscope gyroscope;

    public MotionKafkaDataDTO() {

    }

    public MotionKafkaDataDTO(String deviceId, LocalDateTime timestamp, Accelerometer accelerometer, Gyroscope gyroscope) {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.accelerometer = accelerometer;
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

    public Accelerometer getAccelerometer() {
        return this.accelerometer;
    }

    public void setAccelerometer(Accelerometer accelerometer) {
        this.accelerometer = accelerometer;
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