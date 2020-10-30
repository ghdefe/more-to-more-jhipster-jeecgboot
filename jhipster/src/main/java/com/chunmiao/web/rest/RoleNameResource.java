package com.chunmiao.web.rest;

import com.chunmiao.service.RoleNameService;
import com.chunmiao.web.rest.errors.BadRequestAlertException;
import com.chunmiao.service.dto.RoleNameDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.chunmiao.domain.RoleName}.
 */
@RestController
@RequestMapping("/api")
public class RoleNameResource {

    private final Logger log = LoggerFactory.getLogger(RoleNameResource.class);

    private static final String ENTITY_NAME = "roleName";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoleNameService roleNameService;

    public RoleNameResource(RoleNameService roleNameService) {
        this.roleNameService = roleNameService;
    }

    /**
     * {@code POST  /role-names} : Create a new roleName.
     *
     * @param roleNameDTO the roleNameDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roleNameDTO, or with status {@code 400 (Bad Request)} if the roleName has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/role-names")
    public ResponseEntity<RoleNameDTO> createRoleName(@RequestBody RoleNameDTO roleNameDTO) throws URISyntaxException {
        log.debug("REST request to save RoleName : {}", roleNameDTO);
        if (roleNameDTO.getId() != null) {
            throw new BadRequestAlertException("A new roleName cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleNameDTO result = roleNameService.save(roleNameDTO);
        return ResponseEntity.created(new URI("/api/role-names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /role-names} : Updates an existing roleName.
     *
     * @param roleNameDTO the roleNameDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleNameDTO,
     * or with status {@code 400 (Bad Request)} if the roleNameDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roleNameDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/role-names")
    public ResponseEntity<RoleNameDTO> updateRoleName(@RequestBody RoleNameDTO roleNameDTO) throws URISyntaxException {
        log.debug("REST request to update RoleName : {}", roleNameDTO);
        if (roleNameDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RoleNameDTO result = roleNameService.save(roleNameDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roleNameDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /role-names} : get all the roleNames.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roleNames in body.
     */
    @GetMapping("/role-names")
    public List<RoleNameDTO> getAllRoleNames() {
        log.debug("REST request to get all RoleNames");
        return roleNameService.findAll();
    }

    /**
     * {@code GET  /role-names/:id} : get the "id" roleName.
     *
     * @param id the id of the roleNameDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roleNameDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/role-names/{id}")
    public ResponseEntity<RoleNameDTO> getRoleName(@PathVariable Long id) {
        log.debug("REST request to get RoleName : {}", id);
        Optional<RoleNameDTO> roleNameDTO = roleNameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roleNameDTO);
    }

    /**
     * {@code DELETE  /role-names/:id} : delete the "id" roleName.
     *
     * @param id the id of the roleNameDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/role-names/{id}")
    public ResponseEntity<Void> deleteRoleName(@PathVariable Long id) {
        log.debug("REST request to delete RoleName : {}", id);
        roleNameService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
