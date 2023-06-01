package com.cde.cowdatavisualization.repository.farm;

import java.util.List;
import java.util.Optional;

import com.cde.cowdatavisualization.model.farm.FarmOwners;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmOwnersRepository extends JpaRepository<FarmOwners, String> {

	public Optional<FarmOwners> findByFarmIdAndOwnerId(String farmId, String ownerId);

	public List<FarmOwners> findByFarmId(String farmId);

}