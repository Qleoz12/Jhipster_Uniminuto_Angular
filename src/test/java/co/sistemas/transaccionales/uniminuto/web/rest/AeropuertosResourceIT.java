package co.sistemas.transaccionales.uniminuto.web.rest;

import co.sistemas.transaccionales.uniminuto.PruebaJhipsterApp;
import co.sistemas.transaccionales.uniminuto.domain.Aeropuertos;
import co.sistemas.transaccionales.uniminuto.repository.AeropuertosRepository;
import co.sistemas.transaccionales.uniminuto.service.AeropuertosService;
import co.sistemas.transaccionales.uniminuto.service.dto.AeropuertosDTO;
import co.sistemas.transaccionales.uniminuto.service.mapper.AeropuertosMapper;
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
import java.util.List;

import static co.sistemas.transaccionales.uniminuto.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AeropuertosResource} REST controller.
 */
@SpringBootTest(classes = PruebaJhipsterApp.class)
public class AeropuertosResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private AeropuertosRepository aeropuertosRepository;

    @Autowired
    private AeropuertosMapper aeropuertosMapper;

    @Autowired
    private AeropuertosService aeropuertosService;

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

    private MockMvc restAeropuertosMockMvc;

    private Aeropuertos aeropuertos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AeropuertosResource aeropuertosResource = new AeropuertosResource(aeropuertosService);
        this.restAeropuertosMockMvc = MockMvcBuilders.standaloneSetup(aeropuertosResource)
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
    public static Aeropuertos createEntity(EntityManager em) {
        Aeropuertos aeropuertos = new Aeropuertos()
            .nombre(DEFAULT_NOMBRE);
        return aeropuertos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aeropuertos createUpdatedEntity(EntityManager em) {
        Aeropuertos aeropuertos = new Aeropuertos()
            .nombre(UPDATED_NOMBRE);
        return aeropuertos;
    }

    @BeforeEach
    public void initTest() {
        aeropuertos = createEntity(em);
    }

    @Test
    @Transactional
    public void createAeropuertos() throws Exception {
        int databaseSizeBeforeCreate = aeropuertosRepository.findAll().size();

        // Create the Aeropuertos
        AeropuertosDTO aeropuertosDTO = aeropuertosMapper.toDto(aeropuertos);
        restAeropuertosMockMvc.perform(post("/api/aeropuertos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aeropuertosDTO)))
            .andExpect(status().isCreated());

        // Validate the Aeropuertos in the database
        List<Aeropuertos> aeropuertosList = aeropuertosRepository.findAll();
        assertThat(aeropuertosList).hasSize(databaseSizeBeforeCreate + 1);
        Aeropuertos testAeropuertos = aeropuertosList.get(aeropuertosList.size() - 1);
        assertThat(testAeropuertos.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createAeropuertosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aeropuertosRepository.findAll().size();

        // Create the Aeropuertos with an existing ID
        aeropuertos.setId(1L);
        AeropuertosDTO aeropuertosDTO = aeropuertosMapper.toDto(aeropuertos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAeropuertosMockMvc.perform(post("/api/aeropuertos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aeropuertosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Aeropuertos in the database
        List<Aeropuertos> aeropuertosList = aeropuertosRepository.findAll();
        assertThat(aeropuertosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAeropuertos() throws Exception {
        // Initialize the database
        aeropuertosRepository.saveAndFlush(aeropuertos);

        // Get all the aeropuertosList
        restAeropuertosMockMvc.perform(get("/api/aeropuertos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aeropuertos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }
    
    @Test
    @Transactional
    public void getAeropuertos() throws Exception {
        // Initialize the database
        aeropuertosRepository.saveAndFlush(aeropuertos);

        // Get the aeropuertos
        restAeropuertosMockMvc.perform(get("/api/aeropuertos/{id}", aeropuertos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aeropuertos.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    public void getNonExistingAeropuertos() throws Exception {
        // Get the aeropuertos
        restAeropuertosMockMvc.perform(get("/api/aeropuertos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAeropuertos() throws Exception {
        // Initialize the database
        aeropuertosRepository.saveAndFlush(aeropuertos);

        int databaseSizeBeforeUpdate = aeropuertosRepository.findAll().size();

        // Update the aeropuertos
        Aeropuertos updatedAeropuertos = aeropuertosRepository.findById(aeropuertos.getId()).get();
        // Disconnect from session so that the updates on updatedAeropuertos are not directly saved in db
        em.detach(updatedAeropuertos);
        updatedAeropuertos
            .nombre(UPDATED_NOMBRE);
        AeropuertosDTO aeropuertosDTO = aeropuertosMapper.toDto(updatedAeropuertos);

        restAeropuertosMockMvc.perform(put("/api/aeropuertos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aeropuertosDTO)))
            .andExpect(status().isOk());

        // Validate the Aeropuertos in the database
        List<Aeropuertos> aeropuertosList = aeropuertosRepository.findAll();
        assertThat(aeropuertosList).hasSize(databaseSizeBeforeUpdate);
        Aeropuertos testAeropuertos = aeropuertosList.get(aeropuertosList.size() - 1);
        assertThat(testAeropuertos.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingAeropuertos() throws Exception {
        int databaseSizeBeforeUpdate = aeropuertosRepository.findAll().size();

        // Create the Aeropuertos
        AeropuertosDTO aeropuertosDTO = aeropuertosMapper.toDto(aeropuertos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAeropuertosMockMvc.perform(put("/api/aeropuertos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aeropuertosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Aeropuertos in the database
        List<Aeropuertos> aeropuertosList = aeropuertosRepository.findAll();
        assertThat(aeropuertosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAeropuertos() throws Exception {
        // Initialize the database
        aeropuertosRepository.saveAndFlush(aeropuertos);

        int databaseSizeBeforeDelete = aeropuertosRepository.findAll().size();

        // Delete the aeropuertos
        restAeropuertosMockMvc.perform(delete("/api/aeropuertos/{id}", aeropuertos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Aeropuertos> aeropuertosList = aeropuertosRepository.findAll();
        assertThat(aeropuertosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aeropuertos.class);
        Aeropuertos aeropuertos1 = new Aeropuertos();
        aeropuertos1.setId(1L);
        Aeropuertos aeropuertos2 = new Aeropuertos();
        aeropuertos2.setId(aeropuertos1.getId());
        assertThat(aeropuertos1).isEqualTo(aeropuertos2);
        aeropuertos2.setId(2L);
        assertThat(aeropuertos1).isNotEqualTo(aeropuertos2);
        aeropuertos1.setId(null);
        assertThat(aeropuertos1).isNotEqualTo(aeropuertos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AeropuertosDTO.class);
        AeropuertosDTO aeropuertosDTO1 = new AeropuertosDTO();
        aeropuertosDTO1.setId(1L);
        AeropuertosDTO aeropuertosDTO2 = new AeropuertosDTO();
        assertThat(aeropuertosDTO1).isNotEqualTo(aeropuertosDTO2);
        aeropuertosDTO2.setId(aeropuertosDTO1.getId());
        assertThat(aeropuertosDTO1).isEqualTo(aeropuertosDTO2);
        aeropuertosDTO2.setId(2L);
        assertThat(aeropuertosDTO1).isNotEqualTo(aeropuertosDTO2);
        aeropuertosDTO1.setId(null);
        assertThat(aeropuertosDTO1).isNotEqualTo(aeropuertosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(aeropuertosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(aeropuertosMapper.fromId(null)).isNull();
    }
}
