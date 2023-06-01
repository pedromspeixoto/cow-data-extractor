package com.cde.utils.dto.cow;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class CowRequestDataDTO {

    @JsonProperty(value = "cowName", required = false)
    private String cowName;

    @JsonProperty(value = "cowDescription", required = false)
    private String cowDescription;

    @JsonProperty(value = "cowPicture", required = false)
    private byte[] cowPicture;

    @JsonProperty(value = "birthDate", required = false)
    private LocalDate birthDate;

    @JsonProperty(value = "weight", required = false)
    private Double weight;

    public CowRequestDataDTO() {

    }

    public CowRequestDataDTO(String cowName, String cowDescription, byte[] cowPicture, LocalDate birthDate, Double weight) {
        this.cowName = cowName;
        this.cowDescription = cowDescription;
        this.cowPicture = cowPicture;
        this.birthDate = birthDate;
        this.weight = weight;
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