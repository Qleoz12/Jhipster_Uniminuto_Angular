package co.sistemas.transaccionales.uniminuto.service;

import co.sistemas.transaccionales.uniminuto.service.dto.TicketsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.sistemas.transaccionales.uniminuto.domain.Tickets}.
 */
public interface TicketsService {

    /**
     * Save a tickets.
     *
     * @param ticketsDTO the entity to save.
     * @return the persisted entity.
     */
    TicketsDTO save(TicketsDTO ticketsDTO);

    /**
     * Get all the tickets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TicketsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tickets.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TicketsDTO> findOne(Long id);

    /**
     * Delete the "id" tickets.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
