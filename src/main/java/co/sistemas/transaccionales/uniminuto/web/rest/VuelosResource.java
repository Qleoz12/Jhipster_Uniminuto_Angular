package co.sistemas.transaccionales.uniminuto.web.rest;

import co.sistemas.transaccionales.uniminuto.service.VuelosService;
import co.sistemas.transaccionales.uniminuto.web.rest.errors.BadRequestAlertException;
import co.sistemas.transaccionales.uniminuto.service.dto.VuelosDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.sistemas.transaccionales.uniminuto.domain.Vuelos}.
 */
@RestController
@RequestMapping("/api")
public class VuelosResource {

    private final Logger log = LoggerFactory.getLogger(VuelosResource.class);

    private static final String ENTITY_NAME = "vuelos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VuelosService vuelosService;

    public VuelosResource(VuelosService vuelosService) {
        this.vuelosService = vuelosService;
    }

    /**
     * {@code POST  /vuelos} : Create a new vuelos.
     *
     * @param vuelosDTO the vuelosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vuelosDTO, or with status {@code 400 (Bad Request)} if the vuelos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vuelos")
    public ResponseEntity<VuelosDTO> createVuelos(@RequestBody VuelosDTO vuelosDTO) throws URISyntaxException {
        log.debug("REST request to save Vuelos : {}", vuelosDTO);
        if (vuelosDTO.getId() != null) {
            throw new BadRequestAlertException("A new vuelos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VuelosDTO result = vuelosService.save(vuelosDTO);
        return ResponseEntity.created(new URI("/api/vuelos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vuelos} : Updates an existing vuelos.
     *
     * @param vuelosDTO the vuelosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vuelosDTO,
     * or with status {@code 400 (Bad Request)} if the vuelosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vuelosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vuelos")
    public ResponseEntity<VuelosDTO> updateVuelos(@RequestBody VuelosDTO vuelosDTO) throws URISyntaxException {
        log.debug("REST request to update Vuelos : {}", vuelosDTO);
        if (vuelosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VuelosDTO result = vuelosService.save(vuelosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vuelosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vuelos} : get all the vuelos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vuelos in body.
     */
    @GetMapping("/vuelos")
    public ResponseEntity<List<VuelosDTO>> getAllVuelos(Pageable pageable) {
        log.debug("REST request to get a page of Vuelos");
        Page<VuelosDTO> page = vuelosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vuelos/:id} : get the "id" vuelos.
     *
     * @param id the id of the vuelosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vuelosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vuelos/{id}")
    public ResponseEntity<VuelosDTO> getVuelos(@PathVariable Long id) {
        log.debug("REST request to get Vuelos : {}", id);
        Optional<VuelosDTO> vuelosDTO = vuelosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vuelosDTO);
    }

    /**
     * {@code DELETE  /vuelos/:id} : delete the "id" vuelos.
     *
     * @param id the id of the vuelosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vuelos/{id}")
    public ResponseEntity<Void> deleteVuelos(@PathVariable Long id) {
        log.debug("REST request to delete Vuelos : {}", id);
        vuelosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
