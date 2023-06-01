package com.cde.cowdataconsumer.model.activity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.cde.utils.dto.activity.ActivityDataKafkaDTO;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@IdClass(ActivityID.class)
@Table(name = "activity_data")
public class ActivityData {

    @Id
    private String deviceId;
    @Id
    private LocalDate day;
    @Id
    private LocalDateTime timestamp;
    @Id
    private String activity;

    public ActivityData() {

    }

    public ActivityData(String deviceId, LocalDate day, LocalDateTime timestamp, String activity) {
        this.deviceId = deviceId;
        this.day = day;
        this.timestamp = timestamp;
        this.activity = activity;
    }

    public ActivityData(ActivityDataKafkaDTO activityDataKafkaDTO) {
        this.deviceId = activityDataKafkaDTO.getDeviceId();
        this.day = LocalDate.of(activityDataKafkaDTO.getTimestamp().getYear(),
                                activityDataKafkaDTO.getTimestamp().getMonth(),
                                activityDataKafkaDTO.getTimestamp().getDayOfMonth());
        this.timestamp = activityDataKafkaDTO.getTimestamp();
        this.activity = activityDataKafkaDTO.getActivity().toString();
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public LocalDate getDay() {
        return this.day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
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