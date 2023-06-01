package com.cde.cowdatavisualization.model.device;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cde.utils.dto.device.DeviceRequestDataDTO;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.modelmapper.ModelMapper;

@Entity
@Table(name = "device_data")
public class DeviceData {

    @Id
    private String deviceId;
    private String deviceType;
    private String deviceStatus;
    private String farmId;
    private String deviceDescription;

    public DeviceData() {

    }

    public DeviceData(String deviceId, String deviceType, String deviceStatus, String farmId, String deviceDescription) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.deviceStatus = deviceStatus;
        this.farmId = farmId;
        this.deviceDescription = deviceDescription;
    }

    public DeviceData(DeviceRequestDataDTO deviceRequest) {
        ModelMapper modelMapper = new ModelMapper();
        DeviceData deviceData = modelMapper.map(deviceRequest, DeviceData.class);
        this.deviceId = deviceData.getDeviceId();
        this.deviceDescription = deviceData.getDeviceDescription();
        this.deviceType = deviceData.getDeviceType().toString();
        this.deviceStatus = deviceData.getDeviceStatus().toString();
        this.farmId = deviceData.getFarmId();
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String deviceType) {
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

    public String getDeviceStatus() {
        return this.deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}