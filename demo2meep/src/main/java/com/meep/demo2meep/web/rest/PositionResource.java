package com.meep.demo2meep.web.rest;

import java.util.Optional;

import com.meep.demo2meep.config.Meep2demoProperties;
import com.meep.demo2meep.domain.MeepPosition;
import com.meep.demo2meep.service.PositionService;
import com.meep.demo2meep.web.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link com.meep.backendtest.domain.MeepPosition}.
 */
@RestController
@RequestMapping("/api")
public class PositionResource {

    private final Logger log = LoggerFactory.getLogger(PositionResource.class);

    private final PositionService positionService;

    private final Meep2demoProperties meep2demoProperties;

    public PositionResource(PositionService positionService,
                            Meep2demoProperties meep2demoProperties) {
        this.positionService = positionService;
        this.meep2demoProperties = meep2demoProperties;
    }

    @GetMapping("/position")
    public ResponseEntity<MeepPosition> getPosition() {
        log.debug("REST request to get MeepPosition");

        Optional<MeepPosition> position = positionService.findOneByPosition(meep2demoProperties.getCompanyZoneIds(),
                                                                meep2demoProperties.getLowerLeftLatLon(),
                                                                meep2demoProperties.getUpperRightLatLon());
        return ResponseUtil.wrapOrNotFound(position);
    }

    
}
