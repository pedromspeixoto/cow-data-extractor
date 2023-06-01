package com.cde.utils.dto.gyroscope;

import java.util.List;

import com.cde.utils.models.PageDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class GyroscopeResponseAllDataDTO {

    @JsonProperty(value = "gyroscopeData", required = false)
    private List<GyroscopeDataResponseDTO> gyroscopeData;

    @JsonProperty(value = "pageDetails", required = false)
    private PageDetails pageDetails;

    public GyroscopeResponseAllDataDTO() {
    }

    public GyroscopeResponseAllDataDTO(List<GyroscopeDataResponseDTO> gyroscopeData, PageDetails pageDetails) {
        this.gyroscopeData = gyroscopeData;
        this.pageDetails = pageDetails;
    }

    public List<GyroscopeDataResponseDTO> getGyroscopeData() {
        return this.gyroscopeData;
    }

    public void setGyroscopeData(List<GyroscopeDataResponseDTO> gyroscopeData) {
        this.gyroscopeData = gyroscopeData;
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