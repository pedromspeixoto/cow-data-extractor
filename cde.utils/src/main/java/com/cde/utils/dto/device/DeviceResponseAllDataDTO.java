package com.cde.utils.dto.device;

import java.util.List;

import com.cde.utils.models.PageDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class DeviceResponseAllDataDTO {

    @JsonProperty(value = "deviceList", required = false)
    private List<DeviceResponseDataDTO> deviceList;

    @JsonProperty(value = "pageDetails", required = false)
    private PageDetails pageDetails;

    public DeviceResponseAllDataDTO() {
    }

    public DeviceResponseAllDataDTO(List<DeviceResponseDataDTO> deviceList, PageDetails pageDetails) {
        this.deviceList = deviceList;
        this.pageDetails = pageDetails;
    }

    public List<DeviceResponseDataDTO> getDeviceList() {
        return this.deviceList;
    }

    public void setDeviceList(List<DeviceResponseDataDTO> deviceList) {
        this.deviceList = deviceList;
    }

    public PageDetails getPageDetails() {
        return this.pageDetails;
    }

    public void setPageDetails(PageDetails pageDetails) {
        this.pageDetails = pageDetails;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}