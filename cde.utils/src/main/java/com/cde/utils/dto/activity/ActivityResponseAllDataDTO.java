package com.cde.utils.dto.activity;

import java.util.List;

import com.cde.utils.models.PageDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class ActivityResponseAllDataDTO {

    @JsonProperty(value = "activityData", required = false)
    private List<ActivityDataResponseDTO> activityData;

    @JsonProperty(value = "pageDetails", required = false)
    private PageDetails pageDetails;

    public ActivityResponseAllDataDTO() {
    }

    public ActivityResponseAllDataDTO(List<ActivityDataResponseDTO> activityData, PageDetails pageDetails) {
        this.activityData = activityData;
        this.pageDetails = pageDetails;
    }

    public List<ActivityDataResponseDTO> getActivityData() {
        return this.activityData;
    }

    public void setActivityData(List<ActivityDataResponseDTO> activityData) {
        this.activityData = activityData;
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