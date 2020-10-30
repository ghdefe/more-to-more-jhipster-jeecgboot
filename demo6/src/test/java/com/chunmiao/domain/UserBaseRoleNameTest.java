package com.chunmiao.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.chunmiao.web.rest.TestUtil;

public class UserBaseRoleNameTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserBaseRoleName.class);
        UserBaseRoleName userBaseRoleName1 = new UserBaseRoleName();
        userBaseRoleName1.setId(1L);
        UserBaseRoleName userBaseRoleName2 = new UserBaseRoleName();
        userBaseRoleName2.setId(userBaseRoleName1.getId());
        assertThat(userBaseRoleName1).isEqualTo(userBaseRoleName2);
        userBaseRoleName2.setId(2L);
        assertThat(userBaseRoleName1).isNotEqualTo(userBaseRoleName2);
        userBaseRoleName1.setId(null);
        assertThat(userBaseRoleName1).isNotEqualTo(userBaseRoleName2);
    }
}
