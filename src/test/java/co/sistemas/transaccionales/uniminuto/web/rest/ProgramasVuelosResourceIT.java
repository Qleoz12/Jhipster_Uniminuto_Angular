package co.sistemas.transaccionales.uniminuto.web.rest;

import co.sistemas.transaccionales.uniminuto.PruebaJhipsterApp;
import co.sistemas.transaccionales.uniminuto.domain.ProgramasVuelos;
import co.sistemas.transaccionales.uniminuto.repository.ProgramasVuelosRepository;
import co.sistemas.transaccionales.uniminuto.service.ProgramasVuelosService;
import co.sistemas.transaccionales.uniminuto.service.dto.ProgramasVuelosDTO;
import co.sistemas.transaccionales.uniminuto.service.mapper.ProgramasVuelosMapper;
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
 * Integration tests for the {@link ProgramasVuelosResource} REST controller.
 */
@SpringBootTest(classes = PruebaJhipsterApp.class)
public class ProgramasVuelosResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTAL_DIAS = 1;
    private static final Integer UPDATED_TOTAL_DIAS = 2;

    @Autowired
    private ProgramasVuelosRepository programasVuelosRepository;

    @Autowired
    private ProgramasVuelosMapper programasVuelosMapper;

    @Autowired
    private ProgramasVuelosService programasVuelosService;

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

    private MockMvc restProgramasVuelosMockMvc;

    private ProgramasVuelos programasVuelos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProgramasVuelosResource programasVuelosResource = new ProgramasVuelosResource(programasVuelosService);
        this.restProgramasVuelosMockMvc = MockMvcBuilders.standaloneSetup(programasVuelosResource)
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
    public static ProgramasVuelos createEntity(EntityManager em) {
        ProgramasVuelos programasVuelos = new ProgramasVuelos()
            .nombre(DEFAULT_NOMBRE)
            .totalDias(DEFAULT_TOTAL_DIAS);
        return programasVuelos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProgramasVuelos createUpdatedEntity(EntityManager em) {
        ProgramasVuelos programasVuelos = new ProgramasVuelos()
            .nombre(UPDATED_NOMBRE)
            .totalDias(UPDATED_TOTAL_DIAS);
        return programasVuelos;
    }

    @BeforeEach
    public void initTest() {
        programasVuelos = createEntity(em);
    }

    @Test
    @Transactional
    public void createProgramasVuelos() throws Exception {
        int databaseSizeBeforeCreate = programasVuelosRepository.findAll().size();

        // Create the ProgramasVuelos
        ProgramasVuelosDTO programasVuelosDTO = programasVuelosMapper.toDto(programasVuelos);
        restProgramasVuelosMockMvc.perform(post("/api/programas-vuelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programasVuelosDTO)))
            .andExpect(status().isCreated());

        // Validate the ProgramasVuelos in the database
        List<ProgramasVuelos> programasVuelosList = programasVuelosRepository.findAll();
        assertThat(programasVuelosList).hasSize(databaseSizeBeforeCreate + 1);
        ProgramasVuelos testProgramasVuelos = programasVuelosList.get(programasVuelosList.size() - 1);
        assertThat(testProgramasVuelos.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testProgramasVuelos.getTotalDias()).isEqualTo(DEFAULT_TOTAL_DIAS);
    }

    @Test
    @Transactional
    public void createProgramasVuelosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = programasVuelosRepository.findAll().size();

        // Create the ProgramasVuelos with an existing ID
        programasVuelos.setId(1L);
        ProgramasVuelosDTO programasVuelosDTO = programasVuelosMapper.toDto(programasVuelos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramasVuelosMockMvc.perform(post("/api/programas-vuelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programasVuelosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProgramasVuelos in the database
        List<ProgramasVuelos> programasVuelosList = programasVuelosRepository.findAll();
        assertThat(programasVuelosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProgramasVuelos() throws Exception {
        // Initialize the database
        programasVuelosRepository.saveAndFlush(programasVuelos);

        // Get all the programasVuelosList
        restProgramasVuelosMockMvc.perform(get("/api/programas-vuelos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programasVuelos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].totalDias").value(hasItem(DEFAULT_TOTAL_DIAS)));
    }
    
    @Test
    @Transactional
    public void getProgramasVuelos() throws Exception {
        // Initialize the database
        programasVuelosRepository.saveAndFlush(programasVuelos);

        // Get the programasVuelos
        restProgramasVuelosMockMvc.perform(get("/api/programas-vuelos/{id}", programasVuelos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(programasVuelos.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.totalDias").value(DEFAULT_TOTAL_DIAS));
    }

    @Test
    @Transactional
    public void getNonExistingProgramasVuelos() throws Exception {
        // Get the programasVuelos
        restProgramasVuelosMockMvc.perform(get("/api/programas-vuelos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProgramasVuelos() throws Exception {
        // Initialize the database
        programasVuelosRepository.saveAndFlush(programasVuelos);

        int databaseSizeBeforeUpdate = programasVuelosRepository.findAll().size();

        // Update the programasVuelos
        ProgramasVuelos updatedProgramasVuelos = programasVuelosRepository.findById(programasVuelos.getId()).get();
        // Disconnect from session so that the updates on updatedProgramasVuelos are not directly saved in db
        em.detach(updatedProgramasVuelos);
        updatedProgramasVuelos
            .nombre(UPDATED_NOMBRE)
            .totalDias(UPDATED_TOTAL_DIAS);
        ProgramasVuelosDTO programasVuelosDTO = programasVuelosMapper.toDto(updatedProgramasVuelos);

        restProgramasVuelosMockMvc.perform(put("/api/programas-vuelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programasVuelosDTO)))
            .andExpect(status().isOk());

        // Validate the ProgramasVuelos in the database
        List<ProgramasVuelos> programasVuelosList = programasVuelosRepository.findAll();
        assertThat(programasVuelosList).hasSize(databaseSizeBeforeUpdate);
        ProgramasVuelos testProgramasVuelos = programasVuelosList.get(programasVuelosList.size() - 1);
        assertThat(testProgramasVuelos.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProgramasVuelos.getTotalDias()).isEqualTo(UPDATED_TOTAL_DIAS);
    }

    @Test
    @Transactional
    public void updateNonExistingProgramasVuelos() throws Exception {
        int databaseSizeBeforeUpdate = programasVuelosRepository.findAll().size();

        // Create the ProgramasVuelos
        ProgramasVuelosDTO programasVuelosDTO = programasVuelosMapper.toDto(programasVuelos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramasVuelosMockMvc.perform(put("/api/programas-vuelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programasVuelosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProgramasVuelos in the database
        List<ProgramasVuelos> programasVuelosList = programasVuelosRepository.findAll();
        assertThat(programasVuelosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProgramasVuelos() throws Exception {
        // Initialize the database
        programasVuelosRepository.saveAndFlush(programasVuelos);

        int databaseSizeBeforeDelete = programasVuelosRepository.findAll().size();

        // Delete the programasVuelos
        restProgramasVuelosMockMvc.perform(delete("/api/programas-vuelos/{id}", programasVuelos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProgramasVuelos> programasVuelosList = programasVuelosRepository.findAll();
        assertThat(programasVuelosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgramasVuelos.class);
        ProgramasVuelos programasVuelos1 = new ProgramasVuelos();
        programasVuelos1.setId(1L);
        ProgramasVuelos programasVuelos2 = new ProgramasVuelos();
        programasVuelos2.setId(programasVuelos1.getId());
        assertThat(programasVuelos1).isEqualTo(programasVuelos2);
        programasVuelos2.setId(2L);
        assertThat(programasVuelos1).isNotEqualTo(programasVuelos2);
        programasVuelos1.setId(null);
        assertThat(programasVuelos1).isNotEqualTo(programasVuelos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgramasVuelosDTO.class);
        ProgramasVuelosDTO programasVuelosDTO1 = new ProgramasVuelosDTO();
        programasVuelosDTO1.setId(1L);
        ProgramasVuelosDTO programasVuelosDTO2 = new ProgramasVuelosDTO();
        assertThat(programasVuelosDTO1).isNotEqualTo(programasVuelosDTO2);
        programasVuelosDTO2.setId(programasVuelosDTO1.getId());
        assertThat(programasVuelosDTO1).isEqualTo(programasVuelosDTO2);
        programasVuelosDTO2.setId(2L);
        assertThat(programasVuelosDTO1).isNotEqualTo(programasVuelosDTO2);
        programasVuelosDTO1.setId(null);
        assertThat(programasVuelosDTO1).isNotEqualTo(programasVuelosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(programasVuelosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(programasVuelosMapper.fromId(null)).isNull();
    }
}
