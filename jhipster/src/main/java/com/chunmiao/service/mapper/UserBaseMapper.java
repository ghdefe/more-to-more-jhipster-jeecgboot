package com.chunmiao.service.mapper;


import com.chunmiao.domain.*;
import com.chunmiao.service.dto.RoleNameDTO;
import com.chunmiao.service.dto.UserBaseDTO;

import org.mapstruct.*;

import java.util.List;

/**
 * Mapper for the entity {@link UserBase} and its DTO {@link UserBaseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserBaseMapper {

    @Mappings({
        @Mapping(source = "userBase.id", target = "id"),
        @Mapping(source = "userBase.username", target = "username"),
        @Mapping(source = "userBase.phone", target = "phone"),
        @Mapping(source = "roleNameList", target = "roleNameDTOList")
    })
    UserBaseDTO toDto(UserBase userBase, List<RoleNameDTO> roleNameList);

    @Mappings({
        @Mapping(source = "userBaseDTO.id", target = "id"),
        @Mapping(source = "userBaseDTO.username", target = "username"),
        @Mapping(source = "userBaseDTO.phone", target = "phone")
    })
    UserBase toEntity(UserBaseDTO userBaseDTO);

    default UserBase fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserBase userBase = new UserBase();
        userBase.setId(id);
        return userBase;
    }
}
