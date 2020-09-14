package co.sistemas.transaccionales.uniminuto.service.impl;

import co.sistemas.transaccionales.uniminuto.service.VuelosService;
import co.sistemas.transaccionales.uniminuto.domain.Vuelos;
import co.sistemas.transaccionales.uniminuto.repository.VuelosRepository;
import co.sistemas.transaccionales.uniminuto.service.dto.VuelosDTO;
import co.sistemas.transaccionales.uniminuto.service.mapper.VuelosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Vuelos}.
 */
@Service
@Transactional
public class VuelosServiceImpl implements VuelosService {

    private final Logger log = LoggerFactory.getLogger(VuelosServiceImpl.class);

    private final VuelosRepository vuelosRepository;

    private final VuelosMapper vuelosMapper;

    public VuelosServiceImpl(VuelosRepository vuelosRepository, VuelosMapper vuelosMapper) {
        this.vuelosRepository = vuelosRepository;
        this.vuelosMapper = vuelosMapper;
    }

    /**
     * Save a vuelos.
     *
     * @param vuelosDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VuelosDTO save(VuelosDTO vuelosDTO) {
        log.debug("Request to save Vuelos : {}", vuelosDTO);
        Vuelos vuelos = vuelosMapper.toEntity(vuelosDTO);
        vuelos = vuelosRepository.save(vuelos);
        return vuelosMapper.toDto(vuelos);
    }

    /**
     * Get all the vuelos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VuelosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Vuelos");
        return vuelosRepository.findAll(pageable)
            .map(vuelosMapper::toDto);
    }


    /**
     * Get one vuelos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VuelosDTO> findOne(Long id) {
        log.debug("Request to get Vuelos : {}", id);
        return vuelosRepository.findById(id)
            .map(vuelosMapper::toDto);
    }

    /**
     * Delete the vuelos by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vuelos : {}", id);
        vuelosRepository.deleteById(id);
    }
}
