package co.sistemas.transaccionales.uniminuto.service;

import co.sistemas.transaccionales.uniminuto.service.dto.ClientesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.sistemas.transaccionales.uniminuto.domain.Clientes}.
 */
public interface ClientesService {

    /**
     * Save a clientes.
     *
     * @param clientesDTO the entity to save.
     * @return the persisted entity.
     */
    ClientesDTO save(ClientesDTO clientesDTO);

    /**
     * Get all the clientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClientesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" clientes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClientesDTO> findOne(Long id);

    /**
     * Delete the "id" clientes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
