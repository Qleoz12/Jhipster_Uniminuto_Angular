package co.sistemas.transaccionales.uniminuto.web.rest;

import co.sistemas.transaccionales.uniminuto.service.ClientesService;
import co.sistemas.transaccionales.uniminuto.web.rest.errors.BadRequestAlertException;
import co.sistemas.transaccionales.uniminuto.service.dto.ClientesDTO;

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
 * REST controller for managing {@link co.sistemas.transaccionales.uniminuto.domain.Clientes}.
 */
@RestController
@RequestMapping("/api")
public class ClientesResource {

    private final Logger log = LoggerFactory.getLogger(ClientesResource.class);

    private static final String ENTITY_NAME = "clientes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientesService clientesService;

    public ClientesResource(ClientesService clientesService) {
        this.clientesService = clientesService;
    }

    /**
     * {@code POST  /clientes} : Create a new clientes.
     *
     * @param clientesDTO the clientesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientesDTO, or with status {@code 400 (Bad Request)} if the clientes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clientes")
    public ResponseEntity<ClientesDTO> createClientes(@RequestBody ClientesDTO clientesDTO) throws URISyntaxException {
        log.debug("REST request to save Clientes : {}", clientesDTO);
        if (clientesDTO.getId() != null) {
            throw new BadRequestAlertException("A new clientes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientesDTO result = clientesService.save(clientesDTO);
        return ResponseEntity.created(new URI("/api/clientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clientes} : Updates an existing clientes.
     *
     * @param clientesDTO the clientesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientesDTO,
     * or with status {@code 400 (Bad Request)} if the clientesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clientes")
    public ResponseEntity<ClientesDTO> updateClientes(@RequestBody ClientesDTO clientesDTO) throws URISyntaxException {
        log.debug("REST request to update Clientes : {}", clientesDTO);
        if (clientesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientesDTO result = clientesService.save(clientesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /clientes} : get all the clientes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientes in body.
     */
    @GetMapping("/clientes")
    public ResponseEntity<List<ClientesDTO>> getAllClientes(Pageable pageable) {
        log.debug("REST request to get a page of Clientes");
        Page<ClientesDTO> page = clientesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /clientes/:id} : get the "id" clientes.
     *
     * @param id the id of the clientesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clientes/{id}")
    public ResponseEntity<ClientesDTO> getClientes(@PathVariable Long id) {
        log.debug("REST request to get Clientes : {}", id);
        Optional<ClientesDTO> clientesDTO = clientesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientesDTO);
    }

    /**
     * {@code DELETE  /clientes/:id} : delete the "id" clientes.
     *
     * @param id the id of the clientesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> deleteClientes(@PathVariable Long id) {
        log.debug("REST request to delete Clientes : {}", id);
        clientesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
