package com.cde.utils.dto.farm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonInclude(Include.NON_EMPTY)
public class FarmOwnerResponseDTO {

    @JsonProperty(value = "ownerId", required = true)
    private String ownerId;

    @JsonProperty(value = "username", required = false)
    private String username;

    public FarmOwnerResponseDTO() {
    }

    public FarmOwnerResponseDTO(String ownerId, String username) {
        this.ownerId = ownerId;
        this.username = username;
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}