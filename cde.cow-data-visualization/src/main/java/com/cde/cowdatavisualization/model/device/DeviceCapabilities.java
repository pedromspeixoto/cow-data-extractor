package com.cde.cowdatavisualization.model.device;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cde.utils.dto.device.DeviceRequestDataDTO;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Table(name = "device_capabilities")
public class DeviceCapabilities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long capabilitiesId;
    private String deviceId;
    private Boolean accelerometer = false;
    private Boolean gyroscope = false;
    private Boolean gps = false;
    private Boolean temperature = false;
    private Boolean humidity= false;

    public DeviceCapabilities() {
    }

    public DeviceCapabilities(Long capabilitiesId, String deviceId, Boolean accelerometer, Boolean gyroscope, Boolean gps, Boolean temperature, Boolean humidity) {
        this.capabilitiesId = capabilitiesId;
        this.deviceId = deviceId;
        this.accelerometer = accelerometer;
        this.gyroscope = gyroscope;
        this.gps = gps;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public DeviceCapabilities(DeviceRequestDataDTO deviceRequest) {
        this.deviceId = deviceRequest.getDeviceId();
        if (null != deviceRequest.getCapabilities().getAccelerometer()) {
            this.accelerometer = deviceRequest.getCapabilities().getAccelerometer();
        }
        if (null != deviceRequest.getCapabilities().getGyroscope()) {
            this.gyroscope = deviceRequest.getCapabilities().getGyroscope();
        }
        if (null != deviceRequest.getCapabilities().getGps()) {
            this.gps = deviceRequest.getCapabilities().getGps();
        }
        if (null != deviceRequest.getCapabilities().getTemperature()) {
            this.temperature = deviceRequest.getCapabilities().getTemperature();
        }
        if (null != deviceRequest.getCapabilities().getHumidity()) {
            this.humidity = deviceRequest.getCapabilities().getHumidity();
        }
    }

    public Long getCapabilitiesId() {
        return this.capabilitiesId;
    }

    public void setCapabilitiesId(Long capabilitiesId) {
        this.capabilitiesId = capabilitiesId;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Boolean isAccelerometer() {
        return this.accelerometer;
    }

    public Boolean getAccelerometer() {
        return this.accelerometer;
    }

    public void setAccelerometer(Boolean accelerometer) {
        this.accelerometer = accelerometer;
    }

    public Boolean isGyroscope() {
        return this.gyroscope;
    }

    public Boolean getGyroscope() {
        return this.gyroscope;
    }

    public void setGyroscope(Boolean gyroscope) {
        this.gyroscope = gyroscope;
    }

    public Boolean isGps() {
        return this.gps;
    }

    public Boolean getGps() {
        return this.gps;
    }

    public void setGps(Boolean gps) {
        this.gps = gps;
    }

    public Boolean isTemperature() {
        return this.temperature;
    }

    public Boolean getTemperature() {
        return this.temperature;
    }

    public void setTemperature(Boolean temperature) {
        this.temperature = temperature;
    }

    public Boolean isHumidity() {
        return this.humidity;
    }

    public Boolean getHumidity() {
        return this.humidity;
    }

    public void setHumidity(Boolean humidity) {
        this.humidity = humidity;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}