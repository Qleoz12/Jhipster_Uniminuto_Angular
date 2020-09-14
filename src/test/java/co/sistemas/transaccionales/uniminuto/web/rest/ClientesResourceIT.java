package co.sistemas.transaccionales.uniminuto.web.rest;

import co.sistemas.transaccionales.uniminuto.PruebaJhipsterApp;
import co.sistemas.transaccionales.uniminuto.domain.Clientes;
import co.sistemas.transaccionales.uniminuto.repository.ClientesRepository;
import co.sistemas.transaccionales.uniminuto.service.ClientesService;
import co.sistemas.transaccionales.uniminuto.service.dto.ClientesDTO;
import co.sistemas.transaccionales.uniminuto.service.mapper.ClientesMapper;
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
 * Integration tests for the {@link ClientesResource} REST controller.
 */
@SpringBootTest(classes = PruebaJhipsterApp.class)
public class ClientesResourceIT {

    private static final String DEFAULT_NOMBRES = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRES = "BBBBBBBBBB";

    private static final String DEFAULT_APPELIDOS = "AAAAAAAAAA";
    private static final String UPDATED_APPELIDOS = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTO_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTO_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO_NUMERO = "BBBBBBBBBB";

    @Autowired
    private ClientesRepository clientesRepository;

    @Autowired
    private ClientesMapper clientesMapper;

    @Autowired
    private ClientesService clientesService;

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

    private MockMvc restClientesMockMvc;

    private Clientes clientes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientesResource clientesResource = new ClientesResource(clientesService);
        this.restClientesMockMvc = MockMvcBuilders.standaloneSetup(clientesResource)
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
    public static Clientes createEntity(EntityManager em) {
        Clientes clientes = new Clientes()
            .nombres(DEFAULT_NOMBRES)
            .appelidos(DEFAULT_APPELIDOS)
            .documentoTipo(DEFAULT_DOCUMENTO_TIPO)
            .documentoNumero(DEFAULT_DOCUMENTO_NUMERO);
        return clientes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clientes createUpdatedEntity(EntityManager em) {
        Clientes clientes = new Clientes()
            .nombres(UPDATED_NOMBRES)
            .appelidos(UPDATED_APPELIDOS)
            .documentoTipo(UPDATED_DOCUMENTO_TIPO)
            .documentoNumero(UPDATED_DOCUMENTO_NUMERO);
        return clientes;
    }

    @BeforeEach
    public void initTest() {
        clientes = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientes() throws Exception {
        int databaseSizeBeforeCreate = clientesRepository.findAll().size();

        // Create the Clientes
        ClientesDTO clientesDTO = clientesMapper.toDto(clientes);
        restClientesMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientesDTO)))
            .andExpect(status().isCreated());

        // Validate the Clientes in the database
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeCreate + 1);
        Clientes testClientes = clientesList.get(clientesList.size() - 1);
        assertThat(testClientes.getNombres()).isEqualTo(DEFAULT_NOMBRES);
        assertThat(testClientes.getAppelidos()).isEqualTo(DEFAULT_APPELIDOS);
        assertThat(testClientes.getDocumentoTipo()).isEqualTo(DEFAULT_DOCUMENTO_TIPO);
        assertThat(testClientes.getDocumentoNumero()).isEqualTo(DEFAULT_DOCUMENTO_NUMERO);
    }

    @Test
    @Transactional
    public void createClientesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientesRepository.findAll().size();

        // Create the Clientes with an existing ID
        clientes.setId(1L);
        ClientesDTO clientesDTO = clientesMapper.toDto(clientes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientesMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Clientes in the database
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClientes() throws Exception {
        // Initialize the database
        clientesRepository.saveAndFlush(clientes);

        // Get all the clientesList
        restClientesMockMvc.perform(get("/api/clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientes.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombres").value(hasItem(DEFAULT_NOMBRES)))
            .andExpect(jsonPath("$.[*].appelidos").value(hasItem(DEFAULT_APPELIDOS)))
            .andExpect(jsonPath("$.[*].documentoTipo").value(hasItem(DEFAULT_DOCUMENTO_TIPO)))
            .andExpect(jsonPath("$.[*].documentoNumero").value(hasItem(DEFAULT_DOCUMENTO_NUMERO)));
    }
    
    @Test
    @Transactional
    public void getClientes() throws Exception {
        // Initialize the database
        clientesRepository.saveAndFlush(clientes);

        // Get the clientes
        restClientesMockMvc.perform(get("/api/clientes/{id}", clientes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientes.getId().intValue()))
            .andExpect(jsonPath("$.nombres").value(DEFAULT_NOMBRES))
            .andExpect(jsonPath("$.appelidos").value(DEFAULT_APPELIDOS))
            .andExpect(jsonPath("$.documentoTipo").value(DEFAULT_DOCUMENTO_TIPO))
            .andExpect(jsonPath("$.documentoNumero").value(DEFAULT_DOCUMENTO_NUMERO));
    }

    @Test
    @Transactional
    public void getNonExistingClientes() throws Exception {
        // Get the clientes
        restClientesMockMvc.perform(get("/api/clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientes() throws Exception {
        // Initialize the database
        clientesRepository.saveAndFlush(clientes);

        int databaseSizeBeforeUpdate = clientesRepository.findAll().size();

        // Update the clientes
        Clientes updatedClientes = clientesRepository.findById(clientes.getId()).get();
        // Disconnect from session so that the updates on updatedClientes are not directly saved in db
        em.detach(updatedClientes);
        updatedClientes
            .nombres(UPDATED_NOMBRES)
            .appelidos(UPDATED_APPELIDOS)
            .documentoTipo(UPDATED_DOCUMENTO_TIPO)
            .documentoNumero(UPDATED_DOCUMENTO_NUMERO);
        ClientesDTO clientesDTO = clientesMapper.toDto(updatedClientes);

        restClientesMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientesDTO)))
            .andExpect(status().isOk());

        // Validate the Clientes in the database
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeUpdate);
        Clientes testClientes = clientesList.get(clientesList.size() - 1);
        assertThat(testClientes.getNombres()).isEqualTo(UPDATED_NOMBRES);
        assertThat(testClientes.getAppelidos()).isEqualTo(UPDATED_APPELIDOS);
        assertThat(testClientes.getDocumentoTipo()).isEqualTo(UPDATED_DOCUMENTO_TIPO);
        assertThat(testClientes.getDocumentoNumero()).isEqualTo(UPDATED_DOCUMENTO_NUMERO);
    }

    @Test
    @Transactional
    public void updateNonExistingClientes() throws Exception {
        int databaseSizeBeforeUpdate = clientesRepository.findAll().size();

        // Create the Clientes
        ClientesDTO clientesDTO = clientesMapper.toDto(clientes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientesMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Clientes in the database
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClientes() throws Exception {
        // Initialize the database
        clientesRepository.saveAndFlush(clientes);

        int databaseSizeBeforeDelete = clientesRepository.findAll().size();

        // Delete the clientes
        restClientesMockMvc.perform(delete("/api/clientes/{id}", clientes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clientes.class);
        Clientes clientes1 = new Clientes();
        clientes1.setId(1L);
        Clientes clientes2 = new Clientes();
        clientes2.setId(clientes1.getId());
        assertThat(clientes1).isEqualTo(clientes2);
        clientes2.setId(2L);
        assertThat(clientes1).isNotEqualTo(clientes2);
        clientes1.setId(null);
        assertThat(clientes1).isNotEqualTo(clientes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientesDTO.class);
        ClientesDTO clientesDTO1 = new ClientesDTO();
        clientesDTO1.setId(1L);
        ClientesDTO clientesDTO2 = new ClientesDTO();
        assertThat(clientesDTO1).isNotEqualTo(clientesDTO2);
        clientesDTO2.setId(clientesDTO1.getId());
        assertThat(clientesDTO1).isEqualTo(clientesDTO2);
        clientesDTO2.setId(2L);
        assertThat(clientesDTO1).isNotEqualTo(clientesDTO2);
        clientesDTO1.setId(null);
        assertThat(clientesDTO1).isNotEqualTo(clientesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clientesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clientesMapper.fromId(null)).isNull();
    }
}
