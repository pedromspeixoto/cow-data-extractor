package com.cde.utils.dto.gps;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.cde.utils.models.GPSCoordinates;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class GPSDataDTO {

    @JsonProperty(value = "timestamp", required = true)
    private LocalDateTime timestamp;    

    @JsonProperty(value = "coordinates", required = true)
    private GPSCoordinates gpsCoordinates;

    public GPSDataDTO() {

    }

    public GPSDataDTO(LocalDateTime timestamp, GPSCoordinates gpsCoordinates) {
        this.timestamp = timestamp;
        this.gpsCoordinates = gpsCoordinates;
    }

    public GPSDataDTO(String timestamp, double latitude, double longitude) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        this.timestamp = LocalDateTime.parse(timestamp, formatter);
        this.gpsCoordinates = new GPSCoordinates(latitude, longitude);
    }

    public GPSDataDTO(String timestamp, Double latitude, Double longitude) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        this.timestamp = LocalDateTime.parse(timestamp, formatter);
        this.gpsCoordinates = new GPSCoordinates(latitude, longitude);
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