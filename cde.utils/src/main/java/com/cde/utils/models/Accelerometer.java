package com.cde.utils.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class Accelerometer{

    @JsonProperty(value = "x", required = true)
    private Double x;

    @JsonProperty(value = "y", required = true)
    private Double y;

    @JsonProperty(value = "z", required = true)
    private Double z;

    public Accelerometer() {

    }

    public Accelerometer(Double x, Double y, Double z) {
        this.x = x;
        this.y = y;
        this.z = z;       
    }

    public Double getX() {
        return this.x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return this.y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return this.z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}