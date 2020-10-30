package com.chunmiao.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.chunmiao.web.rest.TestUtil;

public class UserBaseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserBase.class);
        UserBase userBase1 = new UserBase();
        userBase1.setId(1L);
        UserBase userBase2 = new UserBase();
        userBase2.setId(userBase1.getId());
        assertThat(userBase1).isEqualTo(userBase2);
        userBase2.setId(2L);
        assertThat(userBase1).isNotEqualTo(userBase2);
        userBase1.setId(null);
        assertThat(userBase1).isNotEqualTo(userBase2);
    }
}
