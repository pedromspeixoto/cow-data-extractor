package com.cde.utils.dto.gyroscope;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class GyroscopeDataResponseDTO {

    @JsonProperty(value = "timestamp", required = true)
    private LocalDateTime timestamp;    

    @JsonProperty(value = "alpha", required = true)
    private Double alpha;

    @JsonProperty(value = "beta", required = true)
    private Double beta;

    @JsonProperty(value = "gamma", required = true)
    private Double gamma;

    public GyroscopeDataResponseDTO() {

    }

    public GyroscopeDataResponseDTO(LocalDateTime timestamp, Double alpha, Double beta, Double gamma) {
        this.timestamp = timestamp;
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
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