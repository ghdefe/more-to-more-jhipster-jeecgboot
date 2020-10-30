package com.chunmiao.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserBaseMapperTest {

    private UserBaseMapper userBaseMapper;

    @BeforeEach
    public void setUp() {
        userBaseMapper = new UserBaseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userBaseMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userBaseMapper.fromId(null)).isNull();
    }
}
