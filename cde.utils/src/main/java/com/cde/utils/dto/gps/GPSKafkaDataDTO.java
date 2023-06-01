package com.cde.utils.dto.gps;

import java.time.LocalDateTime;

import com.cde.utils.models.GPSCoordinates;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class GPSKafkaDataDTO {

    @JsonProperty(value = "device_id", required = true)
    private String deviceId;   

    @JsonProperty(value = "timestamp", required = true)
    private LocalDateTime timestamp;    

    @JsonProperty(value = "coordinates", required = true)
    private GPSCoordinates gpsCoordinates;

    public GPSKafkaDataDTO() {

    }

    public GPSKafkaDataDTO(String deviceId, LocalDateTime timestamp, GPSCoordinates gpsCoordinates) {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.gpsCoordinates = gpsCoordinates;
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

    public GPSCoordinates getGpsCoordinates() {
        return this.gpsCoordinates;
    }

    public void setGpsCoordinates(GPSCoordinates gpsCoordinates) {
        this.gpsCoordinates = gpsCoordinates;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}