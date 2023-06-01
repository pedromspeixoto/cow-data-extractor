package com.cde.cowdataprocessor.kafka;

import java.util.Properties;

import com.cde.cowdataprocessor.deeplearning4j.classifier.SNNMotionClassifier;
import com.cde.utils.dto.activity.ActivityDataKafkaDTO;
import com.cde.utils.dto.motion.MotionKafkaDataDTO;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class KafkaMotionProcessor {

    @Value(value = "${kafka.motion.input-topic.name}")
    private String inputMotionTopic;

    @Value(value = "${kafka.motion.output-topic.name}")
    private String outputMotionTopic;

    @Value(value = "${kafka.bootstrap.address}")
    private String bootstrapAddress;

    @Autowired
    SNNMotionClassifier motionClassifier;

    /*
    old method to use kafka streams binder
    @Bean
    public Function<KStream<String, MotionKafkaDataDTO>, KStream<String, ActivityDataKafkaDTO>> motionProcessor() {
        return input -> {
            return input.map((key, value) -> new KeyValue<>(key.getBytes().toString(), new ActivityDataKafkaDTO()));
        };
    }*/

    @Bean
    public void processMotionKafkaStream() {
        Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "cowdata-motion-processor");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, new JsonSerde<>(MotionKafkaDataDTO.class).getClass());
        streamsConfiguration.put(JsonDeserializer.KEY_DEFAULT_TYPE, String.class);
        streamsConfiguration.put(JsonDeserializer.VALUE_DEFAULT_TYPE, MotionKafkaDataDTO.class);

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, MotionKafkaDataDTO> motionKafkaStream = builder.stream(inputMotionTopic);

        // print for debug
        
        motionKafkaStream.foreach((key, value) -> {
            System.out.println("input");
            System.out.println("key: " + key + " device-id -> " + value.getDeviceId());

        });

        // send predicted activity to output stream
        KStream<String, ActivityDataKafkaDTO> activiyKafkaStream = motionKafkaStream
                                                                        .map((key, value) -> {
                                                                                return new KeyValue<>(key, new ActivityDataKafkaDTO(value.getDeviceId(),
                                                                                                                                    value.getTimestamp(),
                                                                                                                                    motionClassifier.classify(value.getAccelerometer().getX(),
                                                                                                                                                              value.getAccelerometer().getY(),
                                                                                                                                                              value.getAccelerometer().getZ(),
                                                                                                                                                              value.getGyroscope().getAlpha(),
                                                                                                                                                              value.getGyroscope().getBeta(),
                                                                                                                                                              value.getGyroscope().getGamma())));
                                                                        });

        // print output for debug
        System.out.println("output");
        activiyKafkaStream.foreach((key, value) -> System.out.println("key: " + key + " device-id -> " + value.getDeviceId() + " activity -> " + value.getActivity().toString()));

        activiyKafkaStream.to(outputMotionTopic);

        // start stream
        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);
        streams.start();
    }
}