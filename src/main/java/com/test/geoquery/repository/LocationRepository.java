package com.test.geoquery.repository;

import com.test.geoquery.entity.Location;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("select l from Location l where st_intersects(l.polygon, :polygon)")
    List<Location> findContainingCell(Polygon polygon);
}
