package com.chunmiao.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.chunmiao.web.rest.TestUtil;

public class RoleNameDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleNameDTO.class);
        RoleNameDTO roleNameDTO1 = new RoleNameDTO();
        roleNameDTO1.setId(1L);
        RoleNameDTO roleNameDTO2 = new RoleNameDTO();
        assertThat(roleNameDTO1).isNotEqualTo(roleNameDTO2);
        roleNameDTO2.setId(roleNameDTO1.getId());
        assertThat(roleNameDTO1).isEqualTo(roleNameDTO2);
        roleNameDTO2.setId(2L);
        assertThat(roleNameDTO1).isNotEqualTo(roleNameDTO2);
        roleNameDTO1.setId(null);
        assertThat(roleNameDTO1).isNotEqualTo(roleNameDTO2);
    }
}
