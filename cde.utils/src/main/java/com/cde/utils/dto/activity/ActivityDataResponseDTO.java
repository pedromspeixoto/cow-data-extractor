package com.cde.utils.dto.activity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class ActivityDataResponseDTO {

    @JsonProperty(value = "timestamp", required = true)
    private LocalDateTime timestamp;    

    @JsonProperty(value = "activity", required = true)
    private String activity;

    public ActivityDataResponseDTO() {

    }

    public ActivityDataResponseDTO(LocalDateTime timestamp, String activity) {
        this.timestamp = timestamp;
        this.activity = activity;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getActivity() {
        return this.activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}