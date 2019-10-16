package com.meep.demo2meep.scheduling;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;

import com.meep.demo2meep.config.Meep2demoProperties;
import com.meep.demo2meep.domain.MeepPosition;
import com.meep.demo2meep.service.JSONUtilsService;
import com.meep.demo2meep.service.PositionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Poller with scheduler
 */
@Component
public class LisboaPoller {

    private final Logger log = LoggerFactory.getLogger(LisboaPoller.class);

    private final PositionService positionService;

    private final Meep2demoProperties meep2demoProperties;

    private final JSONUtilsService jsonUtilsService;

    private final RestTemplate restTemplate;

    private final HttpEntity <String> entity;

    public static final long RATE = 30000;


    public LisboaPoller(PositionService positionService,
                        RestTemplate restTemplate,
                        Meep2demoProperties meep2demoProperties,
                        JSONUtilsService jsonUtilsService,
                        HttpEntity <String> entity) {
        this.positionService = positionService;
        this.restTemplate = restTemplate;
        this.meep2demoProperties = meep2demoProperties;
        this.jsonUtilsService = jsonUtilsService;
        this.entity = entity;
    }

    @Scheduled(fixedRate = RATE)
    public void report()  {
        try {
      
            String result = restTemplate.exchange(meep2demoProperties.getMeepurl(), HttpMethod.GET, entity, String.class).getBody();

            Optional<MeepPosition> positionSave = positionService.findOneByPosition(meep2demoProperties.getCompanyZoneIds(),
                                                            meep2demoProperties.getLowerLeftLatLon(),
                                                            meep2demoProperties.getUpperRightLatLon());

            if (positionSave.isPresent()) {
                String compare = jsonUtilsService.compareAndActualizeJSON(result, positionSave.get().getResult());
                positionSave.get().setResult(compare);
            } else {
                MeepPosition positionNew = new MeepPosition();
                positionNew.setCompanyZoneIds(meep2demoProperties.getCompanyZoneIds());
                positionNew.setLowerLeftLatLon(meep2demoProperties.getLowerLeftLatLon());
                positionNew.setUpperRightLatLon(meep2demoProperties.getUpperRightLatLon());
                positionNew.setResult(result);
                positionSave = Optional.of(positionNew);
            }
        
            positionSave.get().setDateUpdate(new Timestamp(System.currentTimeMillis()));

            positionService.save(positionSave.get());

        } catch (IOException exception) {
            log.error(exception.getMessage());
        } catch (Exception exception) {
            log.error("GLOBAL" + exception.getMessage());
        }
        
    }

}

