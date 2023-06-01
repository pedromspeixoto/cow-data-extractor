package com.cde.cowdatavisualization.model.cow;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cde.utils.dto.device.DeviceRequestDataDTO;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Table(name = "cow_data")
public class CowData {

    @Id
    private String cowId;
    private String deviceId;
    private String cowName;
    private String cowDescription;
    private byte[] cowPicture;
    private LocalDate birthDate;
    private Double weight;

    public CowData() {

    }

    public CowData(String cowId, String deviceId, String cowName, String cowDescription, byte[] cowPicture, LocalDate birthDate, Double weight) {
        this.cowId = cowId;
        this.deviceId = deviceId;
        this.cowName = cowName;
        this.cowDescription = cowDescription;
        this.cowPicture = cowPicture;
        this.birthDate = birthDate;
        this.weight = weight;
    }

    public CowData(DeviceRequestDataDTO deviceRequestDataDTO){
        this.cowId = UUID.randomUUID().toString();
        this.deviceId = deviceRequestDataDTO.getDeviceId();
        this.cowDescription = deviceRequestDataDTO.getCowData().getCowDescription();
        this.cowName = deviceRequestDataDTO.getCowData().getCowName();
        this.cowPicture = deviceRequestDataDTO.getCowData().getCowPicture();
        this.birthDate = deviceRequestDataDTO.getCowData().getBirthDate();
        this.weight = deviceRequestDataDTO.getCowData().getWeight();
    }

    public String getCowId() {
        return this.cowId;
    }

    public void setCowId(String cowId) {
        this.cowId = cowId;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCowName() {
        return this.cowName;
    }

    public void setCowName(String cowName) {
        this.cowName = cowName;
    }

    public String getCowDescription() {
        return this.cowDescription;
    }

    public void setCowDescription(String cowDescription) {
        this.cowDescription = cowDescription;
    }

    public byte[] getCowPicture() {
        return this.cowPicture;
    }

    public void setCowPicture(byte[] cowPicture) {
        this.cowPicture = cowPicture;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Double getWeight() {
        return this.weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}