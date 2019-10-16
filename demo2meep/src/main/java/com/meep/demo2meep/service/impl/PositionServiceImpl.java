package com.meep.demo2meep.service.impl;

import java.util.List;
import java.util.Optional;

import com.meep.demo2meep.domain.MeepPosition;
import com.meep.demo2meep.repository.PositionRepository;
import com.meep.demo2meep.service.PositionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MeepPosition}.
 */
@Service
@Transactional
public class PositionServiceImpl implements PositionService {

    private final Logger log = LoggerFactory.getLogger(PositionServiceImpl.class);

    private final PositionRepository positionRepository;

    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    /**
     * Save a position.
     *
     * @param meepPosition the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MeepPosition save(MeepPosition meepPosition) {
        log.debug("Request to save MeepPosition : {}", meepPosition);
        return positionRepository.save(meepPosition);
    }

    /**
     * Get one position by MeepPosition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MeepPosition> findOneByPosition(String companyZoneIds, String lowerLeftLatLon, String upperRightLatLon) {
        log.debug("Request to get Position");

        List<MeepPosition> lstPositions = positionRepository.findPositionByLocation(companyZoneIds, lowerLeftLatLon,
                upperRightLatLon);

        // TODO: avoid duplicates
        Optional<MeepPosition> posAux = lstPositions.stream().findFirst();

        return posAux;
    }
}
