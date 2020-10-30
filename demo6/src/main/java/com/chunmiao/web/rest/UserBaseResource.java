package com.chunmiao.web.rest;

import com.chunmiao.service.UserBaseService;
import com.chunmiao.web.rest.errors.BadRequestAlertException;
import com.chunmiao.service.dto.UserBaseDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.chunmiao.domain.UserBase}.
 */
@RestController
@RequestMapping("/api")
public class UserBaseResource {

    private final Logger log = LoggerFactory.getLogger(UserBaseResource.class);

    private static final String ENTITY_NAME = "userBase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserBaseService userBaseService;

    public UserBaseResource(UserBaseService userBaseService) {
        this.userBaseService = userBaseService;
    }

    /**
     * {@code POST  /user-bases} : Create a new userBase.
     *
     * @param userBaseDTO the userBaseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userBaseDTO, or with status {@code 400 (Bad Request)} if the userBase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-bases")
    public ResponseEntity<UserBaseDTO> createUserBase(@Valid @RequestBody UserBaseDTO userBaseDTO) throws URISyntaxException {
        log.debug("REST request to save UserBase : {}", userBaseDTO);
        if (userBaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new userBase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserBaseDTO result = userBaseService.save(userBaseDTO);
        return ResponseEntity.created(new URI("/api/user-bases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-bases} : Updates an existing userBase.
     *
     * @param userBaseDTO the userBaseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userBaseDTO,
     * or with status {@code 400 (Bad Request)} if the userBaseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userBaseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-bases")
    public ResponseEntity<UserBaseDTO> updateUserBase(@Valid @RequestBody UserBaseDTO userBaseDTO) throws URISyntaxException {
        log.debug("REST request to update UserBase : {}", userBaseDTO);
        if (userBaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserBaseDTO result = userBaseService.save(userBaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userBaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-bases} : get all the userBases.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userBases in body.
     */
    @GetMapping("/user-bases")
    public List<UserBaseDTO> getAllUserBases() {
        log.debug("REST request to get all UserBases");
        return userBaseService.findAll();
    }

    /**
     * {@code GET  /user-bases/:id} : get the "id" userBase.
     *
     * @param id the id of the userBaseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userBaseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-bases/{id}")
    public ResponseEntity<UserBaseDTO> getUserBase(@PathVariable Long id) {
        log.debug("REST request to get UserBase : {}", id);
        Optional<UserBaseDTO> userBaseDTO = userBaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userBaseDTO);
    }

    /**
     * {@code DELETE  /user-bases/:id} : delete the "id" userBase.
     *
     * @param id the id of the userBaseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-bases/{id}")
    public ResponseEntity<Void> deleteUserBase(@PathVariable Long id) {
        log.debug("REST request to delete UserBase : {}", id);
        userBaseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
