package com.cde.cowdataconsumer.repository.gyroscope;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.cde.cowdataconsumer.model.gyroscope.GyroscopeData;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GyroscopeRepository extends JpaRepository<GyroscopeData, String> {

	public List<GyroscopeData> findByDeviceId(String deviceId); 

	public List<GyroscopeData> findByDeviceIdAndDay(String deviceId, LocalDate day);  

	@Query(
		value = "" +
			"SELECT * " +
			"FROM gyroscope_data " +
			"WHERE LOWER(device_id) LIKE CONCAT('%', LOWER(:device_id), '%') " +
			"AND timestamp > :start_hour " +
			"AND timestamp < :end_hour " +
			"ORDER BY timestamp DESC ",
		nativeQuery = true
		)
	public Page<GyroscopeData> findByDeviceIdAndTimeInterval(@Param("device_id") String deviceId, 
													   		 @Param("start_hour") LocalDateTime startHour,
													   		 @Param("end_hour") LocalDateTime endHour,
															 Pageable pageable);  

}