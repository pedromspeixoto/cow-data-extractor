package com.cde.cowdataconsumer.model.gps;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.cde.utils.dto.gps.GPSKafkaDataDTO;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@IdClass(GPSID.class)
@Table(name = "gps_data")
public class GPSData {

    @Id
    private String deviceId;
    @Id
    private LocalDate day;
    @Id
    private LocalDateTime timestamp;
    private Double latitude;
    private Double longitude;

    public GPSData() {

    }

    public GPSData(String deviceId, LocalDate day, LocalDateTime timestamp, Double latitude, Double longitude) {
        this.deviceId = deviceId;
        this.day = day;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GPSData(GPSKafkaDataDTO gpsKafkaDataDTO) {
        this.deviceId = gpsKafkaDataDTO.getDeviceId();
        this.day = LocalDate.of(gpsKafkaDataDTO.getTimestamp().getYear(),
                                gpsKafkaDataDTO.getTimestamp().getMonth(),
                                gpsKafkaDataDTO.getTimestamp().getDayOfMonth());
        this.timestamp = gpsKafkaDataDTO.getTimestamp();
        this.latitude = gpsKafkaDataDTO.getGpsCoordinates().getLatitude();
        this.longitude = gpsKafkaDataDTO.getGpsCoordinates().getLongitude();
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public LocalDate getDay() {
        return this.day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
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

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}