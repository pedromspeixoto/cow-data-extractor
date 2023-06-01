package com.cde.cowdatapublisher.service;

import com.cde.utils.dto.SensorDataDTO;
import com.cde.utils.dto.accelerometer.AccelerometerDataDTO;
import com.cde.utils.dto.gps.GPSDataDTO;
import com.cde.utils.dto.gyroscope.GyroscopeDataDTO;

public interface CowDataPublisherService {

    // put gps data
    void putGPSData(GPSDataDTO gpsDataDTO, String deviceId);

    // put accelerometer data
    void putAccelerometerData(AccelerometerDataDTO accelerometerDataDTO, String deviceId);

    // put gyroscope data
    void putGyroscopeData(GyroscopeDataDTO gyroscopeDataDTO, String deviceId);

    // put sensor data
    void putSensorData(SensorDataDTO sensorDataDTO, String deviceId);

}