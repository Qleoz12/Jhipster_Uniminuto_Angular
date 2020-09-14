package co.sistemas.transaccionales.uniminuto.web.rest;

import co.sistemas.transaccionales.uniminuto.PruebaJhipsterApp;
import co.sistemas.transaccionales.uniminuto.domain.Tickets;
import co.sistemas.transaccionales.uniminuto.repository.TicketsRepository;
import co.sistemas.transaccionales.uniminuto.service.TicketsService;
import co.sistemas.transaccionales.uniminuto.service.dto.TicketsDTO;
import co.sistemas.transaccionales.uniminuto.service.mapper.TicketsMapper;
import co.sistemas.transaccionales.uniminuto.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static co.sistemas.transaccionales.uniminuto.web.rest.TestUtil.sameInstant;
import static co.sistemas.transaccionales.uniminuto.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TicketsResource} REST controller.
 */
@SpringBootTest(classes = PruebaJhipsterApp.class)
public class TicketsResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_DESCUENTO = 1;
    private static final Integer UPDATED_DESCUENTO = 2;

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final ZonedDateTime DEFAULT_FECHA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TicketsRepository ticketsRepository;

    @Autowired
    private TicketsMapper ticketsMapper;

    @Autowired
    private TicketsService ticketsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTicketsMockMvc;

    private Tickets tickets;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TicketsResource ticketsResource = new TicketsResource(ticketsService);
        this.restTicketsMockMvc = MockMvcBuilders.standaloneSetup(ticketsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tickets createEntity(EntityManager em) {
        Tickets tickets = new Tickets()
            .nombre(DEFAULT_NOMBRE)
            .valor(DEFAULT_VALOR)
            .descuento(DEFAULT_DESCUENTO)
            .estado(DEFAULT_ESTADO)
            .fecha(DEFAULT_FECHA);
        return tickets;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tickets createUpdatedEntity(EntityManager em) {
        Tickets tickets = new Tickets()
            .nombre(UPDATED_NOMBRE)
            .valor(UPDATED_VALOR)
            .descuento(UPDATED_DESCUENTO)
            .estado(UPDATED_ESTADO)
            .fecha(UPDATED_FECHA);
        return tickets;
    }

    @BeforeEach
    public void initTest() {
        tickets = createEntity(em);
    }

    @Test
    @Transactional
    public void createTickets() throws Exception {
        int databaseSizeBeforeCreate = ticketsRepository.findAll().size();

        // Create the Tickets
        TicketsDTO ticketsDTO = ticketsMapper.toDto(tickets);
        restTicketsMockMvc.perform(post("/api/tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketsDTO)))
            .andExpect(status().isCreated());

        // Validate the Tickets in the database
        List<Tickets> ticketsList = ticketsRepository.findAll();
        assertThat(ticketsList).hasSize(databaseSizeBeforeCreate + 1);
        Tickets testTickets = ticketsList.get(ticketsList.size() - 1);
        assertThat(testTickets.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTickets.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testTickets.getDescuento()).isEqualTo(DEFAULT_DESCUENTO);
        assertThat(testTickets.isEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testTickets.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createTicketsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ticketsRepository.findAll().size();

        // Create the Tickets with an existing ID
        tickets.setId(1L);
        TicketsDTO ticketsDTO = ticketsMapper.toDto(tickets);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketsMockMvc.perform(post("/api/tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tickets in the database
        List<Tickets> ticketsList = ticketsRepository.findAll();
        assertThat(ticketsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTickets() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList
        restTicketsMockMvc.perform(get("/api/tickets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tickets.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].descuento").value(hasItem(DEFAULT_DESCUENTO)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(sameInstant(DEFAULT_FECHA))));
    }
    
    @Test
    @Transactional
    public void getTickets() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get the tickets
        restTicketsMockMvc.perform(get("/api/tickets/{id}", tickets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tickets.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR))
            .andExpect(jsonPath("$.descuento").value(DEFAULT_DESCUENTO))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.fecha").value(sameInstant(DEFAULT_FECHA)));
    }

    @Test
    @Transactional
    public void getNonExistingTickets() throws Exception {
        // Get the tickets
        restTicketsMockMvc.perform(get("/api/tickets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTickets() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        int databaseSizeBeforeUpdate = ticketsRepository.findAll().size();

        // Update the tickets
        Tickets updatedTickets = ticketsRepository.findById(tickets.getId()).get();
        // Disconnect from session so that the updates on updatedTickets are not directly saved in db
        em.detach(updatedTickets);
        updatedTickets
            .nombre(UPDATED_NOMBRE)
            .valor(UPDATED_VALOR)
            .descuento(UPDATED_DESCUENTO)
            .estado(UPDATED_ESTADO)
            .fecha(UPDATED_FECHA);
        TicketsDTO ticketsDTO = ticketsMapper.toDto(updatedTickets);

        restTicketsMockMvc.perform(put("/api/tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketsDTO)))
            .andExpect(status().isOk());

        // Validate the Tickets in the database
        List<Tickets> ticketsList = ticketsRepository.findAll();
        assertThat(ticketsList).hasSize(databaseSizeBeforeUpdate);
        Tickets testTickets = ticketsList.get(ticketsList.size() - 1);
        assertThat(testTickets.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTickets.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testTickets.getDescuento()).isEqualTo(UPDATED_DESCUENTO);
        assertThat(testTickets.isEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testTickets.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingTickets() throws Exception {
        int databaseSizeBeforeUpdate = ticketsRepository.findAll().size();

        // Create the Tickets
        TicketsDTO ticketsDTO = ticketsMapper.toDto(tickets);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketsMockMvc.perform(put("/api/tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tickets in the database
        List<Tickets> ticketsList = ticketsRepository.findAll();
        assertThat(ticketsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTickets() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        int databaseSizeBeforeDelete = ticketsRepository.findAll().size();

        // Delete the tickets
        restTicketsMockMvc.perform(delete("/api/tickets/{id}", tickets.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tickets> ticketsList = ticketsRepository.findAll();
        assertThat(ticketsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tickets.class);
        Tickets tickets1 = new Tickets();
        tickets1.setId(1L);
        Tickets tickets2 = new Tickets();
        tickets2.setId(tickets1.getId());
        assertThat(tickets1).isEqualTo(tickets2);
        tickets2.setId(2L);
        assertThat(tickets1).isNotEqualTo(tickets2);
        tickets1.setId(null);
        assertThat(tickets1).isNotEqualTo(tickets2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketsDTO.class);
        TicketsDTO ticketsDTO1 = new TicketsDTO();
        ticketsDTO1.setId(1L);
        TicketsDTO ticketsDTO2 = new TicketsDTO();
        assertThat(ticketsDTO1).isNotEqualTo(ticketsDTO2);
        ticketsDTO2.setId(ticketsDTO1.getId());
        assertThat(ticketsDTO1).isEqualTo(ticketsDTO2);
        ticketsDTO2.setId(2L);
        assertThat(ticketsDTO1).isNotEqualTo(ticketsDTO2);
        ticketsDTO1.setId(null);
        assertThat(ticketsDTO1).isNotEqualTo(ticketsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ticketsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ticketsMapper.fromId(null)).isNull();
    }
}
