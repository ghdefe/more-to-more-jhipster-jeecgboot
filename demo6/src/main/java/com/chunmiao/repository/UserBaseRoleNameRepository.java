package com.chunmiao.repository;

import com.chunmiao.domain.UserBaseRoleName;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the UserBaseRoleName entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserBaseRoleNameRepository extends JpaRepository<UserBaseRoleName, Long> {
    void deleteAllByUserBaseIdAndRoleNameId(Long userBaseId,Long roleNameId);

    List<UserBaseRoleName> findAllByUserBaseId(Long id);

    List<UserBaseRoleName> findAllByRoleNameId(Long id);

    void deleteAllByUserBaseId(Long id);

    void deleteAllByRoleNameId(Long id);
}
