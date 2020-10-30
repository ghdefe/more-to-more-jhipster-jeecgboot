package com.chunmiao.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link com.chunmiao.domain.RoleName} entity.
 */
public class RoleNameDTO implements Serializable {

    private Long id;

    private String name;

//    @JsonIgnore
    private List<UserBaseDTO> userBaseDTOList;

    public List<UserBaseDTO> getUserBaseDTOList() {
        return userBaseDTOList;
    }

    public void setUserBaseDTOList(List<UserBaseDTO> userBaseDTOList) {
        this.userBaseDTOList = userBaseDTOList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleNameDTO)) {
            return false;
        }

        return id != null && id.equals(((RoleNameDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "RoleNameDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", userBaseDTOList=" + userBaseDTOList +
            '}';
    }
}
