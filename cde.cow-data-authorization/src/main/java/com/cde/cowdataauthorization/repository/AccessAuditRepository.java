package com.cde.cowdataauthorization.repository;

import com.cde.cowdataauthorization.model.AccessAudit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessAuditRepository extends JpaRepository<AccessAudit, String> {

}