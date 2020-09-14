package co.sistemas.transaccionales.uniminuto.web.rest;

import co.sistemas.transaccionales.uniminuto.PruebaJhipsterApp;
import co.sistemas.transaccionales.uniminuto.domain.Ciudades;
import co.sistemas.transaccionales.uniminuto.repository.CiudadesRepository;
import co.sistemas.transaccionales.uniminuto.service.CiudadesService;
import co.sistemas.transaccionales.uniminuto.service.dto.CiudadesDTO;
import co.sistemas.transaccionales.uniminuto.service.mapper.CiudadesMapper;
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
 * Integration tests for the {@link CiudadesResource} REST controller.
 */
@SpringBootTest(classes = PruebaJhipsterApp.class)
public class CiudadesResourceIT {

    private static final String DEFAULT_CIUDAD = "AAAAAAAAAA";
    private static final String UPDATED_CIUDAD = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    @Autowired
    private CiudadesRepository ciudadesRepository;

    @Autowired
    private CiudadesMapper ciudadesMapper;

    @Autowired
    private CiudadesService ciudadesService;

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

    private MockMvc restCiudadesMockMvc;

    private Ciudades ciudades;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CiudadesResource ciudadesResource = new CiudadesResource(ciudadesService);
        this.restCiudadesMockMvc = MockMvcBuilders.standaloneSetup(ciudadesResource)
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
    public static Ciudades createEntity(EntityManager em) {
        Ciudades ciudades = new Ciudades()
            .ciudad(DEFAULT_CIUDAD)
            .pais(DEFAULT_PAIS);
        return ciudades;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ciudades createUpdatedEntity(EntityManager em) {
        Ciudades ciudades = new Ciudades()
            .ciudad(UPDATED_CIUDAD)
            .pais(UPDATED_PAIS);
        return ciudades;
    }

    @BeforeEach
    public void initTest() {
        ciudades = createEntity(em);
    }

    @Test
    @Transactional
    public void createCiudades() throws Exception {
        int databaseSizeBeforeCreate = ciudadesRepository.findAll().size();

        // Create the Ciudades
        CiudadesDTO ciudadesDTO = ciudadesMapper.toDto(ciudades);
        restCiudadesMockMvc.perform(post("/api/ciudades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciudadesDTO)))
            .andExpect(status().isCreated());

        // Validate the Ciudades in the database
        List<Ciudades> ciudadesList = ciudadesRepository.findAll();
        assertThat(ciudadesList).hasSize(databaseSizeBeforeCreate + 1);
        Ciudades testCiudades = ciudadesList.get(ciudadesList.size() - 1);
        assertThat(testCiudades.getCiudad()).isEqualTo(DEFAULT_CIUDAD);
        assertThat(testCiudades.getPais()).isEqualTo(DEFAULT_PAIS);
    }

    @Test
    @Transactional
    public void createCiudadesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ciudadesRepository.findAll().size();

        // Create the Ciudades with an existing ID
        ciudades.setId(1L);
        CiudadesDTO ciudadesDTO = ciudadesMapper.toDto(ciudades);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCiudadesMockMvc.perform(post("/api/ciudades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciudadesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ciudades in the database
        List<Ciudades> ciudadesList = ciudadesRepository.findAll();
        assertThat(ciudadesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCiudades() throws Exception {
        // Initialize the database
        ciudadesRepository.saveAndFlush(ciudades);

        // Get all the ciudadesList
        restCiudadesMockMvc.perform(get("/api/ciudades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ciudades.getId().intValue())))
            .andExpect(jsonPath("$.[*].ciudad").value(hasItem(DEFAULT_CIUDAD)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)));
    }
    
    @Test
    @Transactional
    public void getCiudades() throws Exception {
        // Initialize the database
        ciudadesRepository.saveAndFlush(ciudades);

        // Get the ciudades
        restCiudadesMockMvc.perform(get("/api/ciudades/{id}", ciudades.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ciudades.getId().intValue()))
            .andExpect(jsonPath("$.ciudad").value(DEFAULT_CIUDAD))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS));
    }

    @Test
    @Transactional
    public void getNonExistingCiudades() throws Exception {
        // Get the ciudades
        restCiudadesMockMvc.perform(get("/api/ciudades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCiudades() throws Exception {
        // Initialize the database
        ciudadesRepository.saveAndFlush(ciudades);

        int databaseSizeBeforeUpdate = ciudadesRepository.findAll().size();

        // Update the ciudades
        Ciudades updatedCiudades = ciudadesRepository.findById(ciudades.getId()).get();
        // Disconnect from session so that the updates on updatedCiudades are not directly saved in db
        em.detach(updatedCiudades);
        updatedCiudades
            .ciudad(UPDATED_CIUDAD)
            .pais(UPDATED_PAIS);
        CiudadesDTO ciudadesDTO = ciudadesMapper.toDto(updatedCiudades);

        restCiudadesMockMvc.perform(put("/api/ciudades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciudadesDTO)))
            .andExpect(status().isOk());

        // Validate the Ciudades in the database
        List<Ciudades> ciudadesList = ciudadesRepository.findAll();
        assertThat(ciudadesList).hasSize(databaseSizeBeforeUpdate);
        Ciudades testCiudades = ciudadesList.get(ciudadesList.size() - 1);
        assertThat(testCiudades.getCiudad()).isEqualTo(UPDATED_CIUDAD);
        assertThat(testCiudades.getPais()).isEqualTo(UPDATED_PAIS);
    }

    @Test
    @Transactional
    public void updateNonExistingCiudades() throws Exception {
        int databaseSizeBeforeUpdate = ciudadesRepository.findAll().size();

        // Create the Ciudades
        CiudadesDTO ciudadesDTO = ciudadesMapper.toDto(ciudades);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCiudadesMockMvc.perform(put("/api/ciudades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciudadesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ciudades in the database
        List<Ciudades> ciudadesList = ciudadesRepository.findAll();
        assertThat(ciudadesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCiudades() throws Exception {
        // Initialize the database
        ciudadesRepository.saveAndFlush(ciudades);

        int databaseSizeBeforeDelete = ciudadesRepository.findAll().size();

        // Delete the ciudades
        restCiudadesMockMvc.perform(delete("/api/ciudades/{id}", ciudades.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ciudades> ciudadesList = ciudadesRepository.findAll();
        assertThat(ciudadesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ciudades.class);
        Ciudades ciudades1 = new Ciudades();
        ciudades1.setId(1L);
        Ciudades ciudades2 = new Ciudades();
        ciudades2.setId(ciudades1.getId());
        assertThat(ciudades1).isEqualTo(ciudades2);
        ciudades2.setId(2L);
        assertThat(ciudades1).isNotEqualTo(ciudades2);
        ciudades1.setId(null);
        assertThat(ciudades1).isNotEqualTo(ciudades2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CiudadesDTO.class);
        CiudadesDTO ciudadesDTO1 = new CiudadesDTO();
        ciudadesDTO1.setId(1L);
        CiudadesDTO ciudadesDTO2 = new CiudadesDTO();
        assertThat(ciudadesDTO1).isNotEqualTo(ciudadesDTO2);
        ciudadesDTO2.setId(ciudadesDTO1.getId());
        assertThat(ciudadesDTO1).isEqualTo(ciudadesDTO2);
        ciudadesDTO2.setId(2L);
        assertThat(ciudadesDTO1).isNotEqualTo(ciudadesDTO2);
        ciudadesDTO1.setId(null);
        assertThat(ciudadesDTO1).isNotEqualTo(ciudadesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ciudadesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ciudadesMapper.fromId(null)).isNull();
    }
}
