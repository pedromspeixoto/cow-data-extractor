package com.cde.cowdataconsumer.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cde.cowdataconsumer.model.accelerometer.AccelerometerData;
import com.cde.cowdataconsumer.model.activity.ActivityData;
import com.cde.cowdataconsumer.model.gps.GPSData;
import com.cde.cowdataconsumer.model.gyroscope.GyroscopeData;
import com.cde.cowdataconsumer.repository.accelerometer.AccelerometerRepository;
import com.cde.cowdataconsumer.repository.activity.ActivityRepository;
import com.cde.cowdataconsumer.repository.gps.GPSRepository;
import com.cde.cowdataconsumer.repository.gyroscope.GyroscopeRepository;
import com.cde.cowdataconsumer.service.CowDataConsumerService;
import com.cde.utils.dto.accelerometer.AccelerometerDataKafkaDataDTO;
import com.cde.utils.dto.accelerometer.AccelerometerDataResponseDTO;
import com.cde.utils.dto.accelerometer.AccelerometerResponseAllDataDTO;
import com.cde.utils.dto.activity.ActivityDataDTO;
import com.cde.utils.dto.activity.ActivityDataKafkaDTO;
import com.cde.utils.dto.activity.ActivityDataResponseDTO;
import com.cde.utils.dto.activity.ActivityResponseAllDataDTO;
import com.cde.utils.dto.gps.GPSDataDTO;
import com.cde.utils.dto.gps.GPSDataResponseDTO;
import com.cde.utils.dto.gps.GPSKafkaDataDTO;
import com.cde.utils.dto.gps.GPSResponseAllDataDTO;
import com.cde.utils.dto.gyroscope.GyroscopeDataKafkaDataDTO;
import com.cde.utils.dto.gyroscope.GyroscopeDataResponseDTO;
import com.cde.utils.dto.gyroscope.GyroscopeResponseAllDataDTO;
import com.cde.utils.enums.activity.ActivityDescription;
import com.cde.utils.models.PageDetails;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CowDataConsumerServiceImpl implements CowDataConsumerService {

    Logger LOGGER = LoggerFactory.getLogger(CowDataConsumerServiceImpl.class);

    private static final Integer DEFAULT_VALUE_LIMIT = 60;
    private static final Integer DEFAULT_VALUE_OFFSET = 0;

    private final GPSRepository gpsDataRepository;
    private final AccelerometerRepository accelerometerDataRepository;
    private final GyroscopeRepository gyroscopeDataRepository;
    private final ActivityRepository activityDataRepository;

    public CowDataConsumerServiceImpl(final GPSRepository gpsDataRepository,
                                      final AccelerometerRepository accelerometerDataRepository,
                                      final GyroscopeRepository gyroscopeDataRepository,
                                      final ActivityRepository activityDataRepository) {
        this.gpsDataRepository = gpsDataRepository;
        this.accelerometerDataRepository = accelerometerDataRepository;
        this.gyroscopeDataRepository = gyroscopeDataRepository;
        this.activityDataRepository = activityDataRepository;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void saveGPSData(final GPSKafkaDataDTO gpsKafkaDataDTO) {
        GPSData gpsData = new GPSData(gpsKafkaDataDTO);
        LOGGER.info("Saving gps data: " + gpsData.toString());
        gpsDataRepository.save(gpsData);

    }

    @Override
    public void saveAccelerometerData(final AccelerometerDataKafkaDataDTO accelerometerDataKafkaDataDTO) {
        AccelerometerData accelerometerData = new AccelerometerData(accelerometerDataKafkaDataDTO);
        LOGGER.info("Saving accelerometer data: " + accelerometerData.toString());
        accelerometerDataRepository.save(accelerometerData);
    }

    @Override
    public void saveGyroscopeData(final GyroscopeDataKafkaDataDTO gyroscopeDataKafkaDataDTO) {
        GyroscopeData gyroscopeData = new GyroscopeData(gyroscopeDataKafkaDataDTO);
        LOGGER.info("Saving gyroscope data: " + gyroscopeData.toString());
        gyroscopeDataRepository.save(gyroscopeData);
    }

    @Override
    public void saveActivityData(final ActivityDataKafkaDTO activityDataKafkaDTO) {
        ActivityData activityData = new ActivityData(activityDataKafkaDTO);
        LOGGER.info("Saving activity data: " + activityData.toString());
        activityDataRepository.save(activityData);
    }

    @Override
    public ActivityDataDTO getDeviceLatestActivity(String deviceId) {
        Optional<ActivityData> activityData = activityDataRepository.findLatestByDeviceId(deviceId);
        if (activityData.isPresent()) {
            return new ActivityDataDTO(activityData.get().getTimestamp(),
                                       ActivityDescription.valueOf(activityData.get().getActivity()));
        }
        return null;
    }

    @Override
    public GPSDataDTO getDeviceLatestGpsData(String deviceId) {
        Optional<GPSData> gpsData = gpsDataRepository.findLatestByDeviceId(deviceId);
        if (gpsData.isPresent()) {
            return new GPSDataDTO(gpsData.get().getTimestamp().toString(), gpsData.get().getLatitude(), gpsData.get().getLongitude());
        }
        return null;        
    }

    @Override
    public AccelerometerResponseAllDataDTO getAccelerometerData(String deviceId, LocalDateTime startHour, LocalDateTime endHour, Integer limit, Integer offset) {
        ModelMapper modelMapper = new ModelMapper();
        if (null == limit ) limit = DEFAULT_VALUE_LIMIT;
        if (null == offset ) offset = DEFAULT_VALUE_OFFSET;

        Pageable pageable = PageRequest.of(offset, limit);

        LOGGER.info("Getting accelerometer data for device: " + deviceId + ". Start timestamp: " + startHour.toString() + ". End timestamp: " + endHour.toString() + ".");
        Page<AccelerometerData> accelerometerDataPage = accelerometerDataRepository.findByDeviceIdAndTimeInterval(deviceId, startHour, endHour, pageable);

        AccelerometerResponseAllDataDTO response = new AccelerometerResponseAllDataDTO(accelerometerDataPage.getContent().stream()
                                                                                                                         .map(sample -> modelMapper.map(sample, AccelerometerDataResponseDTO.class))
                                                                                                                         .collect(Collectors.toList()),
                                                                                       new PageDetails(limit,
                                                                                                       offset,
                                                                                                       accelerometerDataPage.getTotalElements()));

        return response;
    }

    @Override
    public GyroscopeResponseAllDataDTO getGyroscopeData(String deviceId, LocalDateTime startHour, LocalDateTime endHour,
            Integer limit, Integer offset) {
        ModelMapper modelMapper = new ModelMapper();
        if (null == limit ) limit = DEFAULT_VALUE_LIMIT;
        if (null == offset ) offset = DEFAULT_VALUE_OFFSET;
        
        Pageable pageable = PageRequest.of(offset, limit);
        
        LOGGER.info("Getting gyroscope data for device: " + deviceId + ". Start timestamp: " + startHour.toString() + ". End timestamp: " + endHour.toString() + ".");
        Page<GyroscopeData> gyroscopeDataPage = gyroscopeDataRepository.findByDeviceIdAndTimeInterval(deviceId, startHour, endHour, pageable);
        
        GyroscopeResponseAllDataDTO response = new GyroscopeResponseAllDataDTO(gyroscopeDataPage.getContent().stream()
                                                                                                             .map(sample -> modelMapper.map(sample, GyroscopeDataResponseDTO.class))
                                                                                                             .collect(Collectors.toList()),
                                                                                new PageDetails(limit,
                                                                                                offset,
                                                                                                gyroscopeDataPage.getTotalElements()));
        
        return response;
    }

    @Override
    public GPSResponseAllDataDTO getGPSData(String deviceId, LocalDateTime startHour, LocalDateTime endHour,
            Integer limit, Integer offset) {
        ModelMapper modelMapper = new ModelMapper();
        if (null == limit ) limit = DEFAULT_VALUE_LIMIT;
        if (null == offset ) offset = DEFAULT_VALUE_OFFSET;
        
        Pageable pageable = PageRequest.of(offset, limit);
        
        LOGGER.info("Getting gps data for device: " + deviceId + ". Start timestamp: " + startHour.toString() + ". End timestamp: " + endHour.toString() + ".");
        Page<GPSData> gpsDataPage = gpsDataRepository.findByDeviceIdAndTimeInterval(deviceId, startHour, endHour, pageable);
        
        GPSResponseAllDataDTO response = new GPSResponseAllDataDTO(gpsDataPage.getContent().stream()
                                                                                           .map(sample -> modelMapper.map(sample, GPSDataResponseDTO.class))
                                                                                           .collect(Collectors.toList()),
                                                                   new PageDetails(limit,
                                                                                   offset,
                                                                                   gpsDataPage.getTotalElements()));
        
        return response;
    }

    @Override
    public ActivityResponseAllDataDTO getActivityData(String deviceId, LocalDateTime startHour, LocalDateTime endHour,
            Integer limit, Integer offset) {
        ModelMapper modelMapper = new ModelMapper();
        if (null == limit ) limit = DEFAULT_VALUE_LIMIT;
        if (null == offset ) offset = DEFAULT_VALUE_OFFSET;
        
        Pageable pageable = PageRequest.of(offset, limit);
        
        LOGGER.info("Getting activity data for device: " + deviceId + ". Start timestamp: " + startHour.toString() + ". End timestamp: " + endHour.toString() + ".");
        Page<ActivityData> activityDataPage = activityDataRepository.findByDeviceIdAndTimeInterval(deviceId, startHour, endHour, pageable);
        
        ActivityResponseAllDataDTO response = new ActivityResponseAllDataDTO(activityDataPage.getContent().stream()
                                                                                             .map(sample -> modelMapper.map(sample, ActivityDataResponseDTO.class))
                                                                                             .collect(Collectors.toList()),
                                                                             new PageDetails(limit,
                                                                                             offset,
                                                                                             activityDataPage.getTotalElements()));
        
        return response;
    }

}