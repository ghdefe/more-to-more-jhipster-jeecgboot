package com.chunmiao.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link com.chunmiao.domain.UserBase} entity.
 */
public class UserBaseDTO implements Serializable {

    private Long id;

    private String username;

    private String phone;

//    @JsonIgnore
    private List<RoleNameDTO> roleNameDTOList;

    public List<RoleNameDTO> getRoleNameDTOList() {
        return roleNameDTOList;
    }

    public void setRoleNameDTOList(List<RoleNameDTO> roleNameDTOList) {
        this.roleNameDTOList = roleNameDTOList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserBaseDTO)) {
            return false;
        }

        return id != null && id.equals(((UserBaseDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "UserBaseDTO{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", phone='" + phone + '\'' +
            ", roleNameDTOList=" + roleNameDTOList +
            '}';
    }
}
