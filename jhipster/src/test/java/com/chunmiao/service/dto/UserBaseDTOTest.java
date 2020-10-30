package com.chunmiao.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.chunmiao.web.rest.TestUtil;

public class UserBaseDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserBaseDTO.class);
        UserBaseDTO userBaseDTO1 = new UserBaseDTO();
        userBaseDTO1.setId(1L);
        UserBaseDTO userBaseDTO2 = new UserBaseDTO();
        assertThat(userBaseDTO1).isNotEqualTo(userBaseDTO2);
        userBaseDTO2.setId(userBaseDTO1.getId());
        assertThat(userBaseDTO1).isEqualTo(userBaseDTO2);
        userBaseDTO2.setId(2L);
        assertThat(userBaseDTO1).isNotEqualTo(userBaseDTO2);
        userBaseDTO1.setId(null);
        assertThat(userBaseDTO1).isNotEqualTo(userBaseDTO2);
    }
}
