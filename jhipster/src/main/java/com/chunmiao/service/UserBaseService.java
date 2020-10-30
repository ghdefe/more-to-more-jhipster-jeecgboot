package com.chunmiao.service;

import com.chunmiao.domain.RoleName;
import com.chunmiao.domain.UserBase;
import com.chunmiao.domain.UserBaseRoleName;
import com.chunmiao.repository.RoleNameRepository;
import com.chunmiao.repository.UserBaseRepository;
import com.chunmiao.repository.UserBaseRoleNameRepository;
import com.chunmiao.service.dto.RoleNameDTO;
import com.chunmiao.service.dto.UserBaseDTO;
import com.chunmiao.service.dto.UserDTO;
import com.chunmiao.service.mapper.RoleNameMapper;
import com.chunmiao.service.mapper.UserBaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service Implementation for managing {@link UserBase}.
 */
@Service
@Transactional
public class UserBaseService {

    private final Logger log = LoggerFactory.getLogger(UserBaseService.class);

    private final UserBaseRepository userBaseRepository;

    private final UserBaseMapper userBaseMapper;

    private final RoleNameMapper roleNameMapper;


    private final RoleNameRepository roleNameRepository;

    private final UserBaseRoleNameRepository userBaseRoleNameRepository;

    public UserBaseService(UserBaseRepository userBaseRepository, UserBaseMapper userBaseMapper, RoleNameMapper roleNameMapper, RoleNameRepository roleNameRepository, UserBaseRoleNameRepository userBaseRoleNameRepository) {
        this.userBaseRepository = userBaseRepository;
        this.userBaseMapper = userBaseMapper;
        this.roleNameMapper = roleNameMapper;
        this.roleNameRepository = roleNameRepository;
        this.userBaseRoleNameRepository = userBaseRoleNameRepository;
    }

    /**
     * get the all RoleName of userBase
     * 得到该用户的角色
     */
    public List<RoleNameDTO> getRoleName(UserBase userBase) {
        // 得到所有该用户拥有的角色id
        List<UserBaseRoleName> allByUserBaseId = userBaseRoleNameRepository.findAllByUserBaseId(userBase.getId());
        LinkedList<RoleNameDTO> roleNames = new LinkedList<>();
        // 到RoleName表里查找所有符合的角色
        for (UserBaseRoleName userBaseRoleName : allByUserBaseId) {
            RoleName roleName = roleNameRepository.getOne(userBaseRoleName.getRoleNameId());
            RoleNameDTO dto = roleNameMapper.toDto(roleName, null);
            roleNames.add(dto);
        }
        return roleNames;
    }



    /**
     * Save a userBase.
     *
     * @param userBaseDTO the entity to save.
     * @return the persisted entity.
     */
    public UserBaseDTO save(UserBaseDTO userBaseDTO) {
        log.debug("Request to save UserBase : {}", userBaseDTO);
        // 保存用户信息,数据库生成id后会返回给userBase
        UserBase userBase = userBaseRepository.save(userBaseMapper.toEntity(userBaseDTO));

        // 更新角色时还需要对比数据库跟实体类中的角色信息
        List<Long>[] changeId = getChangeId(userBaseDTO);
        List<Long> idsAdd = changeId[0];    // 返回的第一个元素是新增的角色id
        List<Long> idsDelete = changeId[1]; // 第二个元素是删除的角色id
        List<UserBaseRoleName> roleAdd = idsAdd
            .stream()
            .map(aLong -> {
                UserBaseRoleName userBaseRoleName = new UserBaseRoleName();
                userBaseRoleName.setRoleNameId(aLong);
                userBaseRoleName.setUserBaseId(userBase.getId());
                return userBaseRoleName;
            })
            .collect(Collectors.toList());
        userBaseRoleNameRepository.saveAll(roleAdd);

        for (Long id : idsDelete) {
            userBaseRoleNameRepository.deleteAllByUserBaseIdAndRoleNameId(userBaseDTO.getId(),id);
        }
        return userBaseMapper.toDto(userBase,getRoleName(userBase));
    }

    /**
     * Get all the userBases.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserBaseDTO> findAll() {
        log.debug("Request to get all UserBases");
        List<UserBase> userBases = userBaseRepository.findAll();
        LinkedList<UserBaseDTO> userBaseDTOS = new LinkedList<>();
        for (UserBase userBase : userBases) {
            Optional<UserBaseDTO> one = findOne(userBase.getId());
            one.map(userBaseDTO -> userBaseDTOS.add(userBaseDTO));
        }
        return userBaseDTOS;
    }


    /**
     * Get one userBase by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserBaseDTO> findOne(Long id) {
        log.debug("Request to get UserBase : {}", id);
        UserBase userBase = userBaseRepository.getOne(id);
        return Optional.ofNullable(userBaseMapper.toDto(userBase, getRoleName(userBase)));
    }

    /**
     * Delete the userBase by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserBase : {}", id);
        // 删除该用户对应的UserBaseRoleName信息
        deleteUserBaseRoleName(id);
        // 删除用户
        userBaseRepository.deleteById(id);
    }

    public void deleteUserBaseRoleName(Long id){
        userBaseRoleNameRepository.deleteAllByUserBaseId(id);
    }

    List<Long>[] getChangeId(UserBaseDTO userBaseDTO){
        // 数据库的角色id集合
        Set<Long> idsOnDb = userBaseRoleNameRepository.findAllByUserBaseId(userBaseDTO.getId())
            .stream()
            // 去除重复的
            .distinct()
            // 只要其中角色id
            .map(UserBaseRoleName::getRoleNameId)
            // 放到一个HashSet里
            .collect(Collectors.toSet());

        // 请求的角色id集合
        Set<Long> idsOnHttp = userBaseDTO.getRoleNameDTOList()
            .stream()
            .distinct()
            .map(RoleNameDTO::getId)
            .collect(Collectors.toSet());

        // 新增的角色id集合
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

        return new List[]{idsAdd,idsDeleted};
    }
}
