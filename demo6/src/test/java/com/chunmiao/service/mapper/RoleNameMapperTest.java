package com.chunmiao.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RoleNameMapperTest {

    private RoleNameMapper roleNameMapper;

    @BeforeEach
    public void setUp() {
        roleNameMapper = new RoleNameMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(roleNameMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(roleNameMapper.fromId(null)).isNull();
    }
}
