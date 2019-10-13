package com.meep.backendtest.repository;
import com.meep.backendtest.domain.Position;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data  repository for the Position entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    @Query("select position from Position position where " + 
                                        "position.companyZoneIds=:companyZoneIds and " + 
                                        "position.lowerLeftLatLon=:lowerLeftLatLon and " +
                                        "position.upperRightLatLon=:upperRightLatLon")
    List<Position> findPositionByLocation(@Param("companyZoneIds") String companyZoneIds,
                                    @Param("lowerLeftLatLon") String lowerLeftLatLon,
                                    @Param("upperRightLatLon") String upperRightLatLon);

}
