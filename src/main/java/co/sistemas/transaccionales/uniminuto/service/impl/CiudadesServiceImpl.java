package co.sistemas.transaccionales.uniminuto.service.impl;

import co.sistemas.transaccionales.uniminuto.service.CiudadesService;
import co.sistemas.transaccionales.uniminuto.domain.Ciudades;
import co.sistemas.transaccionales.uniminuto.repository.CiudadesRepository;
import co.sistemas.transaccionales.uniminuto.service.dto.CiudadesDTO;
import co.sistemas.transaccionales.uniminuto.service.mapper.CiudadesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Ciudades}.
 */
@Service
@Transactional
public class CiudadesServiceImpl implements CiudadesService {

    private final Logger log = LoggerFactory.getLogger(CiudadesServiceImpl.class);

    private final CiudadesRepository ciudadesRepository;

    private final CiudadesMapper ciudadesMapper;

    public CiudadesServiceImpl(CiudadesRepository ciudadesRepository, CiudadesMapper ciudadesMapper) {
        this.ciudadesRepository = ciudadesRepository;
        this.ciudadesMapper = ciudadesMapper;
    }

    /**
     * Save a ciudades.
     *
     * @param ciudadesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CiudadesDTO save(CiudadesDTO ciudadesDTO) {
        log.debug("Request to save Ciudades : {}", ciudadesDTO);
        Ciudades ciudades = ciudadesMapper.toEntity(ciudadesDTO);
        ciudades = ciudadesRepository.save(ciudades);
        return ciudadesMapper.toDto(ciudades);
    }

    /**
     * Get all the ciudades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CiudadesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ciudades");
        return ciudadesRepository.findAll(pageable)
            .map(ciudadesMapper::toDto);
    }


    /**
     * Get one ciudades by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CiudadesDTO> findOne(Long id) {
        log.debug("Request to get Ciudades : {}", id);
        return ciudadesRepository.findById(id)
            .map(ciudadesMapper::toDto);
    }

    /**
     * Delete the ciudades by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ciudades : {}", id);
        ciudadesRepository.deleteById(id);
    }
}
