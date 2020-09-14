package co.sistemas.transaccionales.uniminuto.service;

import co.sistemas.transaccionales.uniminuto.service.dto.AvionModelosDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.sistemas.transaccionales.uniminuto.domain.AvionModelos}.
 */
public interface AvionModelosService {

    /**
     * Save a avionModelos.
     *
     * @param avionModelosDTO the entity to save.
     * @return the persisted entity.
     */
    AvionModelosDTO save(AvionModelosDTO avionModelosDTO);

    /**
     * Get all the avionModelos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AvionModelosDTO> findAll(Pageable pageable);


    /**
     * Get the "id" avionModelos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AvionModelosDTO> findOne(Long id);

    /**
     * Delete the "id" avionModelos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
