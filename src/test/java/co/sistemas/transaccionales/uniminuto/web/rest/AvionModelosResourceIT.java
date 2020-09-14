package co.sistemas.transaccionales.uniminuto.web.rest;

import co.sistemas.transaccionales.uniminuto.PruebaJhipsterApp;
import co.sistemas.transaccionales.uniminuto.domain.AvionModelos;
import co.sistemas.transaccionales.uniminuto.repository.AvionModelosRepository;
import co.sistemas.transaccionales.uniminuto.service.AvionModelosService;
import co.sistemas.transaccionales.uniminuto.service.dto.AvionModelosDTO;
import co.sistemas.transaccionales.uniminuto.service.mapper.AvionModelosMapper;
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
 * Integration tests for the {@link AvionModelosResource} REST controller.
 */
@SpringBootTest(classes = PruebaJhipsterApp.class)
public class AvionModelosResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAPACIDAD = 1;
    private static final Integer UPDATED_CAPACIDAD = 2;

    @Autowired
    private AvionModelosRepository avionModelosRepository;

    @Autowired
    private AvionModelosMapper avionModelosMapper;

    @Autowired
    private AvionModelosService avionModelosService;

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

    private MockMvc restAvionModelosMockMvc;

    private AvionModelos avionModelos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AvionModelosResource avionModelosResource = new AvionModelosResource(avionModelosService);
        this.restAvionModelosMockMvc = MockMvcBuilders.standaloneSetup(avionModelosResource)
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
    public static AvionModelos createEntity(EntityManager em) {
        AvionModelos avionModelos = new AvionModelos()
            .nombre(DEFAULT_NOMBRE)
            .capacidad(DEFAULT_CAPACIDAD);
        return avionModelos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvionModelos createUpdatedEntity(EntityManager em) {
        AvionModelos avionModelos = new AvionModelos()
            .nombre(UPDATED_NOMBRE)
            .capacidad(UPDATED_CAPACIDAD);
        return avionModelos;
    }

    @BeforeEach
    public void initTest() {
        avionModelos = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvionModelos() throws Exception {
        int databaseSizeBeforeCreate = avionModelosRepository.findAll().size();

        // Create the AvionModelos
        AvionModelosDTO avionModelosDTO = avionModelosMapper.toDto(avionModelos);
        restAvionModelosMockMvc.perform(post("/api/avion-modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avionModelosDTO)))
            .andExpect(status().isCreated());

        // Validate the AvionModelos in the database
        List<AvionModelos> avionModelosList = avionModelosRepository.findAll();
        assertThat(avionModelosList).hasSize(databaseSizeBeforeCreate + 1);
        AvionModelos testAvionModelos = avionModelosList.get(avionModelosList.size() - 1);
        assertThat(testAvionModelos.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAvionModelos.getCapacidad()).isEqualTo(DEFAULT_CAPACIDAD);
    }

    @Test
    @Transactional
    public void createAvionModelosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avionModelosRepository.findAll().size();

        // Create the AvionModelos with an existing ID
        avionModelos.setId(1L);
        AvionModelosDTO avionModelosDTO = avionModelosMapper.toDto(avionModelos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvionModelosMockMvc.perform(post("/api/avion-modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avionModelosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AvionModelos in the database
        List<AvionModelos> avionModelosList = avionModelosRepository.findAll();
        assertThat(avionModelosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAvionModelos() throws Exception {
        // Initialize the database
        avionModelosRepository.saveAndFlush(avionModelos);

        // Get all the avionModelosList
        restAvionModelosMockMvc.perform(get("/api/avion-modelos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avionModelos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].capacidad").value(hasItem(DEFAULT_CAPACIDAD)));
    }
    
    @Test
    @Transactional
    public void getAvionModelos() throws Exception {
        // Initialize the database
        avionModelosRepository.saveAndFlush(avionModelos);

        // Get the avionModelos
        restAvionModelosMockMvc.perform(get("/api/avion-modelos/{id}", avionModelos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(avionModelos.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.capacidad").value(DEFAULT_CAPACIDAD));
    }

    @Test
    @Transactional
    public void getNonExistingAvionModelos() throws Exception {
        // Get the avionModelos
        restAvionModelosMockMvc.perform(get("/api/avion-modelos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvionModelos() throws Exception {
        // Initialize the database
        avionModelosRepository.saveAndFlush(avionModelos);

        int databaseSizeBeforeUpdate = avionModelosRepository.findAll().size();

        // Update the avionModelos
        AvionModelos updatedAvionModelos = avionModelosRepository.findById(avionModelos.getId()).get();
        // Disconnect from session so that the updates on updatedAvionModelos are not directly saved in db
        em.detach(updatedAvionModelos);
        updatedAvionModelos
            .nombre(UPDATED_NOMBRE)
            .capacidad(UPDATED_CAPACIDAD);
        AvionModelosDTO avionModelosDTO = avionModelosMapper.toDto(updatedAvionModelos);

        restAvionModelosMockMvc.perform(put("/api/avion-modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avionModelosDTO)))
            .andExpect(status().isOk());

        // Validate the AvionModelos in the database
        List<AvionModelos> avionModelosList = avionModelosRepository.findAll();
        assertThat(avionModelosList).hasSize(databaseSizeBeforeUpdate);
        AvionModelos testAvionModelos = avionModelosList.get(avionModelosList.size() - 1);
        assertThat(testAvionModelos.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAvionModelos.getCapacidad()).isEqualTo(UPDATED_CAPACIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingAvionModelos() throws Exception {
        int databaseSizeBeforeUpdate = avionModelosRepository.findAll().size();

        // Create the AvionModelos
        AvionModelosDTO avionModelosDTO = avionModelosMapper.toDto(avionModelos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvionModelosMockMvc.perform(put("/api/avion-modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avionModelosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AvionModelos in the database
        List<AvionModelos> avionModelosList = avionModelosRepository.findAll();
        assertThat(avionModelosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAvionModelos() throws Exception {
        // Initialize the database
        avionModelosRepository.saveAndFlush(avionModelos);

        int databaseSizeBeforeDelete = avionModelosRepository.findAll().size();

        // Delete the avionModelos
        restAvionModelosMockMvc.perform(delete("/api/avion-modelos/{id}", avionModelos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AvionModelos> avionModelosList = avionModelosRepository.findAll();
        assertThat(avionModelosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvionModelos.class);
        AvionModelos avionModelos1 = new AvionModelos();
        avionModelos1.setId(1L);
        AvionModelos avionModelos2 = new AvionModelos();
        avionModelos2.setId(avionModelos1.getId());
        assertThat(avionModelos1).isEqualTo(avionModelos2);
        avionModelos2.setId(2L);
        assertThat(avionModelos1).isNotEqualTo(avionModelos2);
        avionModelos1.setId(null);
        assertThat(avionModelos1).isNotEqualTo(avionModelos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvionModelosDTO.class);
        AvionModelosDTO avionModelosDTO1 = new AvionModelosDTO();
        avionModelosDTO1.setId(1L);
        AvionModelosDTO avionModelosDTO2 = new AvionModelosDTO();
        assertThat(avionModelosDTO1).isNotEqualTo(avionModelosDTO2);
        avionModelosDTO2.setId(avionModelosDTO1.getId());
        assertThat(avionModelosDTO1).isEqualTo(avionModelosDTO2);
        avionModelosDTO2.setId(2L);
        assertThat(avionModelosDTO1).isNotEqualTo(avionModelosDTO2);
        avionModelosDTO1.setId(null);
        assertThat(avionModelosDTO1).isNotEqualTo(avionModelosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(avionModelosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(avionModelosMapper.fromId(null)).isNull();
    }
}
