package com.cde.utils.dto.farm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class FarmRequestDataDTO {

    @JsonProperty(value = "farmId", required = true)
    private String farmId;

    @JsonProperty(value = "farmDescription", required = false)
    private String farmDescription;

    @JsonProperty(value = "farmPicture", required = false)
    private byte[] farmPicture;

    @JsonProperty(value = "farmStatus", required = false)
    private String farmStatus;

    @JsonProperty(value = "farmAddress", required = false)
    private String farmAddress;

    @JsonProperty(value = "ownerName", required = false)
    private String ownerName;

    @JsonProperty(value = "ownerPhoneNumber", required = false)
    private String ownerPhoneNumber;

    @JsonProperty(value = "ownerEmail", required = false)
    private String ownerEmail;

    @JsonProperty(value = "latitude", required = false)
    private Double latitude;

    @JsonProperty(value = "longitude", required = false)
    private Double longitude;

    public FarmRequestDataDTO() {
    }

    public FarmRequestDataDTO(String farmId, String farmDescription, byte[] farmPicture, String farmStatus, String farmAddress, String ownerName, String ownerPhoneNumber, String ownerEmail, Double latitude, Double longitude) {
        this.farmId = farmId;
        this.farmDescription = farmDescription;
        this.farmPicture = farmPicture;
        this.farmStatus = farmStatus;
        this.farmAddress = farmAddress;
        this.ownerName = ownerName;
        this.ownerPhoneNumber = ownerPhoneNumber;
        this.ownerEmail = ownerEmail;
        this.latitude = latitude;
        this.longitude = longitude;
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