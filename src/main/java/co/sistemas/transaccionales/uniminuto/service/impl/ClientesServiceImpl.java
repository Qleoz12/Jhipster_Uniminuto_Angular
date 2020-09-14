package co.sistemas.transaccionales.uniminuto.service.impl;

import co.sistemas.transaccionales.uniminuto.service.ClientesService;
import co.sistemas.transaccionales.uniminuto.domain.Clientes;
import co.sistemas.transaccionales.uniminuto.repository.ClientesRepository;
import co.sistemas.transaccionales.uniminuto.service.dto.ClientesDTO;
import co.sistemas.transaccionales.uniminuto.service.mapper.ClientesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Clientes}.
 */
@Service
@Transactional
public class ClientesServiceImpl implements ClientesService {

    private final Logger log = LoggerFactory.getLogger(ClientesServiceImpl.class);

    private final ClientesRepository clientesRepository;

    private final ClientesMapper clientesMapper;

    public ClientesServiceImpl(ClientesRepository clientesRepository, ClientesMapper clientesMapper) {
        this.clientesRepository = clientesRepository;
        this.clientesMapper = clientesMapper;
    }

    /**
     * Save a clientes.
     *
     * @param clientesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ClientesDTO save(ClientesDTO clientesDTO) {
        log.debug("Request to save Clientes : {}", clientesDTO);
        Clientes clientes = clientesMapper.toEntity(clientesDTO);
        clientes = clientesRepository.save(clientes);
        return clientesMapper.toDto(clientes);
    }

    /**
     * Get all the clientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClientesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clientes");
        return clientesRepository.findAll(pageable)
            .map(clientesMapper::toDto);
    }


    /**
     * Get one clientes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClientesDTO> findOne(Long id) {
        log.debug("Request to get Clientes : {}", id);
        return clientesRepository.findById(id)
            .map(clientesMapper::toDto);
    }

    /**
     * Delete the clientes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Clientes : {}", id);
        clientesRepository.deleteById(id);
    }
}
