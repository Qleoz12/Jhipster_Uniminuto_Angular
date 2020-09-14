package co.sistemas.transaccionales.uniminuto.service.impl;

import co.sistemas.transaccionales.uniminuto.service.AvionModelosService;
import co.sistemas.transaccionales.uniminuto.domain.AvionModelos;
import co.sistemas.transaccionales.uniminuto.repository.AvionModelosRepository;
import co.sistemas.transaccionales.uniminuto.service.dto.AvionModelosDTO;
import co.sistemas.transaccionales.uniminuto.service.mapper.AvionModelosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AvionModelos}.
 */
@Service
@Transactional
public class AvionModelosServiceImpl implements AvionModelosService {

    private final Logger log = LoggerFactory.getLogger(AvionModelosServiceImpl.class);

    private final AvionModelosRepository avionModelosRepository;

    private final AvionModelosMapper avionModelosMapper;

    public AvionModelosServiceImpl(AvionModelosRepository avionModelosRepository, AvionModelosMapper avionModelosMapper) {
        this.avionModelosRepository = avionModelosRepository;
        this.avionModelosMapper = avionModelosMapper;
    }

    /**
     * Save a avionModelos.
     *
     * @param avionModelosDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AvionModelosDTO save(AvionModelosDTO avionModelosDTO) {
        log.debug("Request to save AvionModelos : {}", avionModelosDTO);
        AvionModelos avionModelos = avionModelosMapper.toEntity(avionModelosDTO);
        avionModelos = avionModelosRepository.save(avionModelos);
        return avionModelosMapper.toDto(avionModelos);
    }

    /**
     * Get all the avionModelos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AvionModelosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AvionModelos");
        return avionModelosRepository.findAll(pageable)
            .map(avionModelosMapper::toDto);
    }


    /**
     * Get one avionModelos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AvionModelosDTO> findOne(Long id) {
        log.debug("Request to get AvionModelos : {}", id);
        return avionModelosRepository.findById(id)
            .map(avionModelosMapper::toDto);
    }

    /**
     * Delete the avionModelos by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AvionModelos : {}", id);
        avionModelosRepository.deleteById(id);
    }
}
