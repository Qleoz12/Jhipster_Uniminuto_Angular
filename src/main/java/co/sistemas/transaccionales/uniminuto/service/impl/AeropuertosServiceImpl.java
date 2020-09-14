package co.sistemas.transaccionales.uniminuto.service.impl;

import co.sistemas.transaccionales.uniminuto.service.AeropuertosService;
import co.sistemas.transaccionales.uniminuto.domain.Aeropuertos;
import co.sistemas.transaccionales.uniminuto.repository.AeropuertosRepository;
import co.sistemas.transaccionales.uniminuto.service.dto.AeropuertosDTO;
import co.sistemas.transaccionales.uniminuto.service.mapper.AeropuertosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Aeropuertos}.
 */
@Service
@Transactional
public class AeropuertosServiceImpl implements AeropuertosService {

    private final Logger log = LoggerFactory.getLogger(AeropuertosServiceImpl.class);

    private final AeropuertosRepository aeropuertosRepository;

    private final AeropuertosMapper aeropuertosMapper;

    public AeropuertosServiceImpl(AeropuertosRepository aeropuertosRepository, AeropuertosMapper aeropuertosMapper) {
        this.aeropuertosRepository = aeropuertosRepository;
        this.aeropuertosMapper = aeropuertosMapper;
    }

    /**
     * Save a aeropuertos.
     *
     * @param aeropuertosDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AeropuertosDTO save(AeropuertosDTO aeropuertosDTO) {
        log.debug("Request to save Aeropuertos : {}", aeropuertosDTO);
        Aeropuertos aeropuertos = aeropuertosMapper.toEntity(aeropuertosDTO);
        aeropuertos = aeropuertosRepository.save(aeropuertos);
        return aeropuertosMapper.toDto(aeropuertos);
    }

    /**
     * Get all the aeropuertos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AeropuertosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Aeropuertos");
        return aeropuertosRepository.findAll(pageable)
            .map(aeropuertosMapper::toDto);
    }


    /**
     * Get one aeropuertos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AeropuertosDTO> findOne(Long id) {
        log.debug("Request to get Aeropuertos : {}", id);
        return aeropuertosRepository.findById(id)
            .map(aeropuertosMapper::toDto);
    }

    /**
     * Delete the aeropuertos by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Aeropuertos : {}", id);
        aeropuertosRepository.deleteById(id);
    }
}
