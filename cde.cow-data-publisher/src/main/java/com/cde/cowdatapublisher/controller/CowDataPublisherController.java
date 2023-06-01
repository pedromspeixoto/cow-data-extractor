package com.cde.cowdatapublisher.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.cde.utils.dto.SensorDataDTO;
import com.cde.utils.dto.accelerometer.AccelerometerDataDTO;
import com.cde.utils.dto.gps.GPSDataDTO;
import com.cde.utils.dto.gyroscope.GyroscopeDataDTO;
import com.cde.cowdatapublisher.service.CowDataPublisherService;
import com.cde.utils.models.response.ErrorResponse;
import com.cde.utils.models.response.SuccessResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class CowDataPublisherController {

    private final CowDataPublisherService cowDataService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CowDataPublisherController.class);

    public CowDataPublisherController(CowDataPublisherService cowDataService) {
        this.cowDataService = cowDataService;
    }

    // put GPS data
    @PutMapping("/gps")
    public ResponseEntity<?> putGPSData(HttpServletRequest httpRequest,
                                        @RequestHeader(name = "x-device-id") String deviceId,
                                        @Valid @RequestBody GPSDataDTO gpsDataDTO) {

        LOGGER.info("request body: " + gpsDataDTO.toString(), httpRequest);

        // check headers
        if (null == deviceId || deviceId.isEmpty()) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    "Bad request - missing device id."), HttpStatus.BAD_REQUEST);
        }

        try {
            cowDataService.putGPSData(gpsDataDTO, deviceId);
        } catch (Exception ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error posting GPS data.", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(HttpStatus.OK.value(), "GPS data posted successfully"),
                HttpStatus.OK);

    }

    // put Accelerometer data
    @PutMapping("/accelerometer")
    public ResponseEntity<?> putAccelerometerData(HttpServletRequest httpRequest,
                                                  @RequestHeader(name = "x-device-id") String deviceId,
                                                  @Valid @RequestBody AccelerometerDataDTO accelerometerDataDTO) {

        LOGGER.info("request body: " + accelerometerDataDTO.toString(), httpRequest);

        // check headers
        if (null == deviceId || deviceId.isEmpty()) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    "Bad request - missing device id."), HttpStatus.BAD_REQUEST);
        }

        try {
            cowDataService.putAccelerometerData(accelerometerDataDTO, deviceId);
        } catch (Exception ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error posting accelerometer data.", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(HttpStatus.OK.value(), "Accelerometer data posted successfully"),
                HttpStatus.OK);

    }

    // put Gyroscope data
    @PutMapping("/gyroscope")
    public ResponseEntity<?> putGyroscopeData(HttpServletRequest httpRequest,
                                              @RequestHeader(name = "x-device-id") String deviceId,
                                              @Valid @RequestBody GyroscopeDataDTO gyroscopeDataDTO) {

        LOGGER.info("request body: " + gyroscopeDataDTO.toString(), httpRequest);

        // check headers
        if (null == deviceId || deviceId.isEmpty()) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    "Bad request - missing device id."), HttpStatus.BAD_REQUEST);
        }

        try {
            cowDataService.putGyroscopeData(gyroscopeDataDTO, deviceId);
        } catch (Exception ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error posting gyroscope data.", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(HttpStatus.OK.value(), "Gyroscope data posted successfully"),
                HttpStatus.OK);

    }

    // put all sensor data
    @PutMapping("/")
    public ResponseEntity<?> putData(HttpServletRequest httpRequest,
                                     @RequestHeader(name = "x-device-id") String deviceId,
                                     @Valid @RequestBody SensorDataDTO sensorDataDTO) {

        LOGGER.info("request body: " + sensorDataDTO.toString(), httpRequest);

        // check headers
        if (null == deviceId || deviceId.isEmpty()) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    "Bad request - missing device id."), HttpStatus.BAD_REQUEST);
        }

        try {
            cowDataService.putSensorData(sensorDataDTO, deviceId);
        } catch (Exception ex) {
            LOGGER.error("error message: " + ex.getMessage(), httpRequest);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error posting sensors data.", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(HttpStatus.OK.value(), "Sensors data posted successfully"),
                HttpStatus.OK);

    }
}