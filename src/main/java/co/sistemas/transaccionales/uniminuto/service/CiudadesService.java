package co.sistemas.transaccionales.uniminuto.service;

import co.sistemas.transaccionales.uniminuto.service.dto.CiudadesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.sistemas.transaccionales.uniminuto.domain.Ciudades}.
 */
public interface CiudadesService {

    /**
     * Save a ciudades.
     *
     * @param ciudadesDTO the entity to save.
     * @return the persisted entity.
     */
    CiudadesDTO save(CiudadesDTO ciudadesDTO);

    /**
     * Get all the ciudades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CiudadesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ciudades.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CiudadesDTO> findOne(Long id);

    /**
     * Delete the "id" ciudades.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
