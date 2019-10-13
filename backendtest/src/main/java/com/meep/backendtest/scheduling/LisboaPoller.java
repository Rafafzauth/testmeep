package com.meep.backendtest.scheduling;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

import com.meep.backendtest.config.ApplicationProperties;
import com.meep.backendtest.domain.Position;
import com.meep.backendtest.service.JSONUtilsService;
import com.meep.backendtest.service.PositionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.jsonwebtoken.io.IOException;

@Component
public class LisboaPoller {

    private final Logger log = LoggerFactory.getLogger(LisboaPoller.class);

    private final PositionService positionService;

    private final ApplicationProperties applicationProperties;

    private final JSONUtilsService jsonUtilsService;

    private final RestTemplate restTemplate;

    private final HttpHeaders httpHeaders;

    private final HttpEntity <String> entity;


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public LisboaPoller(PositionService positionService,
                        RestTemplate restTemplate,
                        ApplicationProperties applicationProperties,
                        JSONUtilsService jsonUtilsService,
                        HttpHeaders httpHeaders,
                        HttpEntity <String> entity) {
        this.positionService = positionService;
        this.restTemplate = restTemplate;
        this.applicationProperties = applicationProperties;
        this.jsonUtilsService = jsonUtilsService;
        this.httpHeaders = httpHeaders;
        this.entity = entity;
    }

    @Scheduled(fixedRate = 30000)
    public void report() throws java.io.IOException {
        

            String result = restTemplate.exchange(applicationProperties.getMeeturl(), HttpMethod.GET, entity, String.class).getBody();

            Position positionSave = positionService.findOneByPosition(applicationProperties.getCompanyZoneIds(),
                                                        applicationProperties.getLowerLeftLatLon(),
                                                        applicationProperties.getUpperRightLatLon());

            if (positionSave.getResult()!=null) {
                String compare = jsonUtilsService.compareJSON(result, positionSave.getResult());
                log.info("Existe ");
                positionSave.setResult(compare);
            } else {
                log.info("NO Existe ");
                positionSave.setResult(result);
            }

            positionSave.setUpdate(LocalDate.now());

            positionService.save(positionSave);

        
    }

}

