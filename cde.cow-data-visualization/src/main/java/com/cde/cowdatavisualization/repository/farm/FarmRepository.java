package com.cde.cowdatavisualization.repository.farm;

import java.util.Optional;

import com.cde.cowdatavisualization.model.farm.FarmData;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmRepository extends JpaRepository<FarmData, String> {

	public Optional<FarmData> findByFarmId(String farmId); 

	@Query(
		value = "" +
			"SELECT * " +
			"FROM farm_data " +
			"WHERE COALESCE(farm_id, '') LIKE CONCAT('%', LOWER(:farm_id), '%')  ",
		nativeQuery = true
		)
	public Page<FarmData> findAllFarms(@Param("farm_id") String farmId,
									   Pageable pageable);

}