package com.cde.cowdataauthorization.repository;

import java.util.Optional;

import com.cde.cowdataauthorization.model.RoleAccessConfiguration;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleAccessConfigurationRepository extends JpaRepository<RoleAccessConfiguration, Long> {

    @Query(
		value = "" +
			"SELECT * " +
			"FROM role_access_configuration " +
            "WHERE LOWER(:path) LIKE COALESCE(CONCAT('%',path,'%'), '') " +
            "AND role_id = :role_id " +
            "AND method = :method",
		nativeQuery = true
		)
    @Cacheable("access")
    Optional<RoleAccessConfiguration> findByRoleIdAndMethodAndPath(@Param("role_id") Integer roleId,
                                                                   @Param("method") String method,
                                                                   @Param("path") String path);
}