package com.cde.cowdatavisualization.service;

import java.util.List;
import java.util.Optional;

import com.cde.utils.dto.farm.FarmOwnerResponseDTO;
import com.cde.utils.dto.farm.FarmRequestDataDTO;
import com.cde.utils.dto.farm.FarmResponseAllDataDTO;
import com.cde.utils.dto.farm.FarmResponseDataDTO;
import com.cde.utils.exceptions.ForbiddenException;
import com.cde.utils.exceptions.NotFoundException;

public interface FarmDataService {

    // post farm data
    void putFarmData(String userId, String roles, FarmRequestDataDTO farmRequestDataDTO) throws ForbiddenException;

    // get farm data
    FarmResponseAllDataDTO getAllFarmData(String userId, String roles, Optional<String> farmId, Optional<Integer> limit, Optional<Integer> offset) throws ForbiddenException;

    // get single farm data
    FarmResponseDataDTO getFarmData(String userId, String roles, String farmId) throws ForbiddenException, NotFoundException;

    // get farm owners
    List<FarmOwnerResponseDTO> getFarmOwners(String farmId, String ownerId);

    // is farm owner
    Boolean isFarmOwner(String farmId, String ownerId);

    // check if user has access to farm resources
    Boolean hasAccess(String userId, String roles, Optional<String> farmId);

}