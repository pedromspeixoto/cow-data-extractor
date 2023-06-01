package com.cde.cowdataauthorization.repository;

import java.util.Optional;

import com.cde.cowdataauthorization.enums.Roles;
import com.cde.cowdataauthorization.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Roles name);
}