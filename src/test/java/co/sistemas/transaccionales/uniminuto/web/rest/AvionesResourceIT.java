package co.sistemas.transaccionales.uniminuto.web.rest;

import co.sistemas.transaccionales.uniminuto.PruebaJhipsterApp;
import co.sistemas.transaccionales.uniminuto.domain.Aviones;
import co.sistemas.transaccionales.uniminuto.repository.AvionesRepository;
import co.sistemas.transaccionales.uniminuto.service.AvionesService;
import co.sistemas.transaccionales.uniminuto.service.dto.AvionesDTO;
import co.sistemas.transaccionales.uniminuto.service.mapper.AvionesMapper;
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
 * Integration tests for the {@link AvionesResource} REST controller.
 */
@SpringBootTest(classes = PruebaJhipsterApp.class)
public class AvionesResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private AvionesRepository avionesRepository;

    @Autowired
    private AvionesMapper avionesMapper;

    @Autowired
    private AvionesService avionesService;

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

    private MockMvc restAvionesMockMvc;

    private Aviones aviones;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AvionesResource avionesResource = new AvionesResource(avionesService);
        this.restAvionesMockMvc = MockMvcBuilders.standaloneSetup(avionesResource)
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
    public static Aviones createEntity(EntityManager em) {
        Aviones aviones = new Aviones()
            .nombre(DEFAULT_NOMBRE);
        return aviones;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aviones createUpdatedEntity(EntityManager em) {
        Aviones aviones = new Aviones()
            .nombre(UPDATED_NOMBRE);
        return aviones;
    }

    @BeforeEach
    public void initTest() {
        aviones = createEntity(em);
    }

    @Test
    @Transactional
    public void createAviones() throws Exception {
        int databaseSizeBeforeCreate = avionesRepository.findAll().size();

        // Create the Aviones
        AvionesDTO avionesDTO = avionesMapper.toDto(aviones);
        restAvionesMockMvc.perform(post("/api/aviones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avionesDTO)))
            .andExpect(status().isCreated());

        // Validate the Aviones in the database
        List<Aviones> avionesList = avionesRepository.findAll();
        assertThat(avionesList).hasSize(databaseSizeBeforeCreate + 1);
        Aviones testAviones = avionesList.get(avionesList.size() - 1);
        assertThat(testAviones.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createAvionesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avionesRepository.findAll().size();

        // Create the Aviones with an existing ID
        aviones.setId(1L);
        AvionesDTO avionesDTO = avionesMapper.toDto(aviones);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvionesMockMvc.perform(post("/api/aviones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avionesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Aviones in the database
        List<Aviones> avionesList = avionesRepository.findAll();
        assertThat(avionesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAviones() throws Exception {
        // Initialize the database
        avionesRepository.saveAndFlush(aviones);

        // Get all the avionesList
        restAvionesMockMvc.perform(get("/api/aviones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aviones.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }
    
    @Test
    @Transactional
    public void getAviones() throws Exception {
        // Initialize the database
        avionesRepository.saveAndFlush(aviones);

        // Get the aviones
        restAvionesMockMvc.perform(get("/api/aviones/{id}", aviones.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aviones.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    public void getNonExistingAviones() throws Exception {
        // Get the aviones
        restAvionesMockMvc.perform(get("/api/aviones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAviones() throws Exception {
        // Initialize the database
        avionesRepository.saveAndFlush(aviones);

        int databaseSizeBeforeUpdate = avionesRepository.findAll().size();

        // Update the aviones
        Aviones updatedAviones = avionesRepository.findById(aviones.getId()).get();
        // Disconnect from session so that the updates on updatedAviones are not directly saved in db
        em.detach(updatedAviones);
        updatedAviones
            .nombre(UPDATED_NOMBRE);
        AvionesDTO avionesDTO = avionesMapper.toDto(updatedAviones);

        restAvionesMockMvc.perform(put("/api/aviones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avionesDTO)))
            .andExpect(status().isOk());

        // Validate the Aviones in the database
        List<Aviones> avionesList = avionesRepository.findAll();
        assertThat(avionesList).hasSize(databaseSizeBeforeUpdate);
        Aviones testAviones = avionesList.get(avionesList.size() - 1);
        assertThat(testAviones.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingAviones() throws Exception {
        int databaseSizeBeforeUpdate = avionesRepository.findAll().size();

        // Create the Aviones
        AvionesDTO avionesDTO = avionesMapper.toDto(aviones);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvionesMockMvc.perform(put("/api/aviones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avionesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Aviones in the database
        List<Aviones> avionesList = avionesRepository.findAll();
        assertThat(avionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAviones() throws Exception {
        // Initialize the database
        avionesRepository.saveAndFlush(aviones);

        int databaseSizeBeforeDelete = avionesRepository.findAll().size();

        // Delete the aviones
        restAvionesMockMvc.perform(delete("/api/aviones/{id}", aviones.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Aviones> avionesList = avionesRepository.findAll();
        assertThat(avionesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aviones.class);
        Aviones aviones1 = new Aviones();
        aviones1.setId(1L);
        Aviones aviones2 = new Aviones();
        aviones2.setId(aviones1.getId());
        assertThat(aviones1).isEqualTo(aviones2);
        aviones2.setId(2L);
        assertThat(aviones1).isNotEqualTo(aviones2);
        aviones1.setId(null);
        assertThat(aviones1).isNotEqualTo(aviones2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvionesDTO.class);
        AvionesDTO avionesDTO1 = new AvionesDTO();
        avionesDTO1.setId(1L);
        AvionesDTO avionesDTO2 = new AvionesDTO();
        assertThat(avionesDTO1).isNotEqualTo(avionesDTO2);
        avionesDTO2.setId(avionesDTO1.getId());
        assertThat(avionesDTO1).isEqualTo(avionesDTO2);
        avionesDTO2.setId(2L);
        assertThat(avionesDTO1).isNotEqualTo(avionesDTO2);
        avionesDTO1.setId(null);
        assertThat(avionesDTO1).isNotEqualTo(avionesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(avionesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(avionesMapper.fromId(null)).isNull();
    }
}
