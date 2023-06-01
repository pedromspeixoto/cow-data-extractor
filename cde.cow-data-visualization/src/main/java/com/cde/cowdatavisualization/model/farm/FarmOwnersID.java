package com.cde.cowdatavisualization.model.farm;

import java.io.Serializable;

public class FarmOwnersID implements Serializable {

	private static final long serialVersionUID = 1L;
    private String farmId;
    private String ownerId;

    public FarmOwnersID() {
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

}