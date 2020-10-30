package com.chunmiao.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.chunmiao.web.rest.TestUtil;

public class RoleNameTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleName.class);
        RoleName roleName1 = new RoleName();
        roleName1.setId(1L);
        RoleName roleName2 = new RoleName();
        roleName2.setId(roleName1.getId());
        assertThat(roleName1).isEqualTo(roleName2);
        roleName2.setId(2L);
        assertThat(roleName1).isNotEqualTo(roleName2);
        roleName1.setId(null);
        assertThat(roleName1).isNotEqualTo(roleName2);
    }
}
