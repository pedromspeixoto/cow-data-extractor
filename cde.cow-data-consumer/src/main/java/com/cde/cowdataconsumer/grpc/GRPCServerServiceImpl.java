package com.cde.cowdataconsumer.grpc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import com.cde.cowdataconsumer.service.CowDataConsumerService;
import com.cde.utils.dto.accelerometer.AccelerometerResponseAllDataDTO;
import com.cde.utils.dto.activity.ActivityDataDTO;
import com.cde.utils.dto.activity.ActivityResponseAllDataDTO;
import com.cde.utils.dto.gps.GPSDataDTO;
import com.cde.utils.dto.gps.GPSResponseAllDataDTO;
import com.cde.utils.dto.gyroscope.GyroscopeResponseAllDataDTO;
import com.cde.utils.grpc.GRPCAccelerometerData;
import com.cde.utils.grpc.GRPCAccelerometerDataRequest;
import com.cde.utils.grpc.GRPCAccelerometerDataResponse;
import com.cde.utils.grpc.GRPCActivityData;
import com.cde.utils.grpc.GRPCActivityDataRequest;
import com.cde.utils.grpc.GRPCActivityDataResponse;
import com.cde.utils.grpc.GRPCGPSData;
import com.cde.utils.grpc.GRPCGPSDataRequest;
import com.cde.utils.grpc.GRPCGPSDataResponse;
import com.cde.utils.grpc.GRPCGyroscopeData;
import com.cde.utils.grpc.GRPCGyroscopeDataRequest;
import com.cde.utils.grpc.GRPCGyroscopeDataResponse;
import com.cde.utils.grpc.GRPCLatestDeviceActivityRequest;
import com.cde.utils.grpc.GRPCLatestDeviceLocationRequest;
import com.cde.utils.grpc.GRPCLatestDeviceLocationResponse;
import com.cde.utils.grpc.GRPCServerServiceGrpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@Component
@GrpcService
public class GRPCServerServiceImpl extends GRPCServerServiceGrpc.GRPCServerServiceImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(GRPCServerServiceImpl.class);

    @Autowired
    CowDataConsumerService consumerService;

    @Override
    public void getLatestDeviceActivity(final GRPCLatestDeviceActivityRequest request, final StreamObserver<GRPCActivityData> responseObserver) {
        LOGGER.info("Request received from client:\n" + request);
    
        // get data from service
        ActivityDataDTO activityDataDTO = consumerService.getDeviceLatestActivity(request.getDeviceId());

        // convert to response format
        GRPCActivityData response = GRPCActivityData.newBuilder().build();
        if (null != activityDataDTO) {
            response = GRPCActivityData.newBuilder()
                                       .setTimestamp(activityDataDTO.getTimestamp().toString())
                                       .setActivity(activityDataDTO.getActivity().toString())
                                       .build();
        }
        LOGGER.info(response.toString());

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getLatestDeviceLocation(final GRPCLatestDeviceLocationRequest request, final StreamObserver<GRPCLatestDeviceLocationResponse> responseObserver) {
        LOGGER.info("Request received from client:\n" + request);
    
        // get data from service
        GPSDataDTO gpsDataDTO = consumerService.getDeviceLatestGpsData(request.getDeviceId());

        // convert to response format
        GRPCLatestDeviceLocationResponse response = GRPCLatestDeviceLocationResponse.newBuilder().build();
        if (null != gpsDataDTO) {
            response = GRPCLatestDeviceLocationResponse.newBuilder()
                                                       .setTimestamp(gpsDataDTO.getTimestamp().toString())
                                                       .setLatitude(gpsDataDTO.getGpsCoordinates().getLatitude())
                                                       .setLongitude(gpsDataDTO.getGpsCoordinates().getLongitude())
                                                       .build();
        }

        LOGGER.info(response.toString());

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getDeviceAccelerometerData(final GRPCAccelerometerDataRequest request, final StreamObserver<GRPCAccelerometerDataResponse> responseObserver) {
        LOGGER.info("Request received from client:\n" + request);

        // convert strings to timestamp format
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime startTimestamp = LocalDateTime.parse(request.getStartTimestamp(), formatter);
        LocalDateTime endTimestamp = LocalDateTime.parse(request.getEndTimestamp(), formatter);

        // get data from service
        AccelerometerResponseAllDataDTO accelerometerDataDto = consumerService.getAccelerometerData(request.getDeviceId(),
                                                                                                    startTimestamp,
                                                                                                    endTimestamp,
                                                                                                    request.getLimit(),
                                                                                                    request.getOffset());

        // convert to response format
        GRPCAccelerometerDataResponse response = GRPCAccelerometerDataResponse.newBuilder().build();
        if (null != accelerometerDataDto) {
            response = GRPCAccelerometerDataResponse.newBuilder()
                                                    .addAllSamples(accelerometerDataDto.getAccelerometerData()
                                                                                            .stream()
                                                                                            .map(sample -> {
                                                                                                return GRPCAccelerometerData.newBuilder().setTimestamp(sample.getTimestamp().toString())
                                                                                                                                        .setX(sample.getX())
                                                                                                                                        .setY(sample.getY())
                                                                                                                                        .setZ(sample.getZ())
                                                                                                                                        .build();
                                                                        })
                                                                        .collect(Collectors.toList()))
                                                    .setLimit(accelerometerDataDto.getPageDetails().getLimit())
                                                    .setOffset(accelerometerDataDto.getPageDetails().getOffset())
                                                    .setTotalElements(accelerometerDataDto.getPageDetails().getTotalElements())
                                                    .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getDeviceGyroscopeData(final GRPCGyroscopeDataRequest request, final StreamObserver<GRPCGyroscopeDataResponse> responseObserver) {
        LOGGER.info("Request received from client:\n" + request);

        // convert strings to timestamp format
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime startTimestamp = LocalDateTime.parse(request.getStartTimestamp(), formatter);
        LocalDateTime endTimestamp = LocalDateTime.parse(request.getEndTimestamp(), formatter);

        // get data from service
        GyroscopeResponseAllDataDTO gyroscopeDataDto = consumerService.getGyroscopeData(request.getDeviceId(),
                                                                                        startTimestamp,
                                                                                        endTimestamp,
                                                                                        request.getLimit(),
                                                                                        request.getOffset());

        // convert to response format
        GRPCGyroscopeDataResponse response = GRPCGyroscopeDataResponse.newBuilder().build();
        if (null != gyroscopeDataDto) {
            response = GRPCGyroscopeDataResponse.newBuilder()
                                                .addAllSamples(gyroscopeDataDto.getGyroscopeData()
                                                                               .stream()
                                                                               .map(sample -> {
                                                                                    return GRPCGyroscopeData.newBuilder().setTimestamp(sample.getTimestamp().toString())
                                                                                                                         .setAlpha(sample.getAlpha())
                                                                                                                         .setBeta(sample.getBeta())
                                                                                                                         .setGamma(sample.getGamma())
                                                                                                                         .build();
                                                                                })
                                                                                .collect(Collectors.toList()))
                                                .setLimit(gyroscopeDataDto.getPageDetails().getLimit())
                                                .setOffset(gyroscopeDataDto.getPageDetails().getOffset())
                                                .setTotalElements(gyroscopeDataDto.getPageDetails().getTotalElements())
                                                .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getDeviceGPSData(final GRPCGPSDataRequest request, final StreamObserver<GRPCGPSDataResponse> responseObserver) {
        LOGGER.info("Request received from client:\n" + request);

        // convert strings to timestamp format
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime startTimestamp = LocalDateTime.parse(request.getStartTimestamp(), formatter);
        LocalDateTime endTimestamp = LocalDateTime.parse(request.getEndTimestamp(), formatter);

        // get data from service
        GPSResponseAllDataDTO gpsDataDto = consumerService.getGPSData(request.getDeviceId(),
                                                                      startTimestamp,
                                                                      endTimestamp,
                                                                      request.getLimit(),
                                                                      request.getOffset());

        // convert to response format
        GRPCGPSDataResponse response = GRPCGPSDataResponse.newBuilder().build();
        if (null != gpsDataDto) {
            response = GRPCGPSDataResponse.newBuilder()
                                          .addAllSamples(gpsDataDto.getGpsData()
                                                                   .stream()
                                                                   .map(sample -> {
                                                                        return GRPCGPSData.newBuilder().setTimestamp(sample.getTimestamp().toString())
                                                                                                       .setLatitude(sample.getLatitude())
                                                                                                       .setLongitude(sample.getLongitude())
                                                                                                       .build();
                                                                    })
                                                                    .collect(Collectors.toList()))
                                                .setLimit(gpsDataDto.getPageDetails().getLimit())
                                                .setOffset(gpsDataDto.getPageDetails().getOffset())
                                                .setTotalElements(gpsDataDto.getPageDetails().getTotalElements())
                                                .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getDeviceActivityData(final GRPCActivityDataRequest request, final StreamObserver<GRPCActivityDataResponse> responseObserver) {
        LOGGER.info("Request received from client:\n" + request);

        // convert strings to timestamp format
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime startTimestamp = LocalDateTime.parse(request.getStartTimestamp(), formatter);
        LocalDateTime endTimestamp = LocalDateTime.parse(request.getEndTimestamp(), formatter);

        // get data from service
        ActivityResponseAllDataDTO activityDataDto = consumerService.getActivityData(request.getDeviceId(),
                                                                                     startTimestamp,
                                                                                     endTimestamp,
                                                                                     request.getLimit(),
                                                                                     request.getOffset());

        // convert to response format
        GRPCActivityDataResponse response = GRPCActivityDataResponse.newBuilder().build();
        if (null != activityDataDto) {
            response = GRPCActivityDataResponse.newBuilder()
                                               .addAllSamples(activityDataDto.getActivityData()
                                                                             .stream()
                                                                             .map(sample -> {
                                                                                return GRPCActivityData.newBuilder().setTimestamp(sample.getTimestamp().toString())
                                                                                                                    .setActivity(sample.getActivity())
                                                                                                                    .build();
                                                                    })
                                                                    .collect(Collectors.toList()))
                                                .setLimit(activityDataDto.getPageDetails().getLimit())
                                                .setOffset(activityDataDto.getPageDetails().getOffset())
                                                .setTotalElements(activityDataDto.getPageDetails().getTotalElements())
                                                .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}