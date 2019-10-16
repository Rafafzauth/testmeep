package com.meep.demo2meep.web.rest;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.meep.demo2meep.Demo2meepApplication;
import com.meep.demo2meep.domain.MeepPosition;
import com.meep.demo2meep.repository.PositionRepository;
import com.meep.demo2meep.service.PositionService;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
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

/**
 * Integration tests for the {@link PositionResource} REST controller.
 */
@SpringBootTest(classes = Demo2meepApplication.class)
public class PositionResourceIT {

    private static final String DEFAULT_LOWER_LEFT_LAT_LON = "AAAAAAAAAA";
    
    private static final String DEFAULT_UPPER_RIGHT_LAT_LON = "AAAAAAAAAA";
    
    private static final String DEFAULT_COMPANY_ZONE_IDS = "AAAAAAAAAA";
    
    private static final String DEFAULT_RESULT = "[{\"id\":\"DriveNow_13673363\",\"name\":\"M-DX7790\",\"x\":-9.1462,\"y\":38.738,\"licencePlate\":\"M-DX7790\",\"range\":0,\"batteryLevel\":50,\"seats\":4,\"model\":\"BMW 1 Series\",\"resourceImageId\":\"vehicle_gen_dn_bmw_s1\",\"pricePerMinuteParking\":15.0,\"pricePerMinuteDriving\":29.0,\"realTimeData\":true,\"engineType\":\"DIESEL\",\"resourceType\":\"CAR\",\"companyZoneId\":545,\"helmets\":0,\"status\":\"EQUAL\"}]";
    
    
    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private PositionService positionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private Validator validator;

    private MockMvc restPositionMockMvc;

    private MeepPosition position;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PositionResource positionResource = new PositionResource(positionService, null);
        this.restPositionMockMvc = MockMvcBuilders.standaloneSetup(positionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }
    
    
    @Test
    @Transactional
    public void getPosition() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        // Get position
        restPositionMockMvc.perform(get("/api/position"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(position.getId().intValue())))
            .andExpect(jsonPath("$.[*].lowerLeftLatLon").value(hasItem(DEFAULT_LOWER_LEFT_LAT_LON)))
            .andExpect(jsonPath("$.[*].upperRightLatLon").value(hasItem(DEFAULT_UPPER_RIGHT_LAT_LON)))
            .andExpect(jsonPath("$.[*].companyZoneIds").value(hasItem(DEFAULT_COMPANY_ZONE_IDS)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)));
    }

    @Test
    @Transactional
    public void getNonExistingPosition() throws Exception {
        // Get the position
        restPositionMockMvc.perform(get("/api/position"))
            .andExpect(status().isNotFound());
    }

}
