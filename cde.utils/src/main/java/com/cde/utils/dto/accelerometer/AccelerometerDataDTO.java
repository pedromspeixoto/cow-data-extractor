package com.cde.utils.dto.accelerometer;

import java.time.LocalDateTime;

import com.cde.utils.models.Accelerometer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class AccelerometerDataDTO {

    @JsonProperty(value = "timestamp", required = true)
    private LocalDateTime timestamp;    

    @JsonProperty(value = "accelerometer", required = true)
    private Accelerometer accelerometer;

    public AccelerometerDataDTO() {

    }

    public AccelerometerDataDTO(LocalDateTime timestamp, Accelerometer accelerometer) {
        this.timestamp = timestamp;
        this.accelerometer = accelerometer;
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