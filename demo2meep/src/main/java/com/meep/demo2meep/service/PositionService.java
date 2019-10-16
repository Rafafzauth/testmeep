package com.meep.demo2meep.service;

import java.util.Optional;

import com.meep.demo2meep.domain.MeepPosition;


/**
 * Service Interface for managing {@link Position}.
 */
public interface PositionService {

    MeepPosition save(MeepPosition position);

    Optional<MeepPosition> findOneByPosition(String companyZoneIds, String LowerLeftLatLon, String upperRightLatLon);
}
