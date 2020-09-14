package co.sistemas.transaccionales.uniminuto.web.rest;

import co.sistemas.transaccionales.uniminuto.service.TicketsService;
import co.sistemas.transaccionales.uniminuto.web.rest.errors.BadRequestAlertException;
import co.sistemas.transaccionales.uniminuto.service.dto.TicketsDTO;

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
 * REST controller for managing {@link co.sistemas.transaccionales.uniminuto.domain.Tickets}.
 */
@RestController
@RequestMapping("/api")
public class TicketsResource {

    private final Logger log = LoggerFactory.getLogger(TicketsResource.class);

    private static final String ENTITY_NAME = "tickets";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TicketsService ticketsService;

    public TicketsResource(TicketsService ticketsService) {
        this.ticketsService = ticketsService;
    }

    /**
     * {@code POST  /tickets} : Create a new tickets.
     *
     * @param ticketsDTO the ticketsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketsDTO, or with status {@code 400 (Bad Request)} if the tickets has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tickets")
    public ResponseEntity<TicketsDTO> createTickets(@RequestBody TicketsDTO ticketsDTO) throws URISyntaxException {
        log.debug("REST request to save Tickets : {}", ticketsDTO);
        if (ticketsDTO.getId() != null) {
            throw new BadRequestAlertException("A new tickets cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TicketsDTO result = ticketsService.save(ticketsDTO);
        return ResponseEntity.created(new URI("/api/tickets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tickets} : Updates an existing tickets.
     *
     * @param ticketsDTO the ticketsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketsDTO,
     * or with status {@code 400 (Bad Request)} if the ticketsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ticketsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tickets")
    public ResponseEntity<TicketsDTO> updateTickets(@RequestBody TicketsDTO ticketsDTO) throws URISyntaxException {
        log.debug("REST request to update Tickets : {}", ticketsDTO);
        if (ticketsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TicketsDTO result = ticketsService.save(ticketsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ticketsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tickets} : get all the tickets.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tickets in body.
     */
    @GetMapping("/tickets")
    public ResponseEntity<List<TicketsDTO>> getAllTickets(Pageable pageable) {
        log.debug("REST request to get a page of Tickets");
        Page<TicketsDTO> page = ticketsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tickets/:id} : get the "id" tickets.
     *
     * @param id the id of the ticketsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ticketsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tickets/{id}")
    public ResponseEntity<TicketsDTO> getTickets(@PathVariable Long id) {
        log.debug("REST request to get Tickets : {}", id);
        Optional<TicketsDTO> ticketsDTO = ticketsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ticketsDTO);
    }

    /**
     * {@code DELETE  /tickets/:id} : delete the "id" tickets.
     *
     * @param id the id of the ticketsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tickets/{id}")
    public ResponseEntity<Void> deleteTickets(@PathVariable Long id) {
        log.debug("REST request to delete Tickets : {}", id);
        ticketsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
