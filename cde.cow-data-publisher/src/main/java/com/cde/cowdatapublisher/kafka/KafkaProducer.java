package com.cde.cowdatapublisher.kafka;

import com.cde.utils.dto.accelerometer.AccelerometerDataKafkaDataDTO;
import com.cde.utils.dto.gps.GPSKafkaDataDTO;
import com.cde.utils.dto.gyroscope.GyroscopeDataKafkaDataDTO;
import com.cde.utils.dto.motion.MotionKafkaDataDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    @Value(value = "${kafka.gps.topic.name}")
    private String gpsDataTopic;

    @Value(value = "${kafka.accelerometer.topic.name}")
    private String accelerometerDataTopic;

    @Value(value = "${kafka.gyroscope.topic.name}")
    private String gyroscopeDataTopic;

    @Value(value = "${kafka.motion.topic.name}")
    private String motionDataTopic;

    @Autowired
    private KafkaTemplate<String, GPSKafkaDataDTO> gpsKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, AccelerometerDataKafkaDataDTO> accelerometerKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, GyroscopeDataKafkaDataDTO> gyroscopeKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, MotionKafkaDataDTO> motionKafkaTemplate;

    // gps producer
    public void sendGpsData(GPSKafkaDataDTO gpsKafkaDataDTO) {
        LOGGER.info("sending payload='{}' to topic='{}'", gpsKafkaDataDTO.toString(), gpsDataTopic);
        gpsKafkaTemplate.send(gpsDataTopic, gpsKafkaDataDTO.getTimestamp().toString(), gpsKafkaDataDTO);
    }

    // accelerometer producer
    public void sendAccelerometerData(AccelerometerDataKafkaDataDTO accelerometerDataKafkaDataDTO) {
        LOGGER.info("sending payload='{}' to topic='{}'", accelerometerDataKafkaDataDTO.toString(), accelerometerDataTopic);
        accelerometerKafkaTemplate.send(accelerometerDataTopic, accelerometerDataKafkaDataDTO.getTimestamp().toString(), accelerometerDataKafkaDataDTO);
    }

    // gyroscope producer
    public void sendGyroscopeData(GyroscopeDataKafkaDataDTO gyroscopeDataKafkaDataDTO) {
        LOGGER.info("sending payload='{}' to topic='{}'", gyroscopeDataKafkaDataDTO.toString(), gyroscopeDataTopic);
        gyroscopeKafkaTemplate.send(gyroscopeDataTopic, gyroscopeDataKafkaDataDTO.getTimestamp().toString(), gyroscopeDataKafkaDataDTO);
    }

    // motion producer
    public void sendMotionData(MotionKafkaDataDTO motionKafkaDataDTO) {
        LOGGER.info("sending payload='{}' to topic='{}'", motionKafkaDataDTO.toString(), motionDataTopic);
        motionKafkaTemplate.send(motionDataTopic, motionKafkaDataDTO.getTimestamp().toString(), motionKafkaDataDTO);
    }

}