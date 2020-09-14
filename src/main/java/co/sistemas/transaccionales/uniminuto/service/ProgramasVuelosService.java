package co.sistemas.transaccionales.uniminuto.service;

import co.sistemas.transaccionales.uniminuto.service.dto.ProgramasVuelosDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.sistemas.transaccionales.uniminuto.domain.ProgramasVuelos}.
 */
public interface ProgramasVuelosService {

    /**
     * Save a programasVuelos.
     *
     * @param programasVuelosDTO the entity to save.
     * @return the persisted entity.
     */
    ProgramasVuelosDTO save(ProgramasVuelosDTO programasVuelosDTO);

    /**
     * Get all the programasVuelos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProgramasVuelosDTO> findAll(Pageable pageable);


    /**
     * Get the "id" programasVuelos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProgramasVuelosDTO> findOne(Long id);

    /**
     * Delete the "id" programasVuelos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
