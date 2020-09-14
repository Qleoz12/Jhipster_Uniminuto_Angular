package co.sistemas.transaccionales.uniminuto.service.impl;

import co.sistemas.transaccionales.uniminuto.service.TicketsService;
import co.sistemas.transaccionales.uniminuto.domain.Tickets;
import co.sistemas.transaccionales.uniminuto.repository.TicketsRepository;
import co.sistemas.transaccionales.uniminuto.service.dto.TicketsDTO;
import co.sistemas.transaccionales.uniminuto.service.mapper.TicketsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Tickets}.
 */
@Service
@Transactional
public class TicketsServiceImpl implements TicketsService {

    private final Logger log = LoggerFactory.getLogger(TicketsServiceImpl.class);

    private final TicketsRepository ticketsRepository;

    private final TicketsMapper ticketsMapper;

    public TicketsServiceImpl(TicketsRepository ticketsRepository, TicketsMapper ticketsMapper) {
        this.ticketsRepository = ticketsRepository;
        this.ticketsMapper = ticketsMapper;
    }

    /**
     * Save a tickets.
     *
     * @param ticketsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TicketsDTO save(TicketsDTO ticketsDTO) {
        log.debug("Request to save Tickets : {}", ticketsDTO);
        Tickets tickets = ticketsMapper.toEntity(ticketsDTO);
        tickets = ticketsRepository.save(tickets);
        return ticketsMapper.toDto(tickets);
    }

    /**
     * Get all the tickets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TicketsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tickets");
        return ticketsRepository.findAll(pageable)
            .map(ticketsMapper::toDto);
    }


    /**
     * Get one tickets by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TicketsDTO> findOne(Long id) {
        log.debug("Request to get Tickets : {}", id);
        return ticketsRepository.findById(id)
            .map(ticketsMapper::toDto);
    }

    /**
     * Delete the tickets by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tickets : {}", id);
        ticketsRepository.deleteById(id);
    }
}
