package com.cde.cowdatavisualization.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.cde.cowdatavisualization.grpc.GRPCServerServiceClient;
import com.cde.cowdatavisualization.model.farm.FarmData;
import com.cde.cowdatavisualization.model.farm.FarmOwners;
import com.cde.cowdatavisualization.repository.farm.FarmOwnersRepository;
import com.cde.cowdatavisualization.repository.farm.FarmRepository;
import com.cde.cowdatavisualization.service.FarmDataService;
import com.cde.utils.dto.farm.FarmOwnerResponseDTO;
import com.cde.utils.dto.farm.FarmRequestDataDTO;
import com.cde.utils.dto.farm.FarmResponseAllDataDTO;
import com.cde.utils.dto.farm.FarmResponseDataDTO;
import com.cde.utils.enums.auth.Roles;
import com.cde.utils.exceptions.ForbiddenException;
import com.cde.utils.exceptions.NotFoundException;
import com.cde.utils.models.PageDetails;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FarmDataServiceImpl implements FarmDataService{

    private static final Integer DEFAULT_VALUE_LIMIT = 100;
    private static final Integer DEFAULT_VALUE_OFFSET = 0;

    @Autowired
    GRPCServerServiceClient grpcServerServiceClient;

    @Autowired
    FarmRepository farmRepository;

    @Autowired
    FarmOwnersRepository farmOwnersRepository;

    @Override
    @Transactional
    public void putFarmData(String userId, String roles, FarmRequestDataDTO farmRequestDataDTO) throws ForbiddenException {
        ModelMapper modelMapper = new ModelMapper();
        // if new farm add owner and save
        if (!farmRepository.findByFarmId(farmRequestDataDTO.getFarmId()).isPresent()) {
            farmOwnersRepository.save(new FarmOwners(farmRequestDataDTO.getFarmId(), userId));
            farmRepository.save(modelMapper.map(farmRequestDataDTO, FarmData.class));
        } else {
            if (!hasAccess(userId, roles, Optional.of(farmRequestDataDTO.getFarmId())))
                throw new ForbiddenException();
            farmRepository.save(modelMapper.map(farmRequestDataDTO, FarmData.class));
        }
    }

    @Override
    public FarmResponseAllDataDTO getAllFarmData(String userId, String roles, Optional<String> farmId, Optional<Integer> limit, Optional<Integer> offset) throws ForbiddenException {

        if (!hasAccess(userId, roles, farmId))
            throw new ForbiddenException();

        Pageable pageable = PageRequest.of(offset.orElse(DEFAULT_VALUE_OFFSET), limit.orElse(DEFAULT_VALUE_LIMIT));
        ModelMapper modelMapper = new ModelMapper();
        Page<FarmData> farmDataPage = farmRepository.findAllFarms(farmId.orElse("") ,pageable);
        FarmResponseAllDataDTO farmResponseDataDTO = new FarmResponseAllDataDTO(farmDataPage.getContent().stream()
                                                                                                         .map(device -> modelMapper.map(device, FarmResponseDataDTO.class))
                                                                                                         .collect(Collectors.toList()),
                                                                                new PageDetails(limit.orElse(DEFAULT_VALUE_LIMIT),
                                                                                                offset.orElse(DEFAULT_VALUE_OFFSET),
                                                                                                farmDataPage.getTotalElements()));
        
        return farmResponseDataDTO;
    }

    @Override
    public FarmResponseDataDTO getFarmData(String userId, String roles, String farmId) throws ForbiddenException, NotFoundException {
        if (!hasAccess(userId, roles, Optional.of(farmId)))
            throw new ForbiddenException();

        ModelMapper modelMapper = new ModelMapper();
        Optional<FarmData> optionalFarmData = farmRepository.findByFarmId(farmId);

        if (!optionalFarmData.isPresent())
            throw new NotFoundException(farmId);
        
        return modelMapper.map(farmRepository.findByFarmId(farmId).get(), FarmResponseDataDTO.class);
        
    }

    @Override
    public Boolean isFarmOwner(String farmId, String ownerId) {
        if (farmOwnersRepository.findByFarmIdAndOwnerId(farmId, ownerId).isPresent())
            return true;
        return false;
    }

    @Override
    public List<FarmOwnerResponseDTO> getFarmOwners(String farmId, String ownerId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean hasAccess(String userId, String roles, Optional<String> farmId){
        List<String> roleList = new ArrayList<String>(Arrays.asList(roles.split(",")));

        Boolean isAdmin = roleList.stream().anyMatch(role -> Objects.equals(role, Roles.ROLE_ADMIN.toString()));
        Boolean isFarmer = roleList.stream().anyMatch(role -> Objects.equals(role, Roles.ROLE_FARMER.toString()));

        if (isAdmin)
            return true;

        if (isFarmer && isFarmOwner(farmId.orElse(""), userId))
            return true;

        return false;
    }

}