package com.cde.cowdataconsumer.model.gyroscope;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class GyroscopeID implements Serializable {

	private static final long serialVersionUID = 1L;
    private String deviceId;
    private LocalDate day;
    private LocalDateTime timestamp;

    public GyroscopeID() {
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public LocalDate getDay() {
        return this.day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}