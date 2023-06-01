package com.cde.cowdataconsumer.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.cde.utils.dto.accelerometer.AccelerometerDataKafkaDataDTO;
import com.cde.utils.dto.activity.ActivityDataKafkaDTO;
import com.cde.utils.dto.gps.GPSKafkaDataDTO;
import com.cde.utils.dto.gyroscope.GyroscopeDataKafkaDataDTO;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${kafka.bootstrap.address}")
    private String bootstrapAddress;

    public ConsumerFactory<String, String> consumerFactory(String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(String groupId) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(groupId));
        return factory;
    }

    // gps configuration
    public ConsumerFactory<String, GPSKafkaDataDTO> gpsDataConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(GPSKafkaDataDTO.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, GPSKafkaDataDTO> gpsDataKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, GPSKafkaDataDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(gpsDataConsumerFactory());
        return factory;
    }

    // accelerometer configuration
    public ConsumerFactory<String, AccelerometerDataKafkaDataDTO> accelerometerDataConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(AccelerometerDataKafkaDataDTO.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AccelerometerDataKafkaDataDTO> accelerometerDataKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AccelerometerDataKafkaDataDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(accelerometerDataConsumerFactory());
        return factory;
    }

    // gyroscope configuration
    public ConsumerFactory<String, GyroscopeDataKafkaDataDTO> gyroscopeDataConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(GyroscopeDataKafkaDataDTO.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, GyroscopeDataKafkaDataDTO> gyroscopeDataKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, GyroscopeDataKafkaDataDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(gyroscopeDataConsumerFactory());
        return factory;
    }

    // activity configuration
    public ConsumerFactory<String, ActivityDataKafkaDTO> activityDataConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(ActivityDataKafkaDTO.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ActivityDataKafkaDTO> activityDataKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ActivityDataKafkaDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(activityDataConsumerFactory());
        return factory;
    }

}