package com.chunmiao.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A UserBaseRoleName.
 */
@Entity
@Table(name = "user_base_role_name")
public class UserBaseRoleName implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_base_id")
    private Long userBaseId;

    @Column(name = "role_name_id")
    private Long roleNameId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserBaseId() {
        return userBaseId;
    }

    public UserBaseRoleName userBaseId(Long userBaseId) {
        this.userBaseId = userBaseId;
        return this;
    }

    public void setUserBaseId(Long userBaseId) {
        this.userBaseId = userBaseId;
    }

    public Long getRoleNameId() {
        return roleNameId;
    }

    public UserBaseRoleName roleNameId(Long roleNameId) {
        this.roleNameId = roleNameId;
        return this;
    }

    public void setRoleNameId(Long roleNameId) {
        this.roleNameId = roleNameId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserBaseRoleName)) {
            return false;
        }
        return id != null && id.equals(((UserBaseRoleName) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserBaseRoleName{" +
            "id=" + getId() +
            ", userBaseId=" + getUserBaseId() +
            ", roleNameId=" + getRoleNameId() +
            "}";
    }
}
