package com.cde.utils.dto.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class CapabilitiesRequestDataDTO {

    @JsonProperty(value = "accelerometer", required = false)
    private Boolean accelerometer;

    @JsonProperty(value = "gyroscope", required = false)
    private Boolean gyroscope;

    @JsonProperty(value = "gps", required = false)
    private Boolean gps;

    @JsonProperty(value = "temperature", required = false)
    private Boolean temperature;

    @JsonProperty(value = "humidity", required = false)
    private Boolean humidity;

    public CapabilitiesRequestDataDTO() {
    }

    public CapabilitiesRequestDataDTO(Boolean accelerometer, Boolean gyroscope, Boolean gps, Boolean temperature, Boolean humidity) {
        this.accelerometer = accelerometer;
        this.gyroscope = gyroscope;
        this.gps = gps;
        this.temperature = temperature;
        this.humidity = humidity;
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