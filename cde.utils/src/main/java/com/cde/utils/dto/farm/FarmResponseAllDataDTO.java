package com.cde.utils.dto.farm;

import java.util.List;

import com.cde.utils.models.PageDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class FarmResponseAllDataDTO {

    @JsonProperty(value = "farmList", required = false)
    private List<FarmResponseDataDTO> farmList;

    @JsonProperty(value = "pageDetails", required = false)
    private PageDetails pageDetails;

    public FarmResponseAllDataDTO() {
    }

    public FarmResponseAllDataDTO(List<FarmResponseDataDTO> farmList, PageDetails pageDetails) {
        this.farmList = farmList;
        this.pageDetails = pageDetails;
    }

    public List<FarmResponseDataDTO> getFarmList() {
        return this.farmList;
    }

    public void setFarmList(List<FarmResponseDataDTO> farmList) {
        this.farmList = farmList;
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