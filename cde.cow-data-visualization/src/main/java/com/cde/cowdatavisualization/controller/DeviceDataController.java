package com.cde.cowdatavisualization.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.cde.utils.dto.accelerometer.AccelerometerResponseAllDataDTO;
import com.cde.utils.dto.activity.ActivityDataDTO;
import com.cde.utils.dto.activity.ActivityResponseAllDataDTO;
import com.cde.utils.dto.device.DeviceRequestDataDTO;
import com.cde.utils.dto.device.DeviceResponseAllDataDTO;
import com.cde.utils.dto.device.DeviceResponseDataDTO;
import com.cde.utils.dto.gps.GPSResponseAllDataDTO;
import com.cde.utils.dto.gyroscope.GyroscopeResponseAllDataDTO;
import com.cde.utils.enums.device.DeviceStatus;
import com.cde.utils.enums.device.DeviceType;
import com.cde.utils.exceptions.ForbiddenException;
import com.cde.utils.exceptions.NoDataFoundException;
import com.cde.utils.exceptions.NotFoundException;
import com.cde.cowdatavisualization.service.DeviceDataService;
import com.cde.utils.models.response.ErrorResponse;
import com.cde.utils.models.response.SuccessResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class DeviceDataController {

    private final DeviceDataService deviceDataService;
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceDataController.class);

    public DeviceDataController(DeviceDataService deviceDataService) {
        this.deviceDataService = deviceDataService;
    }

    // get devices data 
    @GetMapping("/devices")
    public ResponseEntity<?> getAllDeviceData(HttpServletRequest httpRequest,
                                              @RequestHeader(name = "x-user-id", required = true) String userId,
                                              @RequestHeader(name = "x-user-roles", required = true) String userRoles,
                                              @RequestParam(value = "limit", required = false) Optional<Integer> limit,
                                              @RequestParam(value = "offset", required = false) Optional<Integer> offset,
                                              @RequestParam(value = "farmId", required = false) Optional<String> farmId,
                                              @RequestParam(value = "deviceId", required = false) Optional<String> deviceId,
                                              @RequestParam(value = "deviceType", required = false) Optional<DeviceType> deviceType,
                                              @RequestParam(value = "deviceStatus", required = false) Optional<DeviceStatus> deviceStatus,
                                              @RequestParam(value = "includeLocation", required = false) Optional<Boolean> includeLocation) {

        DeviceResponseAllDataDTO deviceResponseDataDto;

        try {
            deviceResponseDataDto = deviceDataService.getAllDeviceData(userId, userRoles, limit, offset, farmId, deviceId, deviceType, deviceStatus, includeLocation);
        } catch (ForbiddenException ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                    "Forbidden.", ex.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error fetching device data.", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } 

        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(HttpStatus.OK.value(), "Device data retrieved successfully", deviceResponseDataDto),
                HttpStatus.OK);

    }

    // get devices data 
    @GetMapping("/devices/{deviceId}")
    public ResponseEntity<?> getDeviceData(HttpServletRequest httpRequest,
                                           @RequestHeader(name = "x-user-id", required = true) String userId,
                                           @RequestHeader(name = "x-user-roles", required = true) String userRoles,
                                           @PathVariable(value = "deviceId") String deviceId,
                                           @RequestParam(value = "includeLocation", required = false) Optional<Boolean> includeLocation) {

        DeviceResponseDataDTO deviceResponseDataDto;

        try {
            deviceResponseDataDto = deviceDataService.getDeviceData(userId, userRoles, deviceId, includeLocation);
        } catch (ForbiddenException ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                    "Forbidden.", ex.getMessage()), HttpStatus.FORBIDDEN);
        }  catch (NotFoundException ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                    "Device not found.", ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error fetching device data.", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } 

        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(HttpStatus.OK.value(), "Device data retrieved successfully", deviceResponseDataDto),
                HttpStatus.OK);

    }

    // get device activity (cow only)
    @GetMapping("/devices/{deviceId}/activity/latest")
    public ResponseEntity<?> getActivityData(HttpServletRequest httpRequest,
                                             @RequestHeader(name = "x-user-id", required = true) String userId,
                                             @RequestHeader(name = "x-user-roles", required = true) String userRoles,
                                             @PathVariable(value = "deviceId") String deviceId) {

        ActivityDataDTO activityDataDto;

        try {
            activityDataDto = deviceDataService.getLatestActivityData(userId, userRoles, deviceId);
        } catch (NoDataFoundException ex) {
            LOGGER.error("message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.NO_CONTENT.value(),
                    "Activity data not found", ex.getMessage()), HttpStatus.NO_CONTENT);
        } catch (NotFoundException ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                    "Activity data not found", ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error fetching actvitiy data.", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(HttpStatus.OK.value(), "Activity data retrieved successfully", activityDataDto),
                HttpStatus.OK);

    }
    
    // put device data 
    @PutMapping("/devices")
    public ResponseEntity<?> putDeviceData(HttpServletRequest httpRequest,
                                           @RequestHeader(name = "x-user-id", required = true) String userId,
                                           @RequestHeader(name = "x-user-roles", required = true) String userRoles,
                                           @Valid @RequestBody DeviceRequestDataDTO deviceRequestDataDTO) {

        LOGGER.info("Received request: " + deviceRequestDataDTO.toString());

        try {
            deviceDataService.putDeviceData(userId, userRoles, deviceRequestDataDTO);
        } catch (ForbiddenException ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                    "Forbidden.", ex.getMessage()), HttpStatus.FORBIDDEN);
        }  catch (NotFoundException ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                    "Device not found.", ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error fetching device data.", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } 

        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(HttpStatus.OK.value(), "Device data created successfully."),
                HttpStatus.OK);

    }

    // get device accelerometer data with filter 
    @GetMapping("/devices/{deviceId}/accelerometer")
    public ResponseEntity<?> getDeviceAccelerometerData(HttpServletRequest httpRequest,
                                                        @RequestHeader(name = "x-user-id", required = true) String userId,
                                                        @RequestHeader(name = "x-user-roles", required = true) String userRoles,
                                                        @PathVariable(value = "deviceId") String deviceId,
                                                        @RequestParam(value = "startHour", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> startHour,
                                                        @RequestParam(value = "endHour", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> endHour,
                                                        @RequestParam(value = "limit", required = false) Optional<Integer> limit,
                                                        @RequestParam(value = "offset", required = false) Optional<Integer> offset) {

        AccelerometerResponseAllDataDTO accelerometerData;

        try {
            accelerometerData = deviceDataService.getDeviceAccelerometerData(userId, userRoles, deviceId, startHour, endHour, limit, offset);
        } catch (ForbiddenException ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                    "Forbidden.", ex.getMessage()), HttpStatus.FORBIDDEN);
        }  catch (NotFoundException ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                    "Device not found.", ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error fetching device data.", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } 

        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(HttpStatus.OK.value(), "Device data retrieved successfully", accelerometerData),
                HttpStatus.OK);

    }

    // get device gyroscope data with filter 
    @GetMapping("/devices/{deviceId}/gyroscope")
    public ResponseEntity<?> getDeviceGyroscopeData(HttpServletRequest httpRequest,
                                                    @RequestHeader(name = "x-user-id", required = true) String userId,
                                                    @RequestHeader(name = "x-user-roles", required = true) String userRoles,
                                                    @PathVariable(value = "deviceId") String deviceId,
                                                    @RequestParam(value = "startHour", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> startHour,
                                                    @RequestParam(value = "endHour", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> endHour,
                                                    @RequestParam(value = "limit", required = false) Optional<Integer> limit,
                                                    @RequestParam(value = "offset", required = false) Optional<Integer> offset) {

        GyroscopeResponseAllDataDTO gyroscopeData;

        try {
            gyroscopeData = deviceDataService.getDeviceGyroscopeData(userId, userRoles, deviceId, startHour, endHour, limit, offset);
        } catch (ForbiddenException ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                    "Forbidden.", ex.getMessage()), HttpStatus.FORBIDDEN);
        }  catch (NotFoundException ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                    "Device not found.", ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error fetching device data.", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } 

        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(HttpStatus.OK.value(), "Device data retrieved successfully", gyroscopeData),
                HttpStatus.OK);

    }

    // get device gps data with filter 
    @GetMapping("/devices/{deviceId}/gps")
    public ResponseEntity<?> getDeviceGPSData(HttpServletRequest httpRequest,
                                              @RequestHeader(name = "x-user-id", required = true) String userId,
                                              @RequestHeader(name = "x-user-roles", required = true) String userRoles,
                                              @PathVariable(value = "deviceId") String deviceId,
                                              @RequestParam(value = "startHour", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> startHour,
                                              @RequestParam(value = "endHour", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> endHour,
                                              @RequestParam(value = "limit", required = false) Optional<Integer> limit,
                                              @RequestParam(value = "offset", required = false) Optional<Integer> offset) {

        GPSResponseAllDataDTO gpsData;

        try {
            gpsData = deviceDataService.getDeviceGPSData(userId, userRoles, deviceId, startHour, endHour, limit, offset);
        } catch (ForbiddenException ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                    "Forbidden.", ex.getMessage()), HttpStatus.FORBIDDEN);
        }  catch (NotFoundException ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                    "Device not found.", ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error fetching device data.", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } 

        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(HttpStatus.OK.value(), "Device data retrieved successfully", gpsData),
                HttpStatus.OK);

    }

    // get device activity data with filter 
    @GetMapping("/devices/{deviceId}/activity")
    public ResponseEntity<?> getDeviceActivityData(HttpServletRequest httpRequest,
                                                   @RequestHeader(name = "x-user-id", required = true) String userId,
                                                   @RequestHeader(name = "x-user-roles", required = true) String userRoles,
                                                   @PathVariable(value = "deviceId") String deviceId,
                                                   @RequestParam(value = "startHour", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> startHour,
                                                   @RequestParam(value = "endHour", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> endHour,
                                                   @RequestParam(value = "limit", required = false) Optional<Integer> limit,
                                                   @RequestParam(value = "offset", required = false) Optional<Integer> offset) {

        ActivityResponseAllDataDTO activityData;

        try {
            activityData = deviceDataService.getDeviceActivityData(userId, userRoles, deviceId, startHour, endHour, limit, offset);
        } catch (ForbiddenException ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                    "Forbidden.", ex.getMessage()), HttpStatus.FORBIDDEN);
        }  catch (NotFoundException ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                    "Device not found.", ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error fetching device data.", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } 

        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(HttpStatus.OK.value(), "Device data retrieved successfully", activityData),
                HttpStatus.OK);

    }

}