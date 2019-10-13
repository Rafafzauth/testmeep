package com.meep.backendtest.service;

import com.meep.backendtest.domain.Position;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Position}.
 */
public interface PositionService {

    /**
     * Save a position.
     *
     * @param position the entity to save.
     * @return the persisted entity.
     */
    Position save(Position position);

    /**
     * Get all the positions.
     *
     * @return the list of entities.
     */
    List<Position> findAll();


    /**
     * Get the "id" position.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Position> findOne(Long id);

    /**
     * Delete the "id" position.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get one by position.
     *
     * @return the one of entity.
     */
    Position findOneByPosition(String companyZoneIds, String LowerLeftLatLon, String upperRightLatLon);
}
