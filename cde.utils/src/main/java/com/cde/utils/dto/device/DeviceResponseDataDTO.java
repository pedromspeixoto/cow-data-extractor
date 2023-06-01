package com.cde.utils.dto.device;

import java.time.LocalDateTime;

import com.cde.utils.dto.cow.CowResponseDataDTO;
import com.cde.utils.enums.device.DeviceType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class DeviceResponseDataDTO {

    @JsonProperty(value = "deviceId", required = true)
    private String deviceId;

    @JsonProperty(value = "deviceType", required = true)
    private DeviceType deviceType;

    @JsonProperty(value = "farmId", required = false)
    private String farmId;

    @JsonProperty(value = "deviceDescription", required = false)
    private String deviceDescription;

    @JsonProperty(value = "capabilities", required = false)
    private CapabilitiesResponseDataDTO capabilities;

    @JsonProperty(value = "cowData", required = false)
    private CowResponseDataDTO cowData;

    @JsonProperty(value = "timestamp", required = false)
    private LocalDateTime latestTimestamp;

    @JsonProperty(value = "latitude", required = false)
    private Double latestLatitude;

    @JsonProperty(value = "longitude", required = false)
    private Double latestLongitude;

    public DeviceResponseDataDTO() {

    }

    public DeviceResponseDataDTO(String deviceId, DeviceType deviceType, String farmId, String deviceDescription, CapabilitiesResponseDataDTO capabilities, CowResponseDataDTO cowData, LocalDateTime latestTimestamp, Double latestLatitude, Double latestLongitude) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.farmId = farmId;
        this.deviceDescription = deviceDescription;
        this.capabilities = capabilities;
        this.cowData = cowData;
        this.latestTimestamp = latestTimestamp;
        this.latestLatitude = latestLatitude;
        this.latestLongitude = latestLongitude;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceType getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getFarmId() {
        return this.farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public String getDeviceDescription() {
        return this.deviceDescription;
    }

    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }

    public Double getLatestLatitude() {
        return this.latestLatitude;
    }

    public void setLatestLatitude(Double latestLatitude) {
        this.latestLatitude = latestLatitude;
    }

    public Double getLatestLongitude() {
        return this.latestLongitude;
    }

    public void setLatestLongitude(Double latestLongitude) {
        this.latestLongitude = latestLongitude;
    }

    public LocalDateTime getLatestTimestamp() {
        return this.latestTimestamp;
    }

    public void setLatestTimestamp(LocalDateTime latestTimestamp) {
        this.latestTimestamp = latestTimestamp;
    }

    public CapabilitiesResponseDataDTO getCapabilities() {
        return this.capabilities;
    }

    public void setCapabilities(CapabilitiesResponseDataDTO capabilities) {
        this.capabilities = capabilities;
    }

    public CowResponseDataDTO getCowData() {
        return this.cowData;
    }

    public void setCowData(CowResponseDataDTO cowData) {
        this.cowData = cowData;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}