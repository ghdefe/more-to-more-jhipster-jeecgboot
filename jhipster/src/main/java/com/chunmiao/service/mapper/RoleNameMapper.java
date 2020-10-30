package com.chunmiao.service.mapper;


import com.chunmiao.domain.*;
import com.chunmiao.service.dto.RoleNameDTO;

import com.chunmiao.service.dto.UserBaseDTO;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper for the entity {@link RoleName} and its DTO {@link RoleNameDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RoleNameMapper {

    @Mappings({
        @Mapping(source = "roleName.id", target = "id"),
        @Mapping(source = "roleName.name", target = "name"),
        @Mapping(source = "userBaseDTOList", target = "userBaseDTOList")
    })
    RoleNameDTO toDto(RoleName roleName, List<UserBaseDTO> userBaseDTOList);

    @Mappings({
        @Mapping(source = "roleNameDTO.id", target = "id"),
        @Mapping(source = "roleNameDTO.name", target = "name"),
    })
    RoleName toEntity(RoleNameDTO roleNameDTO);


    default RoleName fromId(Long id) {
        if (id == null) {
            return null;
        }
        RoleName roleName = new RoleName();
        roleName.setId(id);
        return roleName;
    }
}
