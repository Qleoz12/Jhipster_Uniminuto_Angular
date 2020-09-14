package co.sistemas.transaccionales.uniminuto.service;

import co.sistemas.transaccionales.uniminuto.service.dto.AeropuertosDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.sistemas.transaccionales.uniminuto.domain.Aeropuertos}.
 */
public interface AeropuertosService {

    /**
     * Save a aeropuertos.
     *
     * @param aeropuertosDTO the entity to save.
     * @return the persisted entity.
     */
    AeropuertosDTO save(AeropuertosDTO aeropuertosDTO);

    /**
     * Get all the aeropuertos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AeropuertosDTO> findAll(Pageable pageable);


    /**
     * Get the "id" aeropuertos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AeropuertosDTO> findOne(Long id);

    /**
     * Delete the "id" aeropuertos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
