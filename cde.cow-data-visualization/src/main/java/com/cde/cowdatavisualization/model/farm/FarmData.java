package com.cde.cowdatavisualization.model.farm;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Table(name = "farm_data")
public class FarmData {

    @Id
    private String farmId;
    private String farmDescription;
    private byte[] farmPicture;
    private String farmStatus;
    private String farmAddress;
    private Double latitude;
    private Double longitude;
    private String ownerName;
    private String ownerPhoneNumber;
    private String ownerEmail;

    public FarmData() {
    }

    public FarmData(String farmId, String farmDescription, byte[] farmPicture, String farmStatus, String farmAddress, Double latitude, Double longitude, String ownerName, String ownerPhoneNumber, String ownerEmail) {
        this.farmId = farmId;
        this.farmDescription = farmDescription;
        this.farmPicture = farmPicture;
        this.farmStatus = farmStatus;
        this.farmAddress = farmAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ownerName = ownerName;
        this.ownerPhoneNumber = ownerPhoneNumber;
        this.ownerEmail = ownerEmail;
    }

    public String getFarmId() {
        return this.farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public String getFarmDescription() {
        return this.farmDescription;
    }

    public void setFarmDescription(String farmDescription) {
        this.farmDescription = farmDescription;
    }

    public byte[] getFarmPicture() {
        return this.farmPicture;
    }

    public void setFarmPicture(byte[] farmPicture) {
        this.farmPicture = farmPicture;
    }

    public String getFarmStatus() {
        return this.farmStatus;
    }

    public void setFarmStatus(String farmStatus) {
        this.farmStatus = farmStatus;
    }

    public String getFarmAddress() {
        return this.farmAddress;
    }

    public void setFarmAddress(String farmAddress) {
        this.farmAddress = farmAddress;
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

    public String getOwnerName() {
        return this.ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhoneNumber() {
        return this.ownerPhoneNumber;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    public String getOwnerEmail() {
        return this.ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}