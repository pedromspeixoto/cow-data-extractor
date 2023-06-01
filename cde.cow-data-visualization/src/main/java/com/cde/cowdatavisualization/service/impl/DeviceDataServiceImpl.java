package com.cde.cowdatavisualization.service.impl;

import com.cde.utils.dto.accelerometer.AccelerometerResponseAllDataDTO;
import com.cde.utils.dto.activity.ActivityDataDTO;
import com.cde.utils.dto.activity.ActivityResponseAllDataDTO;
import com.cde.utils.dto.cow.CowResponseDataDTO;
import com.cde.utils.dto.device.CapabilitiesResponseDataDTO;
import com.cde.utils.dto.device.DeviceRequestDataDTO;
import com.cde.utils.dto.device.DeviceResponseAllDataDTO;
import com.cde.utils.dto.device.DeviceResponseDataDTO;
import com.cde.utils.dto.gps.GPSDataDTO;
import com.cde.utils.dto.gps.GPSResponseAllDataDTO;
import com.cde.utils.dto.gyroscope.GyroscopeResponseAllDataDTO;
import com.cde.utils.enums.device.DeviceStatus;
import com.cde.utils.enums.device.DeviceType;
import com.cde.utils.exceptions.ForbiddenException;
import com.cde.utils.exceptions.NoDataFoundException;
import com.cde.utils.exceptions.NotFoundException;
import com.cde.utils.models.PageDetails;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cde.cowdatavisualization.grpc.GRPCServerServiceClient;
import com.cde.cowdatavisualization.model.cow.CowData;
import com.cde.cowdatavisualization.model.device.DeviceCapabilities;
import com.cde.cowdatavisualization.model.device.DeviceData;
import com.cde.cowdatavisualization.repository.device.DeviceCapabilitiesRepository;
import com.cde.cowdatavisualization.repository.device.DeviceRepository;
import com.cde.cowdatavisualization.repository.device.cow.CowRepository;
import com.cde.cowdatavisualization.service.DeviceDataService;
import com.cde.cowdatavisualization.service.FarmDataService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeviceDataServiceImpl implements DeviceDataService {

    Logger LOGGER = LoggerFactory.getLogger(DeviceDataServiceImpl.class);

    private static final Integer DEFAULT_VALUE_LIMIT = 100;
    private static final Integer DEFAULT_VALUE_OFFSET = 0;

    @Autowired
    GRPCServerServiceClient grpcServerServiceClient;

    @Autowired
    FarmDataService farmDataService;

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    DeviceCapabilitiesRepository capabilitiesRepository;

    @Autowired
    CowRepository cowRepository;

    @Override
    public DeviceResponseAllDataDTO getAllDeviceData(String userId, String userRoles, Optional<Integer> limit, Optional<Integer> offset, 
                                                     Optional<String> farmId, Optional<String> deviceId, Optional<DeviceType> deviceType,
                                                     Optional<DeviceStatus> deviceStatus, Optional<Boolean> includeLocation) throws ForbiddenException {

        if (!farmDataService.hasAccess(userId, userRoles, farmId))
            throw new ForbiddenException();

        Pageable pageable = PageRequest.of(offset.orElse(DEFAULT_VALUE_OFFSET), limit.orElse(DEFAULT_VALUE_LIMIT));

        ModelMapper modelMapper = new ModelMapper();
                                                        
        String deviceTypeString = (deviceType.isPresent()) ? deviceType.get().toString() : "";
        String deviceStatusString = (deviceStatus.isPresent()) ? deviceStatus.get().toString() : "";

        Page<DeviceData> deviceDataPage = deviceRepository.findAllDevices(farmId.orElse(""), deviceId.orElse(""), deviceTypeString, deviceStatusString, pageable);

        DeviceResponseAllDataDTO deviceResponseDataDTO = new DeviceResponseAllDataDTO(deviceDataPage.getContent().stream()
                                                                                                                 .map(device -> {
                                                                                                                    DeviceResponseDataDTO deviceResponse = modelMapper.map(device, DeviceResponseDataDTO.class);
                                                                                                                    // location
                                                                                                                    if(includeLocation.isPresent() && includeLocation.get()) {
                                                                                                                        GPSDataDTO gpsDataDTO = grpcServerServiceClient.getLatestDeviceLocation(device.getDeviceId());
                                                                                                                        if (null != gpsDataDTO) {
                                                                                                                            deviceResponse.setLatestLatitude(gpsDataDTO.getGpsCoordinates().getLatitude());
                                                                                                                            deviceResponse.setLatestLongitude(gpsDataDTO.getGpsCoordinates().getLongitude());
                                                                                                                        }
                                                                                                                    }
                                                                                                                    // capabilities
                                                                                                                    deviceResponse = getCapabilities(deviceResponse);
                                                                                                                    // device type data
                                                                                                                    deviceResponse = getDeviceTypeData(deviceResponse);
                                                                                                                    return deviceResponse;
                                                                                                                  })
                                                                                                                 .collect(Collectors.toList()),
                                                                                      new PageDetails(limit.orElse(DEFAULT_VALUE_LIMIT),
                                                                                                      offset.orElse(DEFAULT_VALUE_OFFSET),
                                                                                                      deviceDataPage.getTotalElements())
                                                                                      );

        return deviceResponseDataDTO;
    }

    @Override
    public DeviceResponseDataDTO getDeviceData(String userId, String userRoles, String deviceId, Optional<Boolean> includeLocation) throws NotFoundException, ForbiddenException {

        if (!checkIfDeviceIdExists(deviceId))
            throw new NotFoundException(deviceId);

        if (!farmDataService.hasAccess(userId, userRoles, Optional.ofNullable(deviceRepository.findByDeviceId(deviceId).get().getFarmId())))
            throw new ForbiddenException();

        ModelMapper modelMapper = new ModelMapper();

        Optional<DeviceData> optionalDeviceData = deviceRepository.findByDeviceId(deviceId);
        DeviceData deviceData = new DeviceData();

        if (optionalDeviceData.isPresent()) {
            deviceData = optionalDeviceData.get();
        }

        DeviceResponseDataDTO deviceResponseDataDTO = modelMapper.map(deviceData, DeviceResponseDataDTO.class);
        deviceResponseDataDTO.setDeviceType(DeviceType.valueOf(deviceData.getDeviceType()));

        if (includeLocation.isPresent() && includeLocation.get()) {
            GPSDataDTO gpsDataDTO = grpcServerServiceClient.getLatestDeviceLocation(deviceId);
            if (null != gpsDataDTO) {
                deviceResponseDataDTO.setLatestTimestamp(gpsDataDTO.getTimestamp());
                deviceResponseDataDTO.setLatestLatitude(gpsDataDTO.getGpsCoordinates().getLatitude());
                deviceResponseDataDTO.setLatestLongitude(gpsDataDTO.getGpsCoordinates().getLongitude());
            }
        }

        deviceResponseDataDTO = getCapabilities(deviceResponseDataDTO);
        deviceResponseDataDTO = getDeviceTypeData(deviceResponseDataDTO);

        return deviceResponseDataDTO;
    }

    @Override
    public ActivityDataDTO getLatestActivityData(String userId, String userRoles, String deviceId) throws NotFoundException, NotFoundException, NoDataFoundException, ForbiddenException {
        if (!checkIfDeviceIdExists(deviceId))
            throw new NotFoundException(deviceId);

        if (!farmDataService.hasAccess(userId, userRoles, Optional.ofNullable(deviceRepository.findByDeviceId(deviceId).get().getFarmId())))
            throw new ForbiddenException();
        
        LOGGER.info("Getting activity data for device: " + deviceId);
        ActivityDataDTO activityDataDTO = grpcServerServiceClient.getLatestActivityData(deviceId);
        if (null == activityDataDTO) {
            throw new NoDataFoundException(deviceId);
        }
        return activityDataDTO;
    }

    @Override
    @Transactional
    public void putDeviceData(String userId, String userRoles, DeviceRequestDataDTO deviceRequestDataDTO) throws NotFoundException, ForbiddenException {

        if (!checkIfDeviceIdExists(deviceRequestDataDTO.getDeviceId()))
            throw new NotFoundException(deviceRequestDataDTO.getDeviceId());

        if (!farmDataService.hasAccess(userId, userRoles, Optional.ofNullable(deviceRepository.findByDeviceId(deviceRequestDataDTO.getDeviceId()).get().getFarmId())))
            throw new ForbiddenException();

        deviceRepository.save(new DeviceData(deviceRequestDataDTO));
        // capabilities
        if (null != deviceRequestDataDTO.getCapabilities()) {
            Optional<DeviceCapabilities> deviceCapabilities = capabilitiesRepository.findByDeviceId(deviceRequestDataDTO.getDeviceId());
            // update capabilites
            if (deviceCapabilities.isPresent()) {
                DeviceCapabilities deviceCapabilitiesObject = deviceCapabilities.get();
                if (null != deviceRequestDataDTO.getCapabilities().getAccelerometer() && deviceRequestDataDTO.getCapabilities().getAccelerometer())
                    deviceCapabilitiesObject.setAccelerometer(deviceRequestDataDTO.getCapabilities().getAccelerometer());
                if (null != deviceRequestDataDTO.getCapabilities().getGyroscope() && deviceRequestDataDTO.getCapabilities().getGyroscope())
                    deviceCapabilitiesObject.setGyroscope(deviceRequestDataDTO.getCapabilities().getGyroscope());
                if (null != deviceRequestDataDTO.getCapabilities().getGps() && deviceRequestDataDTO.getCapabilities().getGps())
                    deviceCapabilitiesObject.setGps(deviceRequestDataDTO.getCapabilities().getGps());
                if (null != deviceRequestDataDTO.getCapabilities().getTemperature() && deviceRequestDataDTO.getCapabilities().getTemperature())
                    deviceCapabilitiesObject.setTemperature(deviceRequestDataDTO.getCapabilities().getTemperature());
                if (null != deviceRequestDataDTO.getCapabilities().getHumidity() && deviceRequestDataDTO.getCapabilities().getHumidity())
                    deviceCapabilitiesObject.setHumidity(deviceRequestDataDTO.getCapabilities().getHumidity());
                capabilitiesRepository.save(deviceCapabilitiesObject);
            } else {
                // insert new capabilites
                capabilitiesRepository.save(new DeviceCapabilities(deviceRequestDataDTO));
            }
        }
        // specific type data
        setDeviceTypeData(deviceRequestDataDTO);
    }

    @Override
    public AccelerometerResponseAllDataDTO getDeviceAccelerometerData(String userId, String userRoles, String deviceId, Optional<LocalDateTime> startHour, Optional<LocalDateTime> endHour, Optional<Integer> limit, Optional<Integer> offset) throws NotFoundException, ForbiddenException {
        LOGGER.info("Getting accelerometer data for device: " + deviceId);

        if (!checkIfDeviceIdExists(deviceId))
            throw new NotFoundException(deviceId);

        if (!farmDataService.hasAccess(userId, userRoles, Optional.ofNullable(deviceRepository.findByDeviceId(deviceId).get().getFarmId())))
            throw new ForbiddenException();

        // check start timestamp filter (defaults to last hour)
        LocalDateTime startTimestamp = (startHour.isPresent() ? startHour.get() : LocalDateTime.now().minusHours(1));
        // chend end timestamp filter (defaults to now)
        LocalDateTime endTimestamp = (endHour.isPresent() ? endHour.get() : LocalDateTime.now());

        // get value from grpc server
        AccelerometerResponseAllDataDTO response = grpcServerServiceClient.getAccelerometerData(deviceId,
                                                                                                startTimestamp,
                                                                                                endTimestamp,
                                                                                                limit.orElse(DEFAULT_VALUE_LIMIT),
                                                                                                offset.orElse(DEFAULT_VALUE_OFFSET));
        return response;
    }

    @Override
    public GyroscopeResponseAllDataDTO getDeviceGyroscopeData(String userId, String userRoles, String deviceId, Optional<LocalDateTime> startHour,
            Optional<LocalDateTime> endHour, Optional<Integer> limit, Optional<Integer> offset)
            throws NotFoundException, ForbiddenException {
        LOGGER.info("Getting gyroscope data for device: " + deviceId);
        if (!checkIfDeviceIdExists(deviceId))
            throw new NotFoundException(deviceId);

        if (!farmDataService.hasAccess(userId, userRoles, Optional.ofNullable(deviceRepository.findByDeviceId(deviceId).get().getFarmId())))
            throw new ForbiddenException();

        // check start timestamp filter (defaults to last hour)
        LocalDateTime startTimestamp = (startHour.isPresent() ? startHour.get() : LocalDateTime.now().minusHours(1));
        // chend end timestamp filter (defaults to now)
        LocalDateTime endTimestamp = (endHour.isPresent() ? endHour.get() : LocalDateTime.now());

        // get value from grpc server
        GyroscopeResponseAllDataDTO response = grpcServerServiceClient.getGyroscopeData(deviceId,
                                                                                        startTimestamp,
                                                                                        endTimestamp,
                                                                                        limit.orElse(DEFAULT_VALUE_LIMIT),
                                                                                        offset.orElse(DEFAULT_VALUE_OFFSET));
        return response;
    }

    @Override
    public GPSResponseAllDataDTO getDeviceGPSData(String userId, String userRoles, String deviceId, Optional<LocalDateTime> startHour,
            Optional<LocalDateTime> endHour, Optional<Integer> limit, Optional<Integer> offset)
            throws NotFoundException, ForbiddenException {
        LOGGER.info("Getting gps data for device: " + deviceId);
        if (!checkIfDeviceIdExists(deviceId))
            throw new NotFoundException(deviceId);

        if (!farmDataService.hasAccess(userId, userRoles, Optional.ofNullable(deviceRepository.findByDeviceId(deviceId).get().getFarmId())))
            throw new ForbiddenException();

        // check start timestamp filter (defaults to last hour)
        LocalDateTime startTimestamp = (startHour.isPresent() ? startHour.get() : LocalDateTime.now().minusHours(1));
        // chend end timestamp filter (defaults to now)
        LocalDateTime endTimestamp = (endHour.isPresent() ? endHour.get() : LocalDateTime.now());

        // get value from grpc server
        GPSResponseAllDataDTO response = grpcServerServiceClient.getGPSData(deviceId,
                                                                            startTimestamp,
                                                                            endTimestamp,
                                                                            limit.orElse(DEFAULT_VALUE_LIMIT),
                                                                            offset.orElse(DEFAULT_VALUE_OFFSET));
        return response;
    }

    @Override
    public ActivityResponseAllDataDTO getDeviceActivityData(String userId, String userRoles, String deviceId, Optional<LocalDateTime> startHour,
            Optional<LocalDateTime> endHour, Optional<Integer> limit, Optional<Integer> offset)
            throws NotFoundException, ForbiddenException {
        LOGGER.info("Getting gps data for device: " + deviceId);
        if (!checkIfDeviceIdExists(deviceId))
            throw new NotFoundException(deviceId);

        if (!farmDataService.hasAccess(userId, userRoles, Optional.ofNullable(deviceRepository.findByDeviceId(deviceId).get().getFarmId())))
            throw new ForbiddenException();

        // check start timestamp filter (defaults to last hour)
        LocalDateTime startTimestamp = (startHour.isPresent() ? startHour.get() : LocalDateTime.now().minusHours(1));
        // chend end timestamp filter (defaults to now)
        LocalDateTime endTimestamp = (endHour.isPresent() ? endHour.get() : LocalDateTime.now());

        // get value from grpc server
        ActivityResponseAllDataDTO response = grpcServerServiceClient.getActivityData(deviceId,
                                                                                      startTimestamp,
                                                                                      endTimestamp,
                                                                                      limit.orElse(DEFAULT_VALUE_LIMIT),
                                                                                      offset.orElse(DEFAULT_VALUE_OFFSET));
        return response;
    }

    //*****************************************************************************************************************
    //********************************************** AUXILIARY FUNCTIONS **********************************************
    //*****************************************************************************************************************

    private DeviceResponseDataDTO getDeviceTypeData(DeviceResponseDataDTO deviceResponseDataDTO) {
        ModelMapper modelMapper = new ModelMapper();
        DeviceResponseDataDTO newDeviceResponseDataDTO = deviceResponseDataDTO;
        switch(deviceResponseDataDTO.getDeviceType()) {
            case COW:
                Optional<CowData> cowData = cowRepository.findByDeviceId(deviceResponseDataDTO.getDeviceId());
                if (cowData.isPresent())
                    newDeviceResponseDataDTO.setCowData(modelMapper.map(cowData.get(), CowResponseDataDTO.class));
                break;
            default:
                break;
        }
        return newDeviceResponseDataDTO;
    }

    private void setDeviceTypeData(DeviceRequestDataDTO deviceRequestDataDTO) {
        switch(deviceRequestDataDTO.getDeviceType()) {
            case COW:
                if (null !=  deviceRequestDataDTO.getCowData()) {
                    Optional<CowData> cowData = cowRepository.findByDeviceId(deviceRequestDataDTO.getDeviceId());
                    if (cowData.isPresent()) {
                        CowData cowDataObject = cowData.get();
                        if (null != deviceRequestDataDTO.getCowData().getBirthDate() && !deviceRequestDataDTO.getCowData().getBirthDate().toString().isEmpty())
                            cowDataObject.setBirthDate(deviceRequestDataDTO.getCowData().getBirthDate());
                        if (null != deviceRequestDataDTO.getCowData().getCowDescription() && !deviceRequestDataDTO.getCowData().getCowDescription().isEmpty())
                            cowDataObject.setCowDescription(deviceRequestDataDTO.getCowData().getCowDescription());
                        if (null != deviceRequestDataDTO.getCowData().getCowName() && !deviceRequestDataDTO.getCowData().getCowName().isEmpty())
                            cowDataObject.setCowName(deviceRequestDataDTO.getCowData().getCowName());
                        if (null != deviceRequestDataDTO.getCowData().getWeight() && !deviceRequestDataDTO.getCowData().getWeight().isNaN())
                            cowDataObject.setWeight(deviceRequestDataDTO.getCowData().getWeight());
                        cowRepository.save(cowDataObject);
                    } else {
                        cowRepository.save(new CowData(deviceRequestDataDTO));
                    }
                }
                break;
            default:
                break;

        }
    }

    private DeviceResponseDataDTO getCapabilities(DeviceResponseDataDTO deviceResponseDataDTO) {
        ModelMapper modelMapper = new ModelMapper();
        DeviceResponseDataDTO newDeviceResponseDataDTO = deviceResponseDataDTO;
        Optional<DeviceCapabilities> deviceCapabilities = capabilitiesRepository.findByDeviceId(deviceResponseDataDTO.getDeviceId());
        if (deviceCapabilities.isPresent()) {
            newDeviceResponseDataDTO.setCapabilities(modelMapper.map(deviceCapabilities.get(), CapabilitiesResponseDataDTO.class));
        }
        return newDeviceResponseDataDTO;
    }

    private Boolean checkIfDeviceIdExists(String deviceId) {
        if (deviceRepository.findByDeviceId(deviceId).isPresent()) return true;
        return false;
    }

}