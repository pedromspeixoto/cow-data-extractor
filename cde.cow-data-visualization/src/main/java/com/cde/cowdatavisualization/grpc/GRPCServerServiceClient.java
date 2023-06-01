package com.cde.cowdatavisualization.grpc;

import com.cde.utils.grpc.GRPCServerServiceGrpc;
import com.cde.utils.models.PageDetails;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import com.cde.utils.dto.accelerometer.AccelerometerDataResponseDTO;
import com.cde.utils.dto.accelerometer.AccelerometerResponseAllDataDTO;
import com.cde.utils.dto.activity.ActivityDataDTO;
import com.cde.utils.dto.activity.ActivityDataResponseDTO;
import com.cde.utils.dto.activity.ActivityResponseAllDataDTO;
import com.cde.utils.dto.gps.GPSDataDTO;
import com.cde.utils.dto.gps.GPSDataResponseDTO;
import com.cde.utils.dto.gps.GPSResponseAllDataDTO;
import com.cde.utils.dto.gyroscope.GyroscopeDataResponseDTO;
import com.cde.utils.dto.gyroscope.GyroscopeResponseAllDataDTO;
import com.cde.utils.grpc.GRPCAccelerometerDataRequest;
import com.cde.utils.grpc.GRPCAccelerometerDataResponse;
import com.cde.utils.grpc.GRPCLatestDeviceActivityRequest;
import com.cde.utils.grpc.GRPCActivityData;
import com.cde.utils.grpc.GRPCActivityDataRequest;
import com.cde.utils.grpc.GRPCActivityDataResponse;
import com.cde.utils.grpc.GRPCGPSDataRequest;
import com.cde.utils.grpc.GRPCGPSDataResponse;
import com.cde.utils.grpc.GRPCGyroscopeDataRequest;
import com.cde.utils.grpc.GRPCGyroscopeDataResponse;
import com.cde.utils.grpc.GRPCLatestDeviceLocationRequest;
import com.cde.utils.grpc.GRPCLatestDeviceLocationResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
@Configuration
public class GRPCServerServiceClient {

    Logger LOGGER = LoggerFactory.getLogger(GRPCServerServiceClient.class);

    @Value(value = "${grpc.server-config.host}")
    private String grpcServerHost;

    @Value(value = "${grpc.server-config.port}")
    private int grpcServerPort;

    public ActivityDataDTO getLatestActivityData(String deviceId) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcServerHost, grpcServerPort)
                                                      .usePlaintext()
                                                      .build();

        GRPCServerServiceGrpc.GRPCServerServiceBlockingStub stub = GRPCServerServiceGrpc.newBlockingStub(channel);

        GRPCActivityData grpcLatestActivityResponse = stub.getLatestDeviceActivity(GRPCLatestDeviceActivityRequest.newBuilder()
                                                                                                                  .setDeviceId(deviceId)
                                                                                                                  .build());
        
        channel.shutdown();

        ActivityDataDTO activityDataDto = null;

        if (null != grpcLatestActivityResponse.getTimestamp() && !grpcLatestActivityResponse.getTimestamp().isEmpty()) {
            activityDataDto = new ActivityDataDTO(grpcLatestActivityResponse.getTimestamp(),
                                                  grpcLatestActivityResponse.getActivity());
        }

        return activityDataDto;

    }

    public GPSDataDTO getLatestDeviceLocation(String deviceId) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcServerHost, grpcServerPort)
                                                      .usePlaintext()
                                                      .build();

        GRPCServerServiceGrpc.GRPCServerServiceBlockingStub stub = GRPCServerServiceGrpc.newBlockingStub(channel);

        GRPCLatestDeviceLocationResponse grpcLatestDeviceLocationResponse = stub.getLatestDeviceLocation(GRPCLatestDeviceLocationRequest.newBuilder()
                                                                                                                                        .setDeviceId(deviceId)
                                                                                                                                        .build());
        
        channel.shutdown();

        GPSDataDTO gpsCoordinates = null;

        if (null != grpcLatestDeviceLocationResponse.getTimestamp() && !grpcLatestDeviceLocationResponse.getTimestamp().isEmpty()){
            gpsCoordinates = new GPSDataDTO(grpcLatestDeviceLocationResponse.getTimestamp(),
                                            grpcLatestDeviceLocationResponse.getLatitude(),
                                            grpcLatestDeviceLocationResponse.getLongitude());
        }

        return gpsCoordinates;
    }

    public AccelerometerResponseAllDataDTO getAccelerometerData(String deviceId, LocalDateTime startTimestamp, LocalDateTime endTimestamp, Integer limit, Integer offset) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcServerHost, grpcServerPort)
                                                      .usePlaintext()
                                                      .build();

        GRPCServerServiceGrpc.GRPCServerServiceBlockingStub stub = GRPCServerServiceGrpc.newBlockingStub(channel);

        GRPCAccelerometerDataResponse grpcAccelerometerDataResponse = stub.getDeviceAccelerometerData(GRPCAccelerometerDataRequest.newBuilder()
                                                                                                                                  .setDeviceId(deviceId)
                                                                                                                                  .setStartTimestamp(startTimestamp.toString())
                                                                                                                                  .setEndTimestamp(endTimestamp.toString())
                                                                                                                                  .setLimit(limit)
                                                                                                                                  .setOffset(offset)
                                                                                                                                  .build());
        
        channel.shutdown();

        AccelerometerResponseAllDataDTO accelerometerDataDto = new AccelerometerResponseAllDataDTO(grpcAccelerometerDataResponse.getSamplesList().stream()
                                                                                                             .map(sample -> {
                                                                                                                    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                                                                                                                    return new AccelerometerDataResponseDTO(LocalDateTime.parse(sample.getTimestamp(), formatter),
                                                                                                                                                            sample.getX(),
                                                                                                                                                            sample.getY(),
                                                                                                                                                            sample.getZ());
                                                                                                                })
                                                                                                             .collect(Collectors.toList()),
                                                                                                   new PageDetails(grpcAccelerometerDataResponse.getLimit(),
                                                                                                                   grpcAccelerometerDataResponse.getOffset(),
                                                                                                                   grpcAccelerometerDataResponse.getTotalElements()));

        return accelerometerDataDto;
    }

    public GyroscopeResponseAllDataDTO getGyroscopeData(String deviceId, LocalDateTime startTimestamp, LocalDateTime endTimestamp, Integer limit, Integer offset) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcServerHost, grpcServerPort)
                                                      .usePlaintext()
                                                      .build();

        GRPCServerServiceGrpc.GRPCServerServiceBlockingStub stub = GRPCServerServiceGrpc.newBlockingStub(channel);

        GRPCGyroscopeDataResponse grpcGyroscopeDataResponse = stub.getDeviceGyroscopeData(GRPCGyroscopeDataRequest.newBuilder()
                                                                                                                  .setDeviceId(deviceId)
                                                                                                                  .setStartTimestamp(startTimestamp.toString())
                                                                                                                  .setEndTimestamp(endTimestamp.toString())
                                                                                                                  .setLimit(limit)
                                                                                                                  .setOffset(offset)
                                                                                                                  .build());
        
        channel.shutdown();

        GyroscopeResponseAllDataDTO gyroscopeDataDto = new GyroscopeResponseAllDataDTO(grpcGyroscopeDataResponse.getSamplesList().stream()
                                                                                                .map(sample -> {
                                                                                                    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                                                                                                    return new GyroscopeDataResponseDTO(LocalDateTime.parse(sample.getTimestamp(), formatter),
                                                                                                                                                            sample.getAlpha(),
                                                                                                                                                            sample.getBeta(),
                                                                                                                                                            sample.getGamma());
                                                                                                 })
                                                                                                 .collect(Collectors.toList()),
                                                                                       new PageDetails(grpcGyroscopeDataResponse.getLimit(),
                                                                                                       grpcGyroscopeDataResponse.getOffset(),
                                                                                                       grpcGyroscopeDataResponse.getTotalElements()));

        return gyroscopeDataDto;
    }

    public GPSResponseAllDataDTO getGPSData(String deviceId, LocalDateTime startTimestamp, LocalDateTime endTimestamp, Integer limit, Integer offset) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcServerHost, grpcServerPort)
                                                      .usePlaintext()
                                                      .build();

        GRPCServerServiceGrpc.GRPCServerServiceBlockingStub stub = GRPCServerServiceGrpc.newBlockingStub(channel);

        GRPCGPSDataResponse grpcGPSDataResponse = stub.getDeviceGPSData(GRPCGPSDataRequest.newBuilder()
                                                                                          .setDeviceId(deviceId)
                                                                                          .setStartTimestamp(startTimestamp.toString())
                                                                                          .setEndTimestamp(endTimestamp.toString())
                                                                                          .setLimit(limit)
                                                                                          .setOffset(offset)
                                                                                          .build());
        
        channel.shutdown();

        GPSResponseAllDataDTO gpsDataDto = new GPSResponseAllDataDTO(grpcGPSDataResponse.getSamplesList().stream()
                                                                                        .map(sample -> {
                                                                                            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                                                                                            return new GPSDataResponseDTO(LocalDateTime.parse(sample.getTimestamp(), formatter),
                                                                                                                                              sample.getLatitude(),
                                                                                                                                              sample.getLongitude());
                                                                                                 })
                                                                                                 .collect(Collectors.toList()),
                                                                     new PageDetails(grpcGPSDataResponse.getLimit(),
                                                                                     grpcGPSDataResponse.getOffset(),
                                                                                     grpcGPSDataResponse.getTotalElements()));

        return gpsDataDto;
    }

    public ActivityResponseAllDataDTO getActivityData(String deviceId, LocalDateTime startTimestamp, LocalDateTime endTimestamp, Integer limit, Integer offset) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcServerHost, grpcServerPort)
                                                      .usePlaintext()
                                                      .build();
    
        GRPCServerServiceGrpc.GRPCServerServiceBlockingStub stub = GRPCServerServiceGrpc.newBlockingStub(channel);
    
        GRPCActivityDataResponse grpcActivityDataResponse = stub.getDeviceActivityData(GRPCActivityDataRequest.newBuilder()
                                                                                                              .setDeviceId(deviceId)
                                                                                                              .setStartTimestamp(startTimestamp.toString())
                                                                                                              .setEndTimestamp(endTimestamp.toString())
                                                                                                              .setLimit(limit)
                                                                                                              .setOffset(offset)
                                                                                                              .build());
        
        channel.shutdown();
    
        ActivityResponseAllDataDTO activityDataDto = new ActivityResponseAllDataDTO(grpcActivityDataResponse.getSamplesList().stream()
                                                                                        .map(sample -> {
                                                                                            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                                                                                            return new ActivityDataResponseDTO(LocalDateTime.parse(sample.getTimestamp(), formatter),
                                                                                                                                                   sample.getActivity());
                                                                                                 })
                                                                                                 .collect(Collectors.toList()),
                                                                     new PageDetails(grpcActivityDataResponse.getLimit(),
                                                                                     grpcActivityDataResponse.getOffset(),
                                                                                     grpcActivityDataResponse.getTotalElements()));
    
        return activityDataDto;
    }

}