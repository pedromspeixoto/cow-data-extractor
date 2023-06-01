package com.cde.utils.dto.activity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.cde.utils.enums.activity.ActivityDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class ActivityDataDTO {

    @JsonProperty(value = "timestamp", required = true)
    private LocalDateTime timestamp;    

    @JsonProperty(value = "activity", required = true)
    private ActivityDescription activity;

    public ActivityDataDTO() {

    }

    public ActivityDataDTO(LocalDateTime timestamp, ActivityDescription activity) {
        this.timestamp = timestamp;
        this.activity = activity;
    }

    public ActivityDataDTO(String timestamp, String activity) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        this.timestamp = LocalDateTime.parse(timestamp, formatter);
        this.activity = ActivityDescription.valueOf(activity);
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public ActivityDescription getActivity() {
        return this.activity;
    }

    public void setActivity(ActivityDescription activity) {
        this.activity = activity;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}