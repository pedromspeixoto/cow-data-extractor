package com.cde.cowdatavisualization.repository.device;

import java.util.Optional;

import com.cde.cowdatavisualization.model.device.DeviceData;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceData, String> {

	public Optional<DeviceData> findByDeviceId(String deviceId);  

	@Query(
		value = "" +
			"SELECT * " +
			"FROM device_data " +
			"WHERE COALESCE(farm_id, '') LIKE CONCAT('%', LOWER(:farm_id), '%') " +
			"AND COALESCE(device_id, '') LIKE CONCAT('%', LOWER(:device_id), '%') " +
			"AND COALESCE(device_type, '') LIKE CONCAT('%', UPPER(:device_type), '%') " +
			"AND COALESCE(device_status, '') LIKE CONCAT('%', UPPER(:device_status), '%')",
		nativeQuery = true
		)
	public Page<DeviceData> findAllDevices(@Param("farm_id") String farmId,
										   @Param("device_id") String deviceId,
										   @Param("device_type") String deviceType,
										   @Param("device_status") String deviceStatus,
										   Pageable pageable);

}