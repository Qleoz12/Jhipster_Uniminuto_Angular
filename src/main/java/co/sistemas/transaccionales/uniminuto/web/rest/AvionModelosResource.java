package co.sistemas.transaccionales.uniminuto.web.rest;

import co.sistemas.transaccionales.uniminuto.service.AvionModelosService;
import co.sistemas.transaccionales.uniminuto.web.rest.errors.BadRequestAlertException;
import co.sistemas.transaccionales.uniminuto.service.dto.AvionModelosDTO;

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
 * REST controller for managing {@link co.sistemas.transaccionales.uniminuto.domain.AvionModelos}.
 */
@RestController
@RequestMapping("/api")
public class AvionModelosResource {

    private final Logger log = LoggerFactory.getLogger(AvionModelosResource.class);

    private static final String ENTITY_NAME = "avionModelos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvionModelosService avionModelosService;

    public AvionModelosResource(AvionModelosService avionModelosService) {
        this.avionModelosService = avionModelosService;
    }

    /**
     * {@code POST  /avion-modelos} : Create a new avionModelos.
     *
     * @param avionModelosDTO the avionModelosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avionModelosDTO, or with status {@code 400 (Bad Request)} if the avionModelos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/avion-modelos")
    public ResponseEntity<AvionModelosDTO> createAvionModelos(@RequestBody AvionModelosDTO avionModelosDTO) throws URISyntaxException {
        log.debug("REST request to save AvionModelos : {}", avionModelosDTO);
        if (avionModelosDTO.getId() != null) {
            throw new BadRequestAlertException("A new avionModelos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvionModelosDTO result = avionModelosService.save(avionModelosDTO);
        return ResponseEntity.created(new URI("/api/avion-modelos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avion-modelos} : Updates an existing avionModelos.
     *
     * @param avionModelosDTO the avionModelosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avionModelosDTO,
     * or with status {@code 400 (Bad Request)} if the avionModelosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avionModelosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/avion-modelos")
    public ResponseEntity<AvionModelosDTO> updateAvionModelos(@RequestBody AvionModelosDTO avionModelosDTO) throws URISyntaxException {
        log.debug("REST request to update AvionModelos : {}", avionModelosDTO);
        if (avionModelosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AvionModelosDTO result = avionModelosService.save(avionModelosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avionModelosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /avion-modelos} : get all the avionModelos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avionModelos in body.
     */
    @GetMapping("/avion-modelos")
    public ResponseEntity<List<AvionModelosDTO>> getAllAvionModelos(Pageable pageable) {
        log.debug("REST request to get a page of AvionModelos");
        Page<AvionModelosDTO> page = avionModelosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /avion-modelos/:id} : get the "id" avionModelos.
     *
     * @param id the id of the avionModelosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avionModelosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/avion-modelos/{id}")
    public ResponseEntity<AvionModelosDTO> getAvionModelos(@PathVariable Long id) {
        log.debug("REST request to get AvionModelos : {}", id);
        Optional<AvionModelosDTO> avionModelosDTO = avionModelosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avionModelosDTO);
    }

    /**
     * {@code DELETE  /avion-modelos/:id} : delete the "id" avionModelos.
     *
     * @param id the id of the avionModelosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/avion-modelos/{id}")
    public ResponseEntity<Void> deleteAvionModelos(@PathVariable Long id) {
        log.debug("REST request to delete AvionModelos : {}", id);
        avionModelosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
