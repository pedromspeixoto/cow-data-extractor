package com.cde.utils.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class Gyroscope{

    @JsonProperty(value = "alpha", required = true)
    private Double alpha;

    @JsonProperty(value = "beta", required = true)
    private Double beta;

    @JsonProperty(value = "gamma", required = true)
    private Double gamma;

    public Gyroscope() {

    }

    public Gyroscope(Double alpha, Double beta, Double gamma) {
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;       
    }

    public Double getAlpha() {
        return this.alpha;
    }

    public void setAlpha(Double alpha) {
        this.alpha = alpha;
    }

    public Double getBeta() {
        return this.beta;
    }

    public void setBeta(Double beta) {
        this.beta = beta;
    }

    public Double getGamma() {
        return this.gamma;
    }

    public void setGamma(Double gamma) {
        this.gamma = gamma;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}