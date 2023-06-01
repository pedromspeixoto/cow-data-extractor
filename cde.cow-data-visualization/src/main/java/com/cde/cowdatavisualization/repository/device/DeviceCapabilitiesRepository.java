package com.cde.cowdatavisualization.repository.device;

import java.util.Optional;

import com.cde.cowdatavisualization.model.device.DeviceCapabilities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceCapabilitiesRepository extends JpaRepository<DeviceCapabilities, String> {

	public Optional<DeviceCapabilities> findByDeviceId(String deviceId);  

}