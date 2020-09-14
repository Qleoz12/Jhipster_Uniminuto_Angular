package co.sistemas.transaccionales.uniminuto.service;

import co.sistemas.transaccionales.uniminuto.service.dto.AvionesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.sistemas.transaccionales.uniminuto.domain.Aviones}.
 */
public interface AvionesService {

    /**
     * Save a aviones.
     *
     * @param avionesDTO the entity to save.
     * @return the persisted entity.
     */
    AvionesDTO save(AvionesDTO avionesDTO);

    /**
     * Get all the aviones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AvionesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" aviones.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AvionesDTO> findOne(Long id);

    /**
     * Delete the "id" aviones.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
