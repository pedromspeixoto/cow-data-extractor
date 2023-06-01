package com.cde.cowdataconsumer.repository.activity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.cde.cowdataconsumer.model.activity.ActivityData;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityData, String> {

	public List<ActivityData> findByDeviceId(String deviceId);

	@Query(
		value = "" +
			"SELECT * " +
			"FROM activity_data " + 
			"WHERE LOWER(device_id) LIKE CONCAT('%', LOWER(:device_id), '%') " +
			"ORDER BY timestamp DESC LIMIT 1",
		nativeQuery = true
		)
	public Optional<ActivityData> findLatestByDeviceId(@Param("device_id")  String deviceId); 

	public List<ActivityData> findByDeviceIdAndDay(String deviceId, LocalDate day);

	@Query(
		value = "" +
			"SELECT * " +
			"FROM activity_data " +
			"WHERE LOWER(device_id) LIKE CONCAT('%', LOWER(:device_id), '%') " +
			"AND timestamp > :start_hour " +
			"AND timestamp < :end_hour " +
			"ORDER BY timestamp DESC ",
		nativeQuery = true
		)
	public Page<ActivityData> findByDeviceIdAndTimeInterval(@Param("device_id") String deviceId, 
													   		@Param("start_hour") LocalDateTime startHour,
													   		@Param("end_hour") LocalDateTime endHour,
															Pageable pageable); 

}