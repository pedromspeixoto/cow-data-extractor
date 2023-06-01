package com.cde.cowdatavisualization.service;

import java.time.LocalDateTime;
import java.util.Optional;

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

public interface DeviceDataService {

    // put device data
    void putDeviceData(String userId, String userRoles, DeviceRequestDataDTO deviceRequestDataDTO) throws NotFoundException, ForbiddenException;

    // get all device data
    DeviceResponseAllDataDTO getAllDeviceData(String userId, String userRoles, Optional<Integer> limit, Optional<Integer> offset, Optional<String> farmId, Optional<String> deviceId, Optional<DeviceType> deviceType, Optional<DeviceStatus> deviceStatus, Optional<Boolean> includeLocation) throws ForbiddenException;

    // get single device data
    DeviceResponseDataDTO getDeviceData(String userId, String userRoles, String deviceId, Optional<Boolean> includeLocation) throws NotFoundException, ForbiddenException;

    // get latest single device activity data
    ActivityDataDTO getLatestActivityData(String userId, String userRoles, String deviceId) throws NotFoundException, NoDataFoundException, ForbiddenException;

    // get accelerometer data by time interval
    AccelerometerResponseAllDataDTO getDeviceAccelerometerData (String userId, String userRoles, String deviceId, Optional<LocalDateTime> startHour, Optional<LocalDateTime> endHour, Optional<Integer> limit, Optional<Integer> offset) throws NotFoundException, ForbiddenException;

    // get gyroscope data by time interval
    GyroscopeResponseAllDataDTO getDeviceGyroscopeData (String userId, String userRoles, String deviceId, Optional<LocalDateTime> startHour, Optional<LocalDateTime> endHour, Optional<Integer> limit, Optional<Integer> offset) throws NotFoundException, ForbiddenException;

    // get gps data by time interval
    GPSResponseAllDataDTO getDeviceGPSData (String userId, String userRoles, String deviceId, Optional<LocalDateTime> startHour, Optional<LocalDateTime> endHour, Optional<Integer> limit, Optional<Integer> offset) throws NotFoundException, ForbiddenException;

    // get activity data by time interval
    ActivityResponseAllDataDTO getDeviceActivityData (String userId, String userRoles, String deviceId, Optional<LocalDateTime> startHour, Optional<LocalDateTime> endHour, Optional<Integer> limit, Optional<Integer> offset) throws NotFoundException, ForbiddenException;

}