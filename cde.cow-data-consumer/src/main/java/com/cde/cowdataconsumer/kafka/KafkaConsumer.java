package com.cde.cowdataconsumer.kafka;

import java.util.concurrent.CountDownLatch;

import com.cde.cowdataconsumer.service.CowDataConsumerService;
import com.cde.utils.dto.accelerometer.AccelerometerDataKafkaDataDTO;
import com.cde.utils.dto.activity.ActivityDataKafkaDTO;
import com.cde.utils.dto.gps.GPSKafkaDataDTO;
import com.cde.utils.dto.gyroscope.GyroscopeDataKafkaDataDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @Value(value = "${kafka.gps.topic.name}")
    private String gpsDataTopic;

    @Value(value = "${kafka.accelerometer.topic.name}")
    private String accelerometerDataTopic;

    @Value(value = "${kafka.gyroscope.topic.name}")
    private String gyroscopeDataTopic;

    @Value(value = "${kafka.activity.topic.name}")
    private String activityDataTopic;

    private CountDownLatch gpsLatch = new CountDownLatch(1);

    private CountDownLatch accelerometerLatch = new CountDownLatch(1);

    private CountDownLatch gyroscopeLatch = new CountDownLatch(1);

    private CountDownLatch activityLatch = new CountDownLatch(1);

    @Autowired
    CowDataConsumerService cowDataConsumerService;

    // gps topic consumer
    @KafkaListener(topics = "${kafka.gps.topic.name}", containerFactory = "gpsDataKafkaListenerContainerFactory")
    public void gpsListener(GPSKafkaDataDTO gpsKafkaDataDTO) {
        LOGGER.info("Received gps data: " + gpsKafkaDataDTO.toString());
        cowDataConsumerService.saveGPSData(gpsKafkaDataDTO);
        this.gpsLatch.countDown();
    }

    // accelerometer topic consumer
    @KafkaListener(topics = "${kafka.accelerometer.topic.name}", containerFactory = "accelerometerDataKafkaListenerContainerFactory")
    public void accelerometerListener(AccelerometerDataKafkaDataDTO accelerometerDataKafkaDataDTO) {
        LOGGER.info("Received accelerometer data: " + accelerometerDataKafkaDataDTO.toString());
        cowDataConsumerService.saveAccelerometerData(accelerometerDataKafkaDataDTO);
        this.accelerometerLatch.countDown();
    }    

    // gyroscope topic consumer
    @KafkaListener(topics = "${kafka.gyroscope.topic.name}", containerFactory = "gyroscopeDataKafkaListenerContainerFactory")
    public void gyroscopeListener(GyroscopeDataKafkaDataDTO gyroscopeDataKafkaDataDTO) {
        LOGGER.info("Received gyroscope data: " + gyroscopeDataKafkaDataDTO.toString());
        cowDataConsumerService.saveGyroscopeData(gyroscopeDataKafkaDataDTO);
        this.gyroscopeLatch.countDown();
    }

    // activity topic consumer
    @KafkaListener(topics = "${kafka.activity.topic.name}", containerFactory = "activityDataKafkaListenerContainerFactory")
    public void activityListener(ActivityDataKafkaDTO activityDataKafkaDTO) {
        LOGGER.info("Received activity data: " + activityDataKafkaDTO.toString());
        cowDataConsumerService.saveActivityData(activityDataKafkaDTO);
        this.activityLatch.countDown();
    }

}