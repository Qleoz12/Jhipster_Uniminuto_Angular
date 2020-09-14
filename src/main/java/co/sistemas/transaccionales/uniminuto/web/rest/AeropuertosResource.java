package co.sistemas.transaccionales.uniminuto.web.rest;

import co.sistemas.transaccionales.uniminuto.service.AeropuertosService;
import co.sistemas.transaccionales.uniminuto.web.rest.errors.BadRequestAlertException;
import co.sistemas.transaccionales.uniminuto.service.dto.AeropuertosDTO;

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
 * REST controller for managing {@link co.sistemas.transaccionales.uniminuto.domain.Aeropuertos}.
 */
@RestController
@RequestMapping("/api")
public class AeropuertosResource {

    private final Logger log = LoggerFactory.getLogger(AeropuertosResource.class);

    private static final String ENTITY_NAME = "aeropuertos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AeropuertosService aeropuertosService;

    public AeropuertosResource(AeropuertosService aeropuertosService) {
        this.aeropuertosService = aeropuertosService;
    }

    /**
     * {@code POST  /aeropuertos} : Create a new aeropuertos.
     *
     * @param aeropuertosDTO the aeropuertosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aeropuertosDTO, or with status {@code 400 (Bad Request)} if the aeropuertos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aeropuertos")
    public ResponseEntity<AeropuertosDTO> createAeropuertos(@RequestBody AeropuertosDTO aeropuertosDTO) throws URISyntaxException {
        log.debug("REST request to save Aeropuertos : {}", aeropuertosDTO);
        if (aeropuertosDTO.getId() != null) {
            throw new BadRequestAlertException("A new aeropuertos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AeropuertosDTO result = aeropuertosService.save(aeropuertosDTO);
        return ResponseEntity.created(new URI("/api/aeropuertos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aeropuertos} : Updates an existing aeropuertos.
     *
     * @param aeropuertosDTO the aeropuertosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aeropuertosDTO,
     * or with status {@code 400 (Bad Request)} if the aeropuertosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aeropuertosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aeropuertos")
    public ResponseEntity<AeropuertosDTO> updateAeropuertos(@RequestBody AeropuertosDTO aeropuertosDTO) throws URISyntaxException {
        log.debug("REST request to update Aeropuertos : {}", aeropuertosDTO);
        if (aeropuertosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AeropuertosDTO result = aeropuertosService.save(aeropuertosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aeropuertosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aeropuertos} : get all the aeropuertos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aeropuertos in body.
     */
    @GetMapping("/aeropuertos")
    public ResponseEntity<List<AeropuertosDTO>> getAllAeropuertos(Pageable pageable) {
        log.debug("REST request to get a page of Aeropuertos");
        Page<AeropuertosDTO> page = aeropuertosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aeropuertos/:id} : get the "id" aeropuertos.
     *
     * @param id the id of the aeropuertosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aeropuertosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aeropuertos/{id}")
    public ResponseEntity<AeropuertosDTO> getAeropuertos(@PathVariable Long id) {
        log.debug("REST request to get Aeropuertos : {}", id);
        Optional<AeropuertosDTO> aeropuertosDTO = aeropuertosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aeropuertosDTO);
    }

    /**
     * {@code DELETE  /aeropuertos/:id} : delete the "id" aeropuertos.
     *
     * @param id the id of the aeropuertosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aeropuertos/{id}")
    public ResponseEntity<Void> deleteAeropuertos(@PathVariable Long id) {
        log.debug("REST request to delete Aeropuertos : {}", id);
        aeropuertosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
