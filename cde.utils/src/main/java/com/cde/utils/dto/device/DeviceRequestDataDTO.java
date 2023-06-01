package com.cde.utils.dto.device;

import com.cde.utils.dto.cow.CowRequestDataDTO;
import com.cde.utils.enums.device.DeviceStatus;
import com.cde.utils.enums.device.DeviceType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class DeviceRequestDataDTO {

    @JsonProperty(value = "deviceId", required = true)
    private String deviceId;

    @JsonProperty(value = "deviceType", required = true)
    private DeviceType deviceType;

    @JsonProperty(value = "deviceStatus", required = true)
    private DeviceStatus deviceStatus;

    @JsonProperty(value = "farmId", required = false)
    private String farmId;

    @JsonProperty(value = "deviceDescription", required = false)
    private String deviceDescription;

    @JsonProperty(value = "capabilities", required = false)
    private CapabilitiesRequestDataDTO capabilities;

    @JsonProperty(value = "cowData", required = false)
    private CowRequestDataDTO cowData;

    public DeviceRequestDataDTO() {

    }

    public DeviceRequestDataDTO(String deviceId, DeviceType deviceType, DeviceStatus deviceStatus, String farmId, String deviceDescription, CapabilitiesRequestDataDTO capabilities, CowRequestDataDTO cowData) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.deviceStatus = deviceStatus;
        this.farmId = farmId;
        this.deviceDescription = deviceDescription;
        this.capabilities = capabilities;
        this.cowData = cowData;
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

    public CapabilitiesRequestDataDTO getCapabilities() {
        return this.capabilities;
    }

    public void setCapabilities(CapabilitiesRequestDataDTO capabilities) {
        this.capabilities = capabilities;
    }

    public CowRequestDataDTO getCowData() {
        return this.cowData;
    }

    public void setCowData(CowRequestDataDTO cowData) {
        this.cowData = cowData;
    }

    public DeviceStatus getDeviceStatus() {
        return this.deviceStatus;
    }

    public void setDeviceStatus(DeviceStatus deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}