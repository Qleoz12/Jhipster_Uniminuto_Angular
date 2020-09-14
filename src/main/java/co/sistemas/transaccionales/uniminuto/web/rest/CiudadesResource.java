package co.sistemas.transaccionales.uniminuto.web.rest;

import co.sistemas.transaccionales.uniminuto.service.CiudadesService;
import co.sistemas.transaccionales.uniminuto.web.rest.errors.BadRequestAlertException;
import co.sistemas.transaccionales.uniminuto.service.dto.CiudadesDTO;

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
 * REST controller for managing {@link co.sistemas.transaccionales.uniminuto.domain.Ciudades}.
 */
@RestController
@RequestMapping("/api")
public class CiudadesResource {

    private final Logger log = LoggerFactory.getLogger(CiudadesResource.class);

    private static final String ENTITY_NAME = "ciudades";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CiudadesService ciudadesService;

    public CiudadesResource(CiudadesService ciudadesService) {
        this.ciudadesService = ciudadesService;
    }

    /**
     * {@code POST  /ciudades} : Create a new ciudades.
     *
     * @param ciudadesDTO the ciudadesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ciudadesDTO, or with status {@code 400 (Bad Request)} if the ciudades has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ciudades")
    public ResponseEntity<CiudadesDTO> createCiudades(@RequestBody CiudadesDTO ciudadesDTO) throws URISyntaxException {
        log.debug("REST request to save Ciudades : {}", ciudadesDTO);
        if (ciudadesDTO.getId() != null) {
            throw new BadRequestAlertException("A new ciudades cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CiudadesDTO result = ciudadesService.save(ciudadesDTO);
        return ResponseEntity.created(new URI("/api/ciudades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ciudades} : Updates an existing ciudades.
     *
     * @param ciudadesDTO the ciudadesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ciudadesDTO,
     * or with status {@code 400 (Bad Request)} if the ciudadesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ciudadesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ciudades")
    public ResponseEntity<CiudadesDTO> updateCiudades(@RequestBody CiudadesDTO ciudadesDTO) throws URISyntaxException {
        log.debug("REST request to update Ciudades : {}", ciudadesDTO);
        if (ciudadesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CiudadesDTO result = ciudadesService.save(ciudadesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ciudadesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ciudades} : get all the ciudades.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ciudades in body.
     */
    @GetMapping("/ciudades")
    public ResponseEntity<List<CiudadesDTO>> getAllCiudades(Pageable pageable) {
        log.debug("REST request to get a page of Ciudades");
        Page<CiudadesDTO> page = ciudadesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ciudades/:id} : get the "id" ciudades.
     *
     * @param id the id of the ciudadesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ciudadesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ciudades/{id}")
    public ResponseEntity<CiudadesDTO> getCiudades(@PathVariable Long id) {
        log.debug("REST request to get Ciudades : {}", id);
        Optional<CiudadesDTO> ciudadesDTO = ciudadesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ciudadesDTO);
    }

    /**
     * {@code DELETE  /ciudades/:id} : delete the "id" ciudades.
     *
     * @param id the id of the ciudadesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ciudades/{id}")
    public ResponseEntity<Void> deleteCiudades(@PathVariable Long id) {
        log.debug("REST request to delete Ciudades : {}", id);
        ciudadesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
