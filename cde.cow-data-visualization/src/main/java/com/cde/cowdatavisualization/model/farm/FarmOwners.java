package com.cde.cowdatavisualization.model.farm;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@IdClass(FarmOwnersID.class)
@Table(name = "farm_owners")
public class FarmOwners {

    @Id
    private String farmId;

    @Id
    private String ownerId;

    public FarmOwners() {

    }

    public FarmOwners(String farmId, String ownerId) {
        this.farmId = farmId;
        this.ownerId = ownerId;
    }

    public String getFarmId() {
        return this.farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}