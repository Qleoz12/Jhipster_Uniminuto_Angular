package co.sistemas.transaccionales.uniminuto.web.rest;

import co.sistemas.transaccionales.uniminuto.service.AvionesService;
import co.sistemas.transaccionales.uniminuto.web.rest.errors.BadRequestAlertException;
import co.sistemas.transaccionales.uniminuto.service.dto.AvionesDTO;

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
 * REST controller for managing {@link co.sistemas.transaccionales.uniminuto.domain.Aviones}.
 */
@RestController
@RequestMapping("/api")
public class AvionesResource {

    private final Logger log = LoggerFactory.getLogger(AvionesResource.class);

    private static final String ENTITY_NAME = "aviones";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvionesService avionesService;

    public AvionesResource(AvionesService avionesService) {
        this.avionesService = avionesService;
    }

    /**
     * {@code POST  /aviones} : Create a new aviones.
     *
     * @param avionesDTO the avionesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avionesDTO, or with status {@code 400 (Bad Request)} if the aviones has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aviones")
    public ResponseEntity<AvionesDTO> createAviones(@RequestBody AvionesDTO avionesDTO) throws URISyntaxException {
        log.debug("REST request to save Aviones : {}", avionesDTO);
        if (avionesDTO.getId() != null) {
            throw new BadRequestAlertException("A new aviones cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvionesDTO result = avionesService.save(avionesDTO);
        return ResponseEntity.created(new URI("/api/aviones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aviones} : Updates an existing aviones.
     *
     * @param avionesDTO the avionesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avionesDTO,
     * or with status {@code 400 (Bad Request)} if the avionesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avionesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aviones")
    public ResponseEntity<AvionesDTO> updateAviones(@RequestBody AvionesDTO avionesDTO) throws URISyntaxException {
        log.debug("REST request to update Aviones : {}", avionesDTO);
        if (avionesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AvionesDTO result = avionesService.save(avionesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avionesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aviones} : get all the aviones.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aviones in body.
     */
    @GetMapping("/aviones")
    public ResponseEntity<List<AvionesDTO>> getAllAviones(Pageable pageable) {
        log.debug("REST request to get a page of Aviones");
        Page<AvionesDTO> page = avionesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aviones/:id} : get the "id" aviones.
     *
     * @param id the id of the avionesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avionesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aviones/{id}")
    public ResponseEntity<AvionesDTO> getAviones(@PathVariable Long id) {
        log.debug("REST request to get Aviones : {}", id);
        Optional<AvionesDTO> avionesDTO = avionesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avionesDTO);
    }

    /**
     * {@code DELETE  /aviones/:id} : delete the "id" aviones.
     *
     * @param id the id of the avionesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aviones/{id}")
    public ResponseEntity<Void> deleteAviones(@PathVariable Long id) {
        log.debug("REST request to delete Aviones : {}", id);
        avionesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
