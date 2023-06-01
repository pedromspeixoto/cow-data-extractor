package com.cde.cowdatavisualization.repository.device.cow;

import java.util.Optional;

import com.cde.cowdatavisualization.model.cow.CowData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CowRepository extends JpaRepository<CowData, String> {

	public Optional<CowData> findByDeviceId(String deviceId); 

	public CowData findByCowId(String cowId);  

}