package co.sistemas.transaccionales.uniminuto.service.impl;

import co.sistemas.transaccionales.uniminuto.service.AvionesService;
import co.sistemas.transaccionales.uniminuto.domain.Aviones;
import co.sistemas.transaccionales.uniminuto.repository.AvionesRepository;
import co.sistemas.transaccionales.uniminuto.service.dto.AvionesDTO;
import co.sistemas.transaccionales.uniminuto.service.mapper.AvionesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Aviones}.
 */
@Service
@Transactional
public class AvionesServiceImpl implements AvionesService {

    private final Logger log = LoggerFactory.getLogger(AvionesServiceImpl.class);

    private final AvionesRepository avionesRepository;

    private final AvionesMapper avionesMapper;

    public AvionesServiceImpl(AvionesRepository avionesRepository, AvionesMapper avionesMapper) {
        this.avionesRepository = avionesRepository;
        this.avionesMapper = avionesMapper;
    }

    /**
     * Save a aviones.
     *
     * @param avionesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AvionesDTO save(AvionesDTO avionesDTO) {
        log.debug("Request to save Aviones : {}", avionesDTO);
        Aviones aviones = avionesMapper.toEntity(avionesDTO);
        aviones = avionesRepository.save(aviones);
        return avionesMapper.toDto(aviones);
    }

    /**
     * Get all the aviones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AvionesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Aviones");
        return avionesRepository.findAll(pageable)
            .map(avionesMapper::toDto);
    }


    /**
     * Get one aviones by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AvionesDTO> findOne(Long id) {
        log.debug("Request to get Aviones : {}", id);
        return avionesRepository.findById(id)
            .map(avionesMapper::toDto);
    }

    /**
     * Delete the aviones by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Aviones : {}", id);
        avionesRepository.deleteById(id);
    }
}
