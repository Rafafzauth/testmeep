package com.meep.demo2meep.repository;

import java.util.List;

import com.meep.demo2meep.domain.MeepPosition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Position entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PositionRepository extends JpaRepository<MeepPosition, Long> {

    @Query("select position from MeepPosition position where " + 
                                        "position.companyZoneIds=:companyZoneIds and " + 
                                        "position.lowerLeftLatLon=:lowerLeftLatLon and " +
                                        "position.upperRightLatLon=:upperRightLatLon")
    List<MeepPosition> findPositionByLocation(@Param("companyZoneIds") String companyZoneIds,
                                    @Param("lowerLeftLatLon") String lowerLeftLatLon,
                                    @Param("upperRightLatLon") String upperRightLatLon);

}
