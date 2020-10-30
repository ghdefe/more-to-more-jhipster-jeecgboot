//package com.chunmiao.web.rest;
//
//import com.chunmiao.Demo6App;
//import com.chunmiao.domain.RoleName;
//import com.chunmiao.repository.RoleNameRepository;
//import com.chunmiao.service.RoleNameService;
//import com.chunmiao.service.dto.RoleNameDTO;
//import com.chunmiao.service.mapper.RoleNameMapper;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//import javax.persistence.EntityManager;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * Integration tests for the {@link RoleNameResource} REST controller.
// */
//@SpringBootTest(classes = Demo6App.class)
//@AutoConfigureMockMvc
//@WithMockUser
//public class RoleNameResourceIT {
//
//    private static final String DEFAULT_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_NAME = "BBBBBBBBBB";
//
//    @Autowired
//    private RoleNameRepository roleNameRepository;
//
//    @Autowired
//    private RoleNameMapper roleNameMapper;
//
//    @Autowired
//    private RoleNameService roleNameService;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restRoleNameMockMvc;
//
//    private RoleName roleName;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static RoleName createEntity(EntityManager em) {
//        RoleName roleName = new RoleName()
//            .name(DEFAULT_NAME);
//        return roleName;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static RoleName createUpdatedEntity(EntityManager em) {
//        RoleName roleName = new RoleName()
//            .name(UPDATED_NAME);
//        return roleName;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        roleName = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createRoleName() throws Exception {
//        int databaseSizeBeforeCreate = roleNameRepository.findAll().size();
//        // Create the RoleName
//        RoleNameDTO roleNameDTO = roleNameMapper.toDto(roleName);
//        restRoleNameMockMvc.perform(post("/api/role-names")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(roleNameDTO)))
//            .andExpect(status().isCreated());
//
//        // Validate the RoleName in the database
//        List<RoleName> roleNameList = roleNameRepository.findAll();
//        assertThat(roleNameList).hasSize(databaseSizeBeforeCreate + 1);
//        RoleName testRoleName = roleNameList.get(roleNameList.size() - 1);
//        assertThat(testRoleName.getName()).isEqualTo(DEFAULT_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void createRoleNameWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = roleNameRepository.findAll().size();
//
//        // Create the RoleName with an existing ID
//        roleName.setId(1L);
//        RoleNameDTO roleNameDTO = roleNameMapper.toDto(roleName);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restRoleNameMockMvc.perform(post("/api/role-names")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(roleNameDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the RoleName in the database
//        List<RoleName> roleNameList = roleNameRepository.findAll();
//        assertThat(roleNameList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllRoleNames() throws Exception {
//        // Initialize the database
//        roleNameRepository.saveAndFlush(roleName);
//
//        // Get all the roleNameList
//        restRoleNameMockMvc.perform(get("/api/role-names?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(roleName.getId().intValue())))
//            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
//    }
//
//    @Test
//    @Transactional
//    public void getRoleName() throws Exception {
//        // Initialize the database
//        roleNameRepository.saveAndFlush(roleName);
//
//        // Get the roleName
//        restRoleNameMockMvc.perform(get("/api/role-names/{id}", roleName.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(roleName.getId().intValue()))
//            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
//    }
//    @Test
//    @Transactional
//    public void getNonExistingRoleName() throws Exception {
//        // Get the roleName
//        restRoleNameMockMvc.perform(get("/api/role-names/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateRoleName() throws Exception {
//        // Initialize the database
//        roleNameRepository.saveAndFlush(roleName);
//
//        int databaseSizeBeforeUpdate = roleNameRepository.findAll().size();
//
//        // Update the roleName
//        RoleName updatedRoleName = roleNameRepository.findById(roleName.getId()).get();
//        // Disconnect from session so that the updates on updatedRoleName are not directly saved in db
//        em.detach(updatedRoleName);
//        updatedRoleName
//            .name(UPDATED_NAME);
//        RoleNameDTO roleNameDTO = roleNameMapper.toDto(updatedRoleName);
//
//        restRoleNameMockMvc.perform(put("/api/role-names")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(roleNameDTO)))
//            .andExpect(status().isOk());
//
//        // Validate the RoleName in the database
//        List<RoleName> roleNameList = roleNameRepository.findAll();
//        assertThat(roleNameList).hasSize(databaseSizeBeforeUpdate);
//        RoleName testRoleName = roleNameList.get(roleNameList.size() - 1);
//        assertThat(testRoleName.getName()).isEqualTo(UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingRoleName() throws Exception {
//        int databaseSizeBeforeUpdate = roleNameRepository.findAll().size();
//
//        // Create the RoleName
//        RoleNameDTO roleNameDTO = roleNameMapper.toDto(roleName);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restRoleNameMockMvc.perform(put("/api/role-names")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(roleNameDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the RoleName in the database
//        List<RoleName> roleNameList = roleNameRepository.findAll();
//        assertThat(roleNameList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteRoleName() throws Exception {
//        // Initialize the database
//        roleNameRepository.saveAndFlush(roleName);
//
//        int databaseSizeBeforeDelete = roleNameRepository.findAll().size();
//
//        // Delete the roleName
//        restRoleNameMockMvc.perform(delete("/api/role-names/{id}", roleName.getId())
//            .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<RoleName> roleNameList = roleNameRepository.findAll();
//        assertThat(roleNameList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
