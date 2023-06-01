package com.cde.cowdataconsumer.model.gyroscope;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.cde.utils.dto.gyroscope.GyroscopeDataKafkaDataDTO;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@IdClass(GyroscopeID.class)
@Table(name = "gyroscope_data")
public class GyroscopeData {

    @Id
    private String deviceId;
    @Id
    private LocalDate day;
    @Id
    private LocalDateTime timestamp;
    private Double alpha;
    private Double beta;
    private Double gamma;

    public GyroscopeData() {

    }

    public GyroscopeData(String deviceId, LocalDate day, LocalDateTime timestamp, Double alpha, Double beta, Double gamma) {
        this.deviceId = deviceId;
        this.day = day;
        this.timestamp = timestamp;
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
    }

    public GyroscopeData(GyroscopeDataKafkaDataDTO gyroscopeDataKafkaDataDTO) {
        this.deviceId = gyroscopeDataKafkaDataDTO.getDeviceId();
        this.day = LocalDate.of(gyroscopeDataKafkaDataDTO.getTimestamp().getYear(),
                                gyroscopeDataKafkaDataDTO.getTimestamp().getMonth(),
                                gyroscopeDataKafkaDataDTO.getTimestamp().getDayOfMonth());
        this.timestamp = gyroscopeDataKafkaDataDTO.getTimestamp();
        this.alpha = gyroscopeDataKafkaDataDTO.getGyroscope().getAlpha();
        this.beta = gyroscopeDataKafkaDataDTO.getGyroscope().getBeta();
        this.gamma = gyroscopeDataKafkaDataDTO.getGyroscope().getGamma();
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

    public Double getAlpha() {
        return this.alpha;
    }

    public void setAlpha(Double alpha) {
        this.alpha = alpha;
    }

    public Double getBeta() {
        return this.beta;
    }

    public void setBeta(Double beta) {
        this.beta = beta;
    }

    public Double getGamma() {
        return this.gamma;
    }

    public void setGamma(Double gamma) {
        this.gamma = gamma;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}