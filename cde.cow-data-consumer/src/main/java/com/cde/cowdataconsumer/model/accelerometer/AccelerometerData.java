package com.cde.cowdataconsumer.model.accelerometer;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.cde.utils.dto.accelerometer.AccelerometerDataKafkaDataDTO;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@IdClass(AccelerometerID.class)
@Table(name = "accelerometer_data")
public class AccelerometerData {

    @Id
    private String deviceId;
    @Id
    private LocalDate day;
    @Id
    private LocalDateTime timestamp;
    @Id
    private Double x;
    @Id
    private Double y;
    @Id
    private Double z;

    public AccelerometerData() {

    }

    public AccelerometerData(String deviceId, LocalDate day, LocalDateTime timestamp, Double x, Double y, Double z) {
        this.deviceId = deviceId;
        this.day = day;
        this.timestamp = timestamp;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public AccelerometerData(AccelerometerDataKafkaDataDTO accelerometerDataKafkaDataDTO) {
        this.deviceId = accelerometerDataKafkaDataDTO.getDeviceId();
        this.day = LocalDate.of(accelerometerDataKafkaDataDTO.getTimestamp().getYear(),
                                accelerometerDataKafkaDataDTO.getTimestamp().getMonth(),
                                accelerometerDataKafkaDataDTO.getTimestamp().getDayOfMonth());
        this.timestamp = accelerometerDataKafkaDataDTO.getTimestamp();
        this.x = accelerometerDataKafkaDataDTO.getAccelerometer().getX();
        this.y = accelerometerDataKafkaDataDTO.getAccelerometer().getY();
        this.z = accelerometerDataKafkaDataDTO.getAccelerometer().getZ();
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

    public Double getX() {
        return this.x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return this.y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return this.z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}