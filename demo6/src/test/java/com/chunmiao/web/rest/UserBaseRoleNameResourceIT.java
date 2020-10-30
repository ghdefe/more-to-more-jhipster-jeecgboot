package com.chunmiao.web.rest;

import com.chunmiao.Demo6App;
import com.chunmiao.domain.UserBaseRoleName;
import com.chunmiao.repository.UserBaseRoleNameRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserBaseRoleNameResource} REST controller.
 */
@SpringBootTest(classes = Demo6App.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserBaseRoleNameResourceIT {

    private static final Long DEFAULT_USER_BASE_ID = 1L;
    private static final Long UPDATED_USER_BASE_ID = 2L;

    private static final Long DEFAULT_ROLE_NAME_ID = 1L;
    private static final Long UPDATED_ROLE_NAME_ID = 2L;

    @Autowired
    private UserBaseRoleNameRepository userBaseRoleNameRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserBaseRoleNameMockMvc;

    private UserBaseRoleName userBaseRoleName;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserBaseRoleName createEntity(EntityManager em) {
        UserBaseRoleName userBaseRoleName = new UserBaseRoleName()
            .userBaseId(DEFAULT_USER_BASE_ID)
            .roleNameId(DEFAULT_ROLE_NAME_ID);
        return userBaseRoleName;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserBaseRoleName createUpdatedEntity(EntityManager em) {
        UserBaseRoleName userBaseRoleName = new UserBaseRoleName()
            .userBaseId(UPDATED_USER_BASE_ID)
            .roleNameId(UPDATED_ROLE_NAME_ID);
        return userBaseRoleName;
    }

    @BeforeEach
    public void initTest() {
        userBaseRoleName = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserBaseRoleName() throws Exception {
        int databaseSizeBeforeCreate = userBaseRoleNameRepository.findAll().size();
        // Create the UserBaseRoleName
        restUserBaseRoleNameMockMvc.perform(post("/api/user-base-role-names")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userBaseRoleName)))
            .andExpect(status().isCreated());

        // Validate the UserBaseRoleName in the database
        List<UserBaseRoleName> userBaseRoleNameList = userBaseRoleNameRepository.findAll();
        assertThat(userBaseRoleNameList).hasSize(databaseSizeBeforeCreate + 1);
        UserBaseRoleName testUserBaseRoleName = userBaseRoleNameList.get(userBaseRoleNameList.size() - 1);
        assertThat(testUserBaseRoleName.getUserBaseId()).isEqualTo(DEFAULT_USER_BASE_ID);
        assertThat(testUserBaseRoleName.getRoleNameId()).isEqualTo(DEFAULT_ROLE_NAME_ID);
    }

    @Test
    @Transactional
    public void createUserBaseRoleNameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userBaseRoleNameRepository.findAll().size();

        // Create the UserBaseRoleName with an existing ID
        userBaseRoleName.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserBaseRoleNameMockMvc.perform(post("/api/user-base-role-names")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userBaseRoleName)))
            .andExpect(status().isBadRequest());

        // Validate the UserBaseRoleName in the database
        List<UserBaseRoleName> userBaseRoleNameList = userBaseRoleNameRepository.findAll();
        assertThat(userBaseRoleNameList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserBaseRoleNames() throws Exception {
        // Initialize the database
        userBaseRoleNameRepository.saveAndFlush(userBaseRoleName);

        // Get all the userBaseRoleNameList
        restUserBaseRoleNameMockMvc.perform(get("/api/user-base-role-names?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userBaseRoleName.getId().intValue())))
            .andExpect(jsonPath("$.[*].userBaseId").value(hasItem(DEFAULT_USER_BASE_ID.intValue())))
            .andExpect(jsonPath("$.[*].roleNameId").value(hasItem(DEFAULT_ROLE_NAME_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getUserBaseRoleName() throws Exception {
        // Initialize the database
        userBaseRoleNameRepository.saveAndFlush(userBaseRoleName);

        // Get the userBaseRoleName
        restUserBaseRoleNameMockMvc.perform(get("/api/user-base-role-names/{id}", userBaseRoleName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userBaseRoleName.getId().intValue()))
            .andExpect(jsonPath("$.userBaseId").value(DEFAULT_USER_BASE_ID.intValue()))
            .andExpect(jsonPath("$.roleNameId").value(DEFAULT_ROLE_NAME_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserBaseRoleName() throws Exception {
        // Get the userBaseRoleName
        restUserBaseRoleNameMockMvc.perform(get("/api/user-base-role-names/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserBaseRoleName() throws Exception {
        // Initialize the database
        userBaseRoleNameRepository.saveAndFlush(userBaseRoleName);

        int databaseSizeBeforeUpdate = userBaseRoleNameRepository.findAll().size();

        // Update the userBaseRoleName
        UserBaseRoleName updatedUserBaseRoleName = userBaseRoleNameRepository.findById(userBaseRoleName.getId()).get();
        // Disconnect from session so that the updates on updatedUserBaseRoleName are not directly saved in db
        em.detach(updatedUserBaseRoleName);
        updatedUserBaseRoleName
            .userBaseId(UPDATED_USER_BASE_ID)
            .roleNameId(UPDATED_ROLE_NAME_ID);

        restUserBaseRoleNameMockMvc.perform(put("/api/user-base-role-names")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserBaseRoleName)))
            .andExpect(status().isOk());

        // Validate the UserBaseRoleName in the database
        List<UserBaseRoleName> userBaseRoleNameList = userBaseRoleNameRepository.findAll();
        assertThat(userBaseRoleNameList).hasSize(databaseSizeBeforeUpdate);
        UserBaseRoleName testUserBaseRoleName = userBaseRoleNameList.get(userBaseRoleNameList.size() - 1);
        assertThat(testUserBaseRoleName.getUserBaseId()).isEqualTo(UPDATED_USER_BASE_ID);
        assertThat(testUserBaseRoleName.getRoleNameId()).isEqualTo(UPDATED_ROLE_NAME_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUserBaseRoleName() throws Exception {
        int databaseSizeBeforeUpdate = userBaseRoleNameRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserBaseRoleNameMockMvc.perform(put("/api/user-base-role-names")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userBaseRoleName)))
            .andExpect(status().isBadRequest());

        // Validate the UserBaseRoleName in the database
        List<UserBaseRoleName> userBaseRoleNameList = userBaseRoleNameRepository.findAll();
        assertThat(userBaseRoleNameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserBaseRoleName() throws Exception {
        // Initialize the database
        userBaseRoleNameRepository.saveAndFlush(userBaseRoleName);

        int databaseSizeBeforeDelete = userBaseRoleNameRepository.findAll().size();

        // Delete the userBaseRoleName
        restUserBaseRoleNameMockMvc.perform(delete("/api/user-base-role-names/{id}", userBaseRoleName.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserBaseRoleName> userBaseRoleNameList = userBaseRoleNameRepository.findAll();
        assertThat(userBaseRoleNameList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
