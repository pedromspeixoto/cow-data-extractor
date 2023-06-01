package com.cde.utils.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class GPSCoordinates{

    @JsonProperty(value = "latitude", required = true)
    private Double latitude;

    @JsonProperty(value = "longitude", required = true)
    private Double longitude;

    public GPSCoordinates() {

    }

    public GPSCoordinates(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;        
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}