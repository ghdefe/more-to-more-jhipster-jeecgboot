package com.chunmiao.service;

import com.chunmiao.domain.RoleName;
import com.chunmiao.domain.User;
import com.chunmiao.domain.UserBase;
import com.chunmiao.domain.UserBaseRoleName;
import com.chunmiao.repository.RoleNameRepository;
import com.chunmiao.repository.UserBaseRepository;
import com.chunmiao.repository.UserBaseRoleNameRepository;
import com.chunmiao.service.dto.RoleNameDTO;
import com.chunmiao.service.dto.UserBaseDTO;
import com.chunmiao.service.mapper.RoleNameMapper;
import com.chunmiao.service.mapper.UserBaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link RoleName}.
 */
@Service
@Transactional
public class RoleNameService {

    private final Logger log = LoggerFactory.getLogger(RoleNameService.class);

    private final UserBaseRepository userBaseRepository;

    private final RoleNameMapper roleNameMapper;

    private final UserBaseMapper userBaseMapper;

    private final RoleNameRepository roleNameRepository;

    private final UserBaseRoleNameRepository userBaseRoleNameRepository;

    public RoleNameService(UserBaseRepository userBaseRepository, RoleNameMapper roleNameMapper, UserBaseMapper userBaseMapper, RoleNameRepository roleNameRepository, UserBaseRoleNameRepository userBaseRoleNameRepository) {
        this.userBaseRepository = userBaseRepository;
        this.roleNameMapper = roleNameMapper;
        this.userBaseMapper = userBaseMapper;
        this.roleNameRepository = roleNameRepository;
        this.userBaseRoleNameRepository = userBaseRoleNameRepository;
    }

    /**
     * get all userBase of roleName
     * 得到所有拥有该角色的用户
     */
    public List<UserBaseDTO> getUserBase(RoleName roleName) {
        // 得到所有该用户拥有的角色id
        List<UserBaseRoleName> allByRoleNameId = userBaseRoleNameRepository.findAllByRoleNameId(roleName.getId());
        LinkedList<UserBaseDTO> userBaseDTOS = new LinkedList<>();
        // 到RoleName表里查找所有符合的角色
        for (UserBaseRoleName userBaseRoleName : allByRoleNameId) {
            UserBase userBase = userBaseRepository.findById(userBaseRoleName.getUserBaseId()).get();
            UserBaseDTO dto = userBaseMapper.toDto(userBase, null);
            userBaseDTOS.add(dto);
        }
        return userBaseDTOS;
    }

    /**
     * Save a roleName.
     *
     * @param roleNameDTO the entity to save.
     * @return the persisted entity.
     */
    public RoleNameDTO save(RoleNameDTO roleNameDTO) {
        log.debug("Request to save RoleName : {}", roleNameDTO);
        RoleName roleName = roleNameRepository.save(roleNameMapper.toEntity(roleNameDTO));

        // 还要保存拥有该角色的用户信息
        List<Long>[] changeId = getChangeId(roleNameDTO);
        List<Long> idsAdd = changeId[0];    // 返回的第一个元素是新增的角色id
        List<Long> idsDelete = changeId[1]; // 第二个元素是删除的角色id
        List<UserBaseRoleName> roleAdd = idsAdd
            .stream()
            .map(aLong -> {
                UserBaseRoleName userBaseRoleName = new UserBaseRoleName();
                userBaseRoleName.setRoleNameId(roleName.getId());
                userBaseRoleName.setUserBaseId(aLong);
                return userBaseRoleName;
            })
            .collect(Collectors.toList());
        userBaseRoleNameRepository.saveAll(roleAdd);

        for (Long id : idsDelete) {
            userBaseRoleNameRepository.deleteAllByUserBaseIdAndRoleNameId(id, roleName.getId());
        }

        return roleNameMapper.toDto(roleName, getUserBase(roleName));
    }

    /**
     * Get all the roleNames.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RoleNameDTO> findAll() {
        log.debug("Request to get all RoleNames");
        List<RoleName> roleNames = roleNameRepository.findAll();
        LinkedList<RoleNameDTO> roleNameDTOS = new LinkedList<>();
        for (RoleName roleName : roleNames) {
            RoleNameDTO dto = findOne(roleName.getId()).get();
            roleNameDTOS.add(dto);
        }
        return roleNameDTOS;
    }


    /**
     * Get one roleName by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RoleNameDTO> findOne(Long id) {
        log.debug("Request to get RoleName : {}", id);
        RoleName roleName = roleNameRepository.findById(id).get();
        return Optional.ofNullable(roleNameMapper.toDto(roleName, getUserBase(roleName)));
    }

    /**
     * Delete the roleName by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RoleName : {}", id);
        // 删除该角色表的用户信息库
        deleteUserBaseRoleName(id);
        roleNameRepository.deleteById(id);

    }

    public void deleteUserBaseRoleName(Long id) {
        userBaseRoleNameRepository.deleteAllByRoleNameId(id);
    }

    List<Long>[] getChangeId(RoleNameDTO roleNameDTO) {
        // 数据库的角色id集合
        Set<Long> idsOnDb = userBaseRoleNameRepository.findAllByRoleNameId(roleNameDTO.getId())
            .stream()
            // 去除重复的
            .distinct()
            // 只要其中用户id
            .map(UserBaseRoleName::getUserBaseId)
            // 放到一个HashSet里
            .collect(Collectors.toSet());

        // 请求的用户id集合
        Set<Long> idsOnHttp = roleNameDTO.getUserBaseDTOList()
            .stream()
            .distinct()
            .map(UserBaseDTO::getId)
            .collect(Collectors.toSet());

        // 新增的用户id集合
        List<Long> idsAdd = idsOnHttp
            .stream()
            // 筛选新增的，在数据库的集合中没有找到的即为新增的
            .filter(idUpdate -> !idsOnDb.contains(idUpdate))
            .collect(Collectors.toList());

        // 删除的角色id集合
        List<Long> idsDeleted = idsOnDb
            .stream()
            .distinct()
            .filter(idOnDb -> !idsOnHttp.contains(idOnDb))
            .collect(Collectors.toList());

        return new List[]{idsAdd, idsDeleted};
    }
}
