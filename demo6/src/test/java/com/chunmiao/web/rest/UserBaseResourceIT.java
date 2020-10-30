//package com.chunmiao.web.rest;
//
//import com.chunmiao.Demo6App;
//import com.chunmiao.domain.UserBase;
//import com.chunmiao.repository.UserBaseRepository;
//import com.chunmiao.service.UserBaseService;
//import com.chunmiao.service.dto.UserBaseDTO;
//import com.chunmiao.service.mapper.UserBaseMapper;
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
// * Integration tests for the {@link UserBaseResource} REST controller.
// */
//@SpringBootTest(classes = Demo6App.class)
//@AutoConfigureMockMvc
//@WithMockUser
//public class UserBaseResourceIT {
//
//    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
//    private static final String UPDATED_USERNAME = "BBBBBBBBBB";
//
//    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
//    private static final String UPDATED_PHONE = "BBBBBBBBBB";
//
//    @Autowired
//    private UserBaseRepository userBaseRepository;
//
//    @Autowired
//    private UserBaseMapper userBaseMapper;
//
//    @Autowired
//    private UserBaseService userBaseService;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restUserBaseMockMvc;
//
//    private UserBase userBase;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static UserBase createEntity(EntityManager em) {
//        UserBase userBase = new UserBase()
//            .username(DEFAULT_USERNAME)
//            .phone(DEFAULT_PHONE);
//        return userBase;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static UserBase createUpdatedEntity(EntityManager em) {
//        UserBase userBase = new UserBase()
//            .username(UPDATED_USERNAME)
//            .phone(UPDATED_PHONE);
//        return userBase;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        userBase = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createUserBase() throws Exception {
//        int databaseSizeBeforeCreate = userBaseRepository.findAll().size();
//        // Create the UserBase
//        UserBaseDTO userBaseDTO = userBaseMapper.toDto(userBase);
//        restUserBaseMockMvc.perform(post("/api/user-bases")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(userBaseDTO)))
//            .andExpect(status().isCreated());
//
//        // Validate the UserBase in the database
//        List<UserBase> userBaseList = userBaseRepository.findAll();
//        assertThat(userBaseList).hasSize(databaseSizeBeforeCreate + 1);
//        UserBase testUserBase = userBaseList.get(userBaseList.size() - 1);
//        assertThat(testUserBase.getUsername()).isEqualTo(DEFAULT_USERNAME);
//        assertThat(testUserBase.getPhone()).isEqualTo(DEFAULT_PHONE);
//    }
//
//    @Test
//    @Transactional
//    public void createUserBaseWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = userBaseRepository.findAll().size();
//
//        // Create the UserBase with an existing ID
//        userBase.setId(1L);
//        UserBaseDTO userBaseDTO = userBaseMapper.toDto(userBase);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restUserBaseMockMvc.perform(post("/api/user-bases")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(userBaseDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the UserBase in the database
//        List<UserBase> userBaseList = userBaseRepository.findAll();
//        assertThat(userBaseList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllUserBases() throws Exception {
//        // Initialize the database
//        userBaseRepository.saveAndFlush(userBase);
//
//        // Get all the userBaseList
//        restUserBaseMockMvc.perform(get("/api/user-bases?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(userBase.getId().intValue())))
//            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
//            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
//    }
//
//    @Test
//    @Transactional
//    public void getUserBase() throws Exception {
//        // Initialize the database
//        userBaseRepository.saveAndFlush(userBase);
//
//        // Get the userBase
//        restUserBaseMockMvc.perform(get("/api/user-bases/{id}", userBase.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(userBase.getId().intValue()))
//            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
//            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
//    }
//    @Test
//    @Transactional
//    public void getNonExistingUserBase() throws Exception {
//        // Get the userBase
//        restUserBaseMockMvc.perform(get("/api/user-bases/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateUserBase() throws Exception {
//        // Initialize the database
//        userBaseRepository.saveAndFlush(userBase);
//
//        int databaseSizeBeforeUpdate = userBaseRepository.findAll().size();
//
//        // Update the userBase
//        UserBase updatedUserBase = userBaseRepository.findById(userBase.getId()).get();
//        // Disconnect from session so that the updates on updatedUserBase are not directly saved in db
//        em.detach(updatedUserBase);
//        updatedUserBase
//            .username(UPDATED_USERNAME)
//            .phone(UPDATED_PHONE);
//        UserBaseDTO userBaseDTO = userBaseMapper.toDto(updatedUserBase);
//
//        restUserBaseMockMvc.perform(put("/api/user-bases")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(userBaseDTO)))
//            .andExpect(status().isOk());
//
//        // Validate the UserBase in the database
//        List<UserBase> userBaseList = userBaseRepository.findAll();
//        assertThat(userBaseList).hasSize(databaseSizeBeforeUpdate);
//        UserBase testUserBase = userBaseList.get(userBaseList.size() - 1);
//        assertThat(testUserBase.getUsername()).isEqualTo(UPDATED_USERNAME);
//        assertThat(testUserBase.getPhone()).isEqualTo(UPDATED_PHONE);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingUserBase() throws Exception {
//        int databaseSizeBeforeUpdate = userBaseRepository.findAll().size();
//
//        // Create the UserBase
//        UserBaseDTO userBaseDTO = userBaseMapper.toDto(userBase);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restUserBaseMockMvc.perform(put("/api/user-bases")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(userBaseDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the UserBase in the database
//        List<UserBase> userBaseList = userBaseRepository.findAll();
//        assertThat(userBaseList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteUserBase() throws Exception {
//        // Initialize the database
//        userBaseRepository.saveAndFlush(userBase);
//
//        int databaseSizeBeforeDelete = userBaseRepository.findAll().size();
//
//        // Delete the userBase
//        restUserBaseMockMvc.perform(delete("/api/user-bases/{id}", userBase.getId())
//            .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<UserBase> userBaseList = userBaseRepository.findAll();
//        assertThat(userBaseList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
