package co.sistemas.transaccionales.uniminuto.web.rest;

import co.sistemas.transaccionales.uniminuto.PruebaJhipsterApp;
import co.sistemas.transaccionales.uniminuto.domain.Vuelos;
import co.sistemas.transaccionales.uniminuto.repository.VuelosRepository;
import co.sistemas.transaccionales.uniminuto.service.VuelosService;
import co.sistemas.transaccionales.uniminuto.service.dto.VuelosDTO;
import co.sistemas.transaccionales.uniminuto.service.mapper.VuelosMapper;
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
 * Integration tests for the {@link VuelosResource} REST controller.
 */
@SpringBootTest(classes = PruebaJhipsterApp.class)
public class VuelosResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DIAS_SEMANA = "AAAAAAAAAA";
    private static final String UPDATED_DIAS_SEMANA = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FECHA_SALIDA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_SALIDA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_ARRIBO_9 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_ARRIBO_9 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_PLAZAS_VACIAS = 1;
    private static final Integer UPDATED_PLAZAS_VACIAS = 2;

    private static final Integer DEFAULT_ORDEN = 1;
    private static final Integer UPDATED_ORDEN = 2;

    private static final Integer DEFAULT_ESTADO_VUELO = 1;
    private static final Integer UPDATED_ESTADO_VUELO = 2;

    private static final Boolean DEFAULT_ES_ESCALA = false;
    private static final Boolean UPDATED_ES_ESCALA = true;

    @Autowired
    private VuelosRepository vuelosRepository;

    @Autowired
    private VuelosMapper vuelosMapper;

    @Autowired
    private VuelosService vuelosService;

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

    private MockMvc restVuelosMockMvc;

    private Vuelos vuelos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VuelosResource vuelosResource = new VuelosResource(vuelosService);
        this.restVuelosMockMvc = MockMvcBuilders.standaloneSetup(vuelosResource)
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
    public static Vuelos createEntity(EntityManager em) {
        Vuelos vuelos = new Vuelos()
            .nombre(DEFAULT_NOMBRE)
            .diasSemana(DEFAULT_DIAS_SEMANA)
            .fechaSalida(DEFAULT_FECHA_SALIDA)
            .fechaArribo9(DEFAULT_FECHA_ARRIBO_9)
            .plazasVacias(DEFAULT_PLAZAS_VACIAS)
            .orden(DEFAULT_ORDEN)
            .estadoVuelo(DEFAULT_ESTADO_VUELO)
            .esEscala(DEFAULT_ES_ESCALA);
        return vuelos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vuelos createUpdatedEntity(EntityManager em) {
        Vuelos vuelos = new Vuelos()
            .nombre(UPDATED_NOMBRE)
            .diasSemana(UPDATED_DIAS_SEMANA)
            .fechaSalida(UPDATED_FECHA_SALIDA)
            .fechaArribo9(UPDATED_FECHA_ARRIBO_9)
            .plazasVacias(UPDATED_PLAZAS_VACIAS)
            .orden(UPDATED_ORDEN)
            .estadoVuelo(UPDATED_ESTADO_VUELO)
            .esEscala(UPDATED_ES_ESCALA);
        return vuelos;
    }

    @BeforeEach
    public void initTest() {
        vuelos = createEntity(em);
    }

    @Test
    @Transactional
    public void createVuelos() throws Exception {
        int databaseSizeBeforeCreate = vuelosRepository.findAll().size();

        // Create the Vuelos
        VuelosDTO vuelosDTO = vuelosMapper.toDto(vuelos);
        restVuelosMockMvc.perform(post("/api/vuelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vuelosDTO)))
            .andExpect(status().isCreated());

        // Validate the Vuelos in the database
        List<Vuelos> vuelosList = vuelosRepository.findAll();
        assertThat(vuelosList).hasSize(databaseSizeBeforeCreate + 1);
        Vuelos testVuelos = vuelosList.get(vuelosList.size() - 1);
        assertThat(testVuelos.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testVuelos.getDiasSemana()).isEqualTo(DEFAULT_DIAS_SEMANA);
        assertThat(testVuelos.getFechaSalida()).isEqualTo(DEFAULT_FECHA_SALIDA);
        assertThat(testVuelos.getFechaArribo9()).isEqualTo(DEFAULT_FECHA_ARRIBO_9);
        assertThat(testVuelos.getPlazasVacias()).isEqualTo(DEFAULT_PLAZAS_VACIAS);
        assertThat(testVuelos.getOrden()).isEqualTo(DEFAULT_ORDEN);
        assertThat(testVuelos.getEstadoVuelo()).isEqualTo(DEFAULT_ESTADO_VUELO);
        assertThat(testVuelos.isEsEscala()).isEqualTo(DEFAULT_ES_ESCALA);
    }

    @Test
    @Transactional
    public void createVuelosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vuelosRepository.findAll().size();

        // Create the Vuelos with an existing ID
        vuelos.setId(1L);
        VuelosDTO vuelosDTO = vuelosMapper.toDto(vuelos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVuelosMockMvc.perform(post("/api/vuelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vuelosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vuelos in the database
        List<Vuelos> vuelosList = vuelosRepository.findAll();
        assertThat(vuelosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVuelos() throws Exception {
        // Initialize the database
        vuelosRepository.saveAndFlush(vuelos);

        // Get all the vuelosList
        restVuelosMockMvc.perform(get("/api/vuelos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vuelos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].diasSemana").value(hasItem(DEFAULT_DIAS_SEMANA)))
            .andExpect(jsonPath("$.[*].fechaSalida").value(hasItem(sameInstant(DEFAULT_FECHA_SALIDA))))
            .andExpect(jsonPath("$.[*].fechaArribo9").value(hasItem(sameInstant(DEFAULT_FECHA_ARRIBO_9))))
            .andExpect(jsonPath("$.[*].plazasVacias").value(hasItem(DEFAULT_PLAZAS_VACIAS)))
            .andExpect(jsonPath("$.[*].orden").value(hasItem(DEFAULT_ORDEN)))
            .andExpect(jsonPath("$.[*].estadoVuelo").value(hasItem(DEFAULT_ESTADO_VUELO)))
            .andExpect(jsonPath("$.[*].esEscala").value(hasItem(DEFAULT_ES_ESCALA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getVuelos() throws Exception {
        // Initialize the database
        vuelosRepository.saveAndFlush(vuelos);

        // Get the vuelos
        restVuelosMockMvc.perform(get("/api/vuelos/{id}", vuelos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vuelos.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.diasSemana").value(DEFAULT_DIAS_SEMANA))
            .andExpect(jsonPath("$.fechaSalida").value(sameInstant(DEFAULT_FECHA_SALIDA)))
            .andExpect(jsonPath("$.fechaArribo9").value(sameInstant(DEFAULT_FECHA_ARRIBO_9)))
            .andExpect(jsonPath("$.plazasVacias").value(DEFAULT_PLAZAS_VACIAS))
            .andExpect(jsonPath("$.orden").value(DEFAULT_ORDEN))
            .andExpect(jsonPath("$.estadoVuelo").value(DEFAULT_ESTADO_VUELO))
            .andExpect(jsonPath("$.esEscala").value(DEFAULT_ES_ESCALA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVuelos() throws Exception {
        // Get the vuelos
        restVuelosMockMvc.perform(get("/api/vuelos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVuelos() throws Exception {
        // Initialize the database
        vuelosRepository.saveAndFlush(vuelos);

        int databaseSizeBeforeUpdate = vuelosRepository.findAll().size();

        // Update the vuelos
        Vuelos updatedVuelos = vuelosRepository.findById(vuelos.getId()).get();
        // Disconnect from session so that the updates on updatedVuelos are not directly saved in db
        em.detach(updatedVuelos);
        updatedVuelos
            .nombre(UPDATED_NOMBRE)
            .diasSemana(UPDATED_DIAS_SEMANA)
            .fechaSalida(UPDATED_FECHA_SALIDA)
            .fechaArribo9(UPDATED_FECHA_ARRIBO_9)
            .plazasVacias(UPDATED_PLAZAS_VACIAS)
            .orden(UPDATED_ORDEN)
            .estadoVuelo(UPDATED_ESTADO_VUELO)
            .esEscala(UPDATED_ES_ESCALA);
        VuelosDTO vuelosDTO = vuelosMapper.toDto(updatedVuelos);

        restVuelosMockMvc.perform(put("/api/vuelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vuelosDTO)))
            .andExpect(status().isOk());

        // Validate the Vuelos in the database
        List<Vuelos> vuelosList = vuelosRepository.findAll();
        assertThat(vuelosList).hasSize(databaseSizeBeforeUpdate);
        Vuelos testVuelos = vuelosList.get(vuelosList.size() - 1);
        assertThat(testVuelos.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testVuelos.getDiasSemana()).isEqualTo(UPDATED_DIAS_SEMANA);
        assertThat(testVuelos.getFechaSalida()).isEqualTo(UPDATED_FECHA_SALIDA);
        assertThat(testVuelos.getFechaArribo9()).isEqualTo(UPDATED_FECHA_ARRIBO_9);
        assertThat(testVuelos.getPlazasVacias()).isEqualTo(UPDATED_PLAZAS_VACIAS);
        assertThat(testVuelos.getOrden()).isEqualTo(UPDATED_ORDEN);
        assertThat(testVuelos.getEstadoVuelo()).isEqualTo(UPDATED_ESTADO_VUELO);
        assertThat(testVuelos.isEsEscala()).isEqualTo(UPDATED_ES_ESCALA);
    }

    @Test
    @Transactional
    public void updateNonExistingVuelos() throws Exception {
        int databaseSizeBeforeUpdate = vuelosRepository.findAll().size();

        // Create the Vuelos
        VuelosDTO vuelosDTO = vuelosMapper.toDto(vuelos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVuelosMockMvc.perform(put("/api/vuelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vuelosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vuelos in the database
        List<Vuelos> vuelosList = vuelosRepository.findAll();
        assertThat(vuelosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVuelos() throws Exception {
        // Initialize the database
        vuelosRepository.saveAndFlush(vuelos);

        int databaseSizeBeforeDelete = vuelosRepository.findAll().size();

        // Delete the vuelos
        restVuelosMockMvc.perform(delete("/api/vuelos/{id}", vuelos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vuelos> vuelosList = vuelosRepository.findAll();
        assertThat(vuelosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vuelos.class);
        Vuelos vuelos1 = new Vuelos();
        vuelos1.setId(1L);
        Vuelos vuelos2 = new Vuelos();
        vuelos2.setId(vuelos1.getId());
        assertThat(vuelos1).isEqualTo(vuelos2);
        vuelos2.setId(2L);
        assertThat(vuelos1).isNotEqualTo(vuelos2);
        vuelos1.setId(null);
        assertThat(vuelos1).isNotEqualTo(vuelos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VuelosDTO.class);
        VuelosDTO vuelosDTO1 = new VuelosDTO();
        vuelosDTO1.setId(1L);
        VuelosDTO vuelosDTO2 = new VuelosDTO();
        assertThat(vuelosDTO1).isNotEqualTo(vuelosDTO2);
        vuelosDTO2.setId(vuelosDTO1.getId());
        assertThat(vuelosDTO1).isEqualTo(vuelosDTO2);
        vuelosDTO2.setId(2L);
        assertThat(vuelosDTO1).isNotEqualTo(vuelosDTO2);
        vuelosDTO1.setId(null);
        assertThat(vuelosDTO1).isNotEqualTo(vuelosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vuelosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vuelosMapper.fromId(null)).isNull();
    }
}
