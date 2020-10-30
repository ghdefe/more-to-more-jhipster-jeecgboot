package com.chunmiao.web.rest;

import com.chunmiao.domain.UserBaseRoleName;
import com.chunmiao.repository.UserBaseRoleNameRepository;
import com.chunmiao.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.chunmiao.domain.UserBaseRoleName}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserBaseRoleNameResource {

    private final Logger log = LoggerFactory.getLogger(UserBaseRoleNameResource.class);

    private static final String ENTITY_NAME = "userBaseRoleName";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserBaseRoleNameRepository userBaseRoleNameRepository;

    public UserBaseRoleNameResource(UserBaseRoleNameRepository userBaseRoleNameRepository) {
        this.userBaseRoleNameRepository = userBaseRoleNameRepository;
    }

    /**
     * {@code POST  /user-base-role-names} : Create a new userBaseRoleName.
     *
     * @param userBaseRoleName the userBaseRoleName to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userBaseRoleName, or with status {@code 400 (Bad Request)} if the userBaseRoleName has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-base-role-names")
    public ResponseEntity<UserBaseRoleName> createUserBaseRoleName(@RequestBody UserBaseRoleName userBaseRoleName) throws URISyntaxException {
        log.debug("REST request to save UserBaseRoleName : {}", userBaseRoleName);
        if (userBaseRoleName.getId() != null) {
            throw new BadRequestAlertException("A new userBaseRoleName cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserBaseRoleName result = userBaseRoleNameRepository.save(userBaseRoleName);
        return ResponseEntity.created(new URI("/api/user-base-role-names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-base-role-names} : Updates an existing userBaseRoleName.
     *
     * @param userBaseRoleName the userBaseRoleName to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userBaseRoleName,
     * or with status {@code 400 (Bad Request)} if the userBaseRoleName is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userBaseRoleName couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-base-role-names")
    public ResponseEntity<UserBaseRoleName> updateUserBaseRoleName(@RequestBody UserBaseRoleName userBaseRoleName) throws URISyntaxException {
        log.debug("REST request to update UserBaseRoleName : {}", userBaseRoleName);
        if (userBaseRoleName.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserBaseRoleName result = userBaseRoleNameRepository.save(userBaseRoleName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userBaseRoleName.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-base-role-names} : get all the userBaseRoleNames.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userBaseRoleNames in body.
     */
    @GetMapping("/user-base-role-names")
    public List<UserBaseRoleName> getAllUserBaseRoleNames() {
        log.debug("REST request to get all UserBaseRoleNames");
        return userBaseRoleNameRepository.findAll();
    }

    /**
     * {@code GET  /user-base-role-names/:id} : get the "id" userBaseRoleName.
     *
     * @param id the id of the userBaseRoleName to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userBaseRoleName, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-base-role-names/{id}")
    public ResponseEntity<UserBaseRoleName> getUserBaseRoleName(@PathVariable Long id) {
        log.debug("REST request to get UserBaseRoleName : {}", id);
        Optional<UserBaseRoleName> userBaseRoleName = userBaseRoleNameRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userBaseRoleName);
    }

    /**
     * {@code DELETE  /user-base-role-names/:id} : delete the "id" userBaseRoleName.
     *
     * @param id the id of the userBaseRoleName to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-base-role-names/{id}")
    public ResponseEntity<Void> deleteUserBaseRoleName(@PathVariable Long id) {
        log.debug("REST request to delete UserBaseRoleName : {}", id);
        userBaseRoleNameRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
