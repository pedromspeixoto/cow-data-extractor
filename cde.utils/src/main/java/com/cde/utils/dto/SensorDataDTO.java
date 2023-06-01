package com.cde.utils.dto;

import java.time.LocalDateTime;

import com.cde.utils.models.GPSCoordinates;
import com.cde.utils.models.Accelerometer;
import com.cde.utils.models.Gyroscope;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class SensorDataDTO {

    @JsonProperty(value = "timestamp", required = true)
    private LocalDateTime timestamp;    

    @JsonProperty(value = "coordinates", required = true)
    private GPSCoordinates gpsCoordinates;
    
    @JsonProperty(value = "accelerometer", required = true)
    private Accelerometer accelerometer;

    @JsonProperty(value = "gyroscope", required = true)
    private Gyroscope gyroscope;

    public SensorDataDTO() {

    }

    public SensorDataDTO(LocalDateTime timestamp, GPSCoordinates gpsCoordinates, Accelerometer accelerometer, Gyroscope gyroscope) {
        this.timestamp = timestamp;
        this.gpsCoordinates = gpsCoordinates;
        this.accelerometer = accelerometer;
        this.gyroscope = gyroscope;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public GPSCoordinates getGpsCoordinates() {
        return this.gpsCoordinates;
    }

    public void setGpsCoordinates(GPSCoordinates gpsCoordinates) {
        this.gpsCoordinates = gpsCoordinates;
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