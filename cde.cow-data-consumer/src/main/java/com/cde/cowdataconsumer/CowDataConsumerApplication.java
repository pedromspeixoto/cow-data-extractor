package com.cde.cowdataconsumer;

import com.cde.utils.grpc.GRPCServerServiceGrpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({"com.cde.cowdataconsumer","com.cde.utils.grpc"})
@Import(GRPCServerServiceGrpc.class)
public class CowDataConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CowDataConsumerApplication.class, args);
    }

}