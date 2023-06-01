package com.cde.utils.dto.accelerometer;

import java.util.List;

import com.cde.utils.models.PageDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class AccelerometerResponseAllDataDTO {

    @JsonProperty(value = "accelerometerData", required = false)
    private List<AccelerometerDataResponseDTO> accelerometerData;

    @JsonProperty(value = "pageDetails", required = false)
    private PageDetails pageDetails;

    public AccelerometerResponseAllDataDTO() {
    }

    public AccelerometerResponseAllDataDTO(List<AccelerometerDataResponseDTO> accelerometerData, PageDetails pageDetails) {
        this.accelerometerData = accelerometerData;
        this.pageDetails = pageDetails;
    }

    public List<AccelerometerDataResponseDTO> getAccelerometerData() {
        return this.accelerometerData;
    }

    public void setAccelerometerData(List<AccelerometerDataResponseDTO> accelerometerData) {
        this.accelerometerData = accelerometerData;
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