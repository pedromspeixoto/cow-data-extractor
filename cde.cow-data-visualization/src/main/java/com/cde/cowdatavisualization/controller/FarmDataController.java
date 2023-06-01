package com.cde.cowdatavisualization.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.cde.utils.dto.farm.FarmRequestDataDTO;
import com.cde.utils.dto.farm.FarmResponseAllDataDTO;
import com.cde.utils.dto.farm.FarmResponseDataDTO;
import com.cde.utils.exceptions.ForbiddenException;
import com.cde.utils.exceptions.NotFoundException;
import com.cde.cowdatavisualization.service.FarmDataService;
import com.cde.utils.models.response.ErrorResponse;
import com.cde.utils.models.response.SuccessResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class FarmDataController {

    private final FarmDataService farmDataService;
    private static final Logger LOGGER = LoggerFactory.getLogger(FarmDataController.class);

    public FarmDataController(FarmDataService farmDataService) {
        this.farmDataService = farmDataService;
    }

    // get farms data 
    @GetMapping("/farms")
    public ResponseEntity<?> getFarmData(HttpServletRequest httpRequest,
                                         @RequestHeader(name = "x-user-id", required = true) String userId,
                                         @RequestHeader(name = "x-user-roles", required = true) String userRoles,
                                         @RequestParam(value = "farmId", required = false) Optional<String> farmId,
                                         @RequestParam(value = "limit", required = false) Optional<Integer> limit,
                                         @RequestParam(value = "offset", required = false) Optional<Integer> offset) {

        FarmResponseAllDataDTO farmResponseDto;

        try {
            farmResponseDto = farmDataService.getAllFarmData(userId, userRoles, farmId, limit, offset);
        } catch (ForbiddenException ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                    "User does not have access to this resource.", ex.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error fetching farm data.", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(HttpStatus.OK.value(), "Farm data retrieved successfully", farmResponseDto),
                HttpStatus.OK);

    }

    // get farm data 
    @GetMapping("/farms/{farmId}")
    public ResponseEntity<?> getFarmData(HttpServletRequest httpRequest,
                                         @RequestHeader(name = "x-user-id", required = true) String userId,
                                         @RequestHeader(name = "x-user-roles", required = true) String userRoles,
                                         @PathVariable(value = "farmId") String farmId) {

        FarmResponseDataDTO farmResponseDataDTO;

        try {
            farmResponseDataDTO = farmDataService.getFarmData(userId, userRoles, farmId);
        } catch (NotFoundException ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                    "Not found.", ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (ForbiddenException ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                    "User does not have access to this resource.", ex.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error fetching farm data.", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(HttpStatus.OK.value(), "Farm data retrieved successfully", farmResponseDataDTO),
                HttpStatus.OK);

    }

    // post farm data 
    @PutMapping("/farms")
    public ResponseEntity<?> putFarmData(HttpServletRequest httpRequest,
                                         @RequestHeader(name = "x-user-id", required = true) String userId,
                                         @RequestHeader(name = "x-user-roles", required = true) String userRoles,
                                         @Valid @RequestBody FarmRequestDataDTO farmRequestDataDTO) {

        try {
            farmDataService.putFarmData(userId, userRoles, farmRequestDataDTO);
        } catch (Exception ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error creating farm.", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(HttpStatus.OK.value(), "Farm created successfully."),
                HttpStatus.OK);

    }

}