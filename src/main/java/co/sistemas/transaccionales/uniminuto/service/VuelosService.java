package co.sistemas.transaccionales.uniminuto.service;

import co.sistemas.transaccionales.uniminuto.service.dto.VuelosDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.sistemas.transaccionales.uniminuto.domain.Vuelos}.
 */
public interface VuelosService {

    /**
     * Save a vuelos.
     *
     * @param vuelosDTO the entity to save.
     * @return the persisted entity.
     */
    VuelosDTO save(VuelosDTO vuelosDTO);

    /**
     * Get all the vuelos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VuelosDTO> findAll(Pageable pageable);


    /**
     * Get the "id" vuelos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VuelosDTO> findOne(Long id);

    /**
     * Delete the "id" vuelos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
