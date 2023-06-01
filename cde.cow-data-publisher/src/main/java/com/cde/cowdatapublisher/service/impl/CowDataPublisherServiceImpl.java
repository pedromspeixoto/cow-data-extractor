package com.cde.cowdatapublisher.service.impl;

import com.cde.utils.dto.SensorDataDTO;
import com.cde.utils.dto.accelerometer.AccelerometerDataDTO;
import com.cde.utils.dto.accelerometer.AccelerometerDataKafkaDataDTO;
import com.cde.utils.dto.gps.GPSDataDTO;
import com.cde.utils.dto.gps.GPSKafkaDataDTO;
import com.cde.utils.dto.gyroscope.GyroscopeDataDTO;
import com.cde.utils.dto.gyroscope.GyroscopeDataKafkaDataDTO;
import com.cde.utils.dto.motion.MotionKafkaDataDTO;
import com.cde.cowdatapublisher.service.CowDataPublisherService;
import com.cde.cowdatapublisher.kafka.KafkaProducer;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class CowDataPublisherServiceImpl implements CowDataPublisherService {

    Logger LOGGER = LoggerFactory.getLogger(CowDataPublisherServiceImpl.class);

    private final KafkaProducer kafkaProducer;

    public CowDataPublisherServiceImpl(final KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void putGPSData(final GPSDataDTO gpsDataDTO, final String deviceId) {

        ModelMapper modelMapper = new ModelMapper();
        LOGGER.info("Posting to Kafka: " + gpsDataDTO.toString());
        GPSKafkaDataDTO gpsKafkaDataDTO = modelMapper.map(gpsDataDTO, GPSKafkaDataDTO.class);
        gpsKafkaDataDTO.setDeviceId(deviceId);
        kafkaProducer.sendGpsData(gpsKafkaDataDTO);

    }

    
    @Override
    public void putAccelerometerData(final AccelerometerDataDTO accelerometerDataDTO, final String deviceId) {

        ModelMapper modelMapper = new ModelMapper();
        LOGGER.info("Posting to Kafka: " + accelerometerDataDTO.toString());
        AccelerometerDataKafkaDataDTO accelerometerDataKafkaDataDTO = modelMapper.map(accelerometerDataDTO, AccelerometerDataKafkaDataDTO.class);
        accelerometerDataKafkaDataDTO.setDeviceId(deviceId);
        kafkaProducer.sendAccelerometerData(accelerometerDataKafkaDataDTO);

    }

    @Override
    public void putGyroscopeData(final GyroscopeDataDTO gyroscopeDataDTO, final String deviceId) {

        ModelMapper modelMapper = new ModelMapper();
        LOGGER.info("Posting to Kafka: " + gyroscopeDataDTO.toString());
        GyroscopeDataKafkaDataDTO gyroscopeDataKafkaDataDTO = modelMapper.map(gyroscopeDataDTO, GyroscopeDataKafkaDataDTO.class);
        gyroscopeDataKafkaDataDTO.setDeviceId(deviceId);
        kafkaProducer.sendGyroscopeData(gyroscopeDataKafkaDataDTO);

    }

    @Override
    public void putSensorData(final SensorDataDTO sensorDataDTO, final String deviceId) {

        ModelMapper modelMapper = new ModelMapper();

        // map and publish gps data
        if (null != sensorDataDTO.getGpsCoordinates()) {
            GPSDataDTO gpsDataDTO = modelMapper.map(sensorDataDTO, GPSDataDTO.class);
            this.putGPSData(gpsDataDTO, deviceId);
        }

        // map and publish accelerometer data
        if (null != sensorDataDTO.getAccelerometer()) {
            AccelerometerDataDTO accelerometerDataDTO = modelMapper.map(sensorDataDTO, AccelerometerDataDTO.class);
            this.putAccelerometerData(accelerometerDataDTO, deviceId);
        }

        // map and publish gyroscope data
        if (null != sensorDataDTO.getGyroscope()) {
            GyroscopeDataDTO gyroscopeDataDTO = modelMapper.map(sensorDataDTO, GyroscopeDataDTO.class);
            this.putGyroscopeData(gyroscopeDataDTO, deviceId);
        }

        // if applicable, publish to motion topic
        if (null != sensorDataDTO.getGyroscope() && null != sensorDataDTO.getAccelerometer()) {
            // send motion data to kafka
            MotionKafkaDataDTO motionKafkaDataDTO = new MotionKafkaDataDTO(deviceId, sensorDataDTO.getTimestamp(), sensorDataDTO.getAccelerometer(), sensorDataDTO.getGyroscope());
            kafkaProducer.sendMotionData(motionKafkaDataDTO);
        }

    }

}