package co.sistemas.transaccionales.uniminuto.web.rest;

import co.sistemas.transaccionales.uniminuto.service.ProgramasVuelosService;
import co.sistemas.transaccionales.uniminuto.web.rest.errors.BadRequestAlertException;
import co.sistemas.transaccionales.uniminuto.service.dto.ProgramasVuelosDTO;

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
 * REST controller for managing {@link co.sistemas.transaccionales.uniminuto.domain.ProgramasVuelos}.
 */
@RestController
@RequestMapping("/api")
public class ProgramasVuelosResource {

    private final Logger log = LoggerFactory.getLogger(ProgramasVuelosResource.class);

    private static final String ENTITY_NAME = "programasVuelos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgramasVuelosService programasVuelosService;

    public ProgramasVuelosResource(ProgramasVuelosService programasVuelosService) {
        this.programasVuelosService = programasVuelosService;
    }

    /**
     * {@code POST  /programas-vuelos} : Create a new programasVuelos.
     *
     * @param programasVuelosDTO the programasVuelosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new programasVuelosDTO, or with status {@code 400 (Bad Request)} if the programasVuelos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/programas-vuelos")
    public ResponseEntity<ProgramasVuelosDTO> createProgramasVuelos(@RequestBody ProgramasVuelosDTO programasVuelosDTO) throws URISyntaxException {
        log.debug("REST request to save ProgramasVuelos : {}", programasVuelosDTO);
        if (programasVuelosDTO.getId() != null) {
            throw new BadRequestAlertException("A new programasVuelos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProgramasVuelosDTO result = programasVuelosService.save(programasVuelosDTO);
        return ResponseEntity.created(new URI("/api/programas-vuelos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /programas-vuelos} : Updates an existing programasVuelos.
     *
     * @param programasVuelosDTO the programasVuelosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programasVuelosDTO,
     * or with status {@code 400 (Bad Request)} if the programasVuelosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the programasVuelosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/programas-vuelos")
    public ResponseEntity<ProgramasVuelosDTO> updateProgramasVuelos(@RequestBody ProgramasVuelosDTO programasVuelosDTO) throws URISyntaxException {
        log.debug("REST request to update ProgramasVuelos : {}", programasVuelosDTO);
        if (programasVuelosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProgramasVuelosDTO result = programasVuelosService.save(programasVuelosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programasVuelosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /programas-vuelos} : get all the programasVuelos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programasVuelos in body.
     */
    @GetMapping("/programas-vuelos")
    public ResponseEntity<List<ProgramasVuelosDTO>> getAllProgramasVuelos(Pageable pageable) {
        log.debug("REST request to get a page of ProgramasVuelos");
        Page<ProgramasVuelosDTO> page = programasVuelosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /programas-vuelos/:id} : get the "id" programasVuelos.
     *
     * @param id the id of the programasVuelosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programasVuelosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/programas-vuelos/{id}")
    public ResponseEntity<ProgramasVuelosDTO> getProgramasVuelos(@PathVariable Long id) {
        log.debug("REST request to get ProgramasVuelos : {}", id);
        Optional<ProgramasVuelosDTO> programasVuelosDTO = programasVuelosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(programasVuelosDTO);
    }

    /**
     * {@code DELETE  /programas-vuelos/:id} : delete the "id" programasVuelos.
     *
     * @param id the id of the programasVuelosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/programas-vuelos/{id}")
    public ResponseEntity<Void> deleteProgramasVuelos(@PathVariable Long id) {
        log.debug("REST request to delete ProgramasVuelos : {}", id);
        programasVuelosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
