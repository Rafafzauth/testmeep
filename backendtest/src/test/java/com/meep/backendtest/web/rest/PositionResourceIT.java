package com.meep.backendtest.web.rest;

import com.meep.backendtest.BackendtestApp;
import com.meep.backendtest.domain.Position;
import com.meep.backendtest.repository.PositionRepository;
import com.meep.backendtest.service.PositionService;
import com.meep.backendtest.web.rest.errors.ExceptionTranslator;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.meep.backendtest.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PositionResource} REST controller.
 */
@SpringBootTest(classes = BackendtestApp.class)
public class PositionResourceIT {

    private static final String DEFAULT_LOWER_LEFT_LAT_LON = "AAAAAAAAAA";
    private static final String UPDATED_LOWER_LEFT_LAT_LON = "BBBBBBBBBB";

    private static final String DEFAULT_UPPER_RIGHT_LAT_LON = "AAAAAAAAAA";
    private static final String UPDATED_UPPER_RIGHT_LAT_LON = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_ZONE_IDS = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_ZONE_IDS = "BBBBBBBBBB";

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private PositionService positionService;

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

    private MockMvc restPositionMockMvc;

    private Position position;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PositionResource positionResource = new PositionResource(positionService);
        this.restPositionMockMvc = MockMvcBuilders.standaloneSetup(positionResource)
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
    public static Position createEntity(EntityManager em) {
        Position position = new Position()
            .lowerLeftLatLon(DEFAULT_LOWER_LEFT_LAT_LON)
            .upperRightLatLon(DEFAULT_UPPER_RIGHT_LAT_LON)
            .companyZoneIds(DEFAULT_COMPANY_ZONE_IDS)
            .result(DEFAULT_RESULT)
            .update(DEFAULT_UPDATE);
        return position;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Position createUpdatedEntity(EntityManager em) {
        Position position = new Position()
            .lowerLeftLatLon(UPDATED_LOWER_LEFT_LAT_LON)
            .upperRightLatLon(UPDATED_UPPER_RIGHT_LAT_LON)
            .companyZoneIds(UPDATED_COMPANY_ZONE_IDS)
            .result(UPDATED_RESULT)
            .update(UPDATED_UPDATE);
        return position;
    }

    @BeforeEach
    public void initTest() {
        position = createEntity(em);
    }

    @Test
    @Transactional
    public void createPosition() throws Exception {
        int databaseSizeBeforeCreate = positionRepository.findAll().size();

        // Create the Position
        restPositionMockMvc.perform(post("/api/positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(position)))
            .andExpect(status().isCreated());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeCreate + 1);
        Position testPosition = positionList.get(positionList.size() - 1);
        assertThat(testPosition.getLowerLeftLatLon()).isEqualTo(DEFAULT_LOWER_LEFT_LAT_LON);
        assertThat(testPosition.getUpperRightLatLon()).isEqualTo(DEFAULT_UPPER_RIGHT_LAT_LON);
        assertThat(testPosition.getCompanyZoneIds()).isEqualTo(DEFAULT_COMPANY_ZONE_IDS);
        assertThat(testPosition.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testPosition.getUpdate()).isEqualTo(DEFAULT_UPDATE);
    }

    @Test
    @Transactional
    public void createPositionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = positionRepository.findAll().size();

        // Create the Position with an existing ID
        position.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPositionMockMvc.perform(post("/api/positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(position)))
            .andExpect(status().isBadRequest());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPositions() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        // Get all the positionList
        restPositionMockMvc.perform(get("/api/positions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(position.getId().intValue())))
            .andExpect(jsonPath("$.[*].lowerLeftLatLon").value(hasItem(DEFAULT_LOWER_LEFT_LAT_LON)))
            .andExpect(jsonPath("$.[*].upperRightLatLon").value(hasItem(DEFAULT_UPPER_RIGHT_LAT_LON)))
            .andExpect(jsonPath("$.[*].companyZoneIds").value(hasItem(DEFAULT_COMPANY_ZONE_IDS)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].update").value(hasItem(DEFAULT_UPDATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPosition() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        // Get the position
        restPositionMockMvc.perform(get("/api/positions/{id}", position.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(position.getId().intValue()))
            .andExpect(jsonPath("$.lowerLeftLatLon").value(DEFAULT_LOWER_LEFT_LAT_LON))
            .andExpect(jsonPath("$.upperRightLatLon").value(DEFAULT_UPPER_RIGHT_LAT_LON))
            .andExpect(jsonPath("$.companyZoneIds").value(DEFAULT_COMPANY_ZONE_IDS))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT))
            .andExpect(jsonPath("$.update").value(DEFAULT_UPDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPosition() throws Exception {
        // Get the position
        restPositionMockMvc.perform(get("/api/positions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePosition() throws Exception {
        // Initialize the database
        positionService.save(position);

        int databaseSizeBeforeUpdate = positionRepository.findAll().size();

        // Update the position
        Position updatedPosition = positionRepository.findById(position.getId()).get();
        // Disconnect from session so that the updates on updatedPosition are not directly saved in db
        em.detach(updatedPosition);
        updatedPosition
            .lowerLeftLatLon(UPDATED_LOWER_LEFT_LAT_LON)
            .upperRightLatLon(UPDATED_UPPER_RIGHT_LAT_LON)
            .companyZoneIds(UPDATED_COMPANY_ZONE_IDS)
            .result(UPDATED_RESULT)
            .update(UPDATED_UPDATE);

        restPositionMockMvc.perform(put("/api/positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPosition)))
            .andExpect(status().isOk());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeUpdate);
        Position testPosition = positionList.get(positionList.size() - 1);
        assertThat(testPosition.getLowerLeftLatLon()).isEqualTo(UPDATED_LOWER_LEFT_LAT_LON);
        assertThat(testPosition.getUpperRightLatLon()).isEqualTo(UPDATED_UPPER_RIGHT_LAT_LON);
        assertThat(testPosition.getCompanyZoneIds()).isEqualTo(UPDATED_COMPANY_ZONE_IDS);
        assertThat(testPosition.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testPosition.getUpdate()).isEqualTo(UPDATED_UPDATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPosition() throws Exception {
        int databaseSizeBeforeUpdate = positionRepository.findAll().size();

        // Create the Position

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPositionMockMvc.perform(put("/api/positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(position)))
            .andExpect(status().isBadRequest());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePosition() throws Exception {
        // Initialize the database
        positionService.save(position);

        int databaseSizeBeforeDelete = positionRepository.findAll().size();

        // Delete the position
        restPositionMockMvc.perform(delete("/api/positions/{id}", position.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Position.class);
        Position position1 = new Position();
        position1.setId(1L);
        Position position2 = new Position();
        position2.setId(position1.getId());
        assertThat(position1).isEqualTo(position2);
        position2.setId(2L);
        assertThat(position1).isNotEqualTo(position2);
        position1.setId(null);
        assertThat(position1).isNotEqualTo(position2);
    }
}
