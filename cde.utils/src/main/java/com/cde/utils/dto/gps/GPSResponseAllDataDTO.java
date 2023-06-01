package com.cde.utils.dto.gps;

import java.util.List;

import com.cde.utils.models.PageDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class GPSResponseAllDataDTO {

    @JsonProperty(value = "gpsData", required = false)
    private List<GPSDataResponseDTO> gpsData;

    @JsonProperty(value = "pageDetails", required = false)
    private PageDetails pageDetails;

    public GPSResponseAllDataDTO() {
    }

    public GPSResponseAllDataDTO(List<GPSDataResponseDTO> gpsData, PageDetails pageDetails) {
        this.gpsData = gpsData;
        this.pageDetails = pageDetails;
    }

    public List<GPSDataResponseDTO> getGpsData() {
        return this.gpsData;
    }

    public void setGpsData(List<GPSDataResponseDTO> gpsData) {
        this.gpsData = gpsData;
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