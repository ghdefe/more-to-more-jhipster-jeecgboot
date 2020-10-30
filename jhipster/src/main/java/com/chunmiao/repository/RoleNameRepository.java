package com.chunmiao.repository;

import com.chunmiao.domain.RoleName;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RoleName entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleNameRepository extends JpaRepository<RoleName, Long> {
}
