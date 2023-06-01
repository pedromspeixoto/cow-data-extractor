package com.cde.cowdataconsumer.service;

import java.time.LocalDateTime;

import com.cde.utils.dto.accelerometer.AccelerometerDataKafkaDataDTO;
import com.cde.utils.dto.accelerometer.AccelerometerResponseAllDataDTO;
import com.cde.utils.dto.activity.ActivityDataDTO;
import com.cde.utils.dto.activity.ActivityDataKafkaDTO;
import com.cde.utils.dto.activity.ActivityResponseAllDataDTO;
import com.cde.utils.dto.gps.GPSDataDTO;
import com.cde.utils.dto.gps.GPSKafkaDataDTO;
import com.cde.utils.dto.gps.GPSResponseAllDataDTO;
import com.cde.utils.dto.gyroscope.GyroscopeDataKafkaDataDTO;
import com.cde.utils.dto.gyroscope.GyroscopeResponseAllDataDTO;

public interface CowDataConsumerService {

    //
    // METHODS USED TO SAVE DATA
    //

    // save gps data
    void saveGPSData(GPSKafkaDataDTO gpsKafkaDataDTO);

    // save accelerometer data
    void saveAccelerometerData(AccelerometerDataKafkaDataDTO accelerometerDataKafkaDataDTO);

    // save gyroscope data
    void saveGyroscopeData(GyroscopeDataKafkaDataDTO gyroscopeDataKafkaDataDTO);

    // save activity data
    void saveActivityData(ActivityDataKafkaDTO activityDataKafkaDTO);

    //
    // METHODS USED TO GET DATA
    //

    ActivityDataDTO getDeviceLatestActivity(String deviceId);

    GPSDataDTO getDeviceLatestGpsData(String deviceId);

    AccelerometerResponseAllDataDTO getAccelerometerData(String deviceId, LocalDateTime startHour, LocalDateTime endHour, Integer limit, Integer offset);

    GyroscopeResponseAllDataDTO getGyroscopeData(String deviceId, LocalDateTime startHour, LocalDateTime endHour, Integer limit, Integer offset);

    GPSResponseAllDataDTO getGPSData(String deviceId, LocalDateTime startHour, LocalDateTime endHour, Integer limit, Integer offset);

    ActivityResponseAllDataDTO getActivityData(String deviceId, LocalDateTime startHour, LocalDateTime endHour, Integer limit, Integer offset);

}