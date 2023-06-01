package com.cde.utils.dto.activity;

import java.time.LocalDateTime;

import com.cde.utils.enums.activity.ActivityDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class ActivityDataKafkaDTO {

    @JsonProperty(value = "device_id", required = true)
    private String deviceId;   

    @JsonProperty(value = "timestamp", required = true)
    private LocalDateTime timestamp;    

    @JsonProperty(value = "activity", required = true)
    private ActivityDescription activity;

    public ActivityDataKafkaDTO() {

    }

    public ActivityDataKafkaDTO(String deviceId, LocalDateTime timestamp, ActivityDescription activity) {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.activity = activity;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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