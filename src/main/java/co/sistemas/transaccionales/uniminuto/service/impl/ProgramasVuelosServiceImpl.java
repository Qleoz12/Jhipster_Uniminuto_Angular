package co.sistemas.transaccionales.uniminuto.service.impl;

import co.sistemas.transaccionales.uniminuto.service.ProgramasVuelosService;
import co.sistemas.transaccionales.uniminuto.domain.ProgramasVuelos;
import co.sistemas.transaccionales.uniminuto.repository.ProgramasVuelosRepository;
import co.sistemas.transaccionales.uniminuto.service.dto.ProgramasVuelosDTO;
import co.sistemas.transaccionales.uniminuto.service.mapper.ProgramasVuelosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProgramasVuelos}.
 */
@Service
@Transactional
public class ProgramasVuelosServiceImpl implements ProgramasVuelosService {

    private final Logger log = LoggerFactory.getLogger(ProgramasVuelosServiceImpl.class);

    private final ProgramasVuelosRepository programasVuelosRepository;

    private final ProgramasVuelosMapper programasVuelosMapper;

    public ProgramasVuelosServiceImpl(ProgramasVuelosRepository programasVuelosRepository, ProgramasVuelosMapper programasVuelosMapper) {
        this.programasVuelosRepository = programasVuelosRepository;
        this.programasVuelosMapper = programasVuelosMapper;
    }

    /**
     * Save a programasVuelos.
     *
     * @param programasVuelosDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProgramasVuelosDTO save(ProgramasVuelosDTO programasVuelosDTO) {
        log.debug("Request to save ProgramasVuelos : {}", programasVuelosDTO);
        ProgramasVuelos programasVuelos = programasVuelosMapper.toEntity(programasVuelosDTO);
        programasVuelos = programasVuelosRepository.save(programasVuelos);
        return programasVuelosMapper.toDto(programasVuelos);
    }

    /**
     * Get all the programasVuelos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProgramasVuelosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProgramasVuelos");
        return programasVuelosRepository.findAll(pageable)
            .map(programasVuelosMapper::toDto);
    }


    /**
     * Get one programasVuelos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProgramasVuelosDTO> findOne(Long id) {
        log.debug("Request to get ProgramasVuelos : {}", id);
        return programasVuelosRepository.findById(id)
            .map(programasVuelosMapper::toDto);
    }

    /**
     * Delete the programasVuelos by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProgramasVuelos : {}", id);
        programasVuelosRepository.deleteById(id);
    }
}
