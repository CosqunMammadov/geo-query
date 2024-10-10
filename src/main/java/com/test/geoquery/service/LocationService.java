package com.test.geoquery.service;

import com.test.geoquery.entity.Location;
import com.test.geoquery.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository cellRepository;

    public void save(double startLat, double startLon, double distance, double azimuth){
        Polygon polygon = PolygonGenerator.createPolygon(startLat, startLon, distance, azimuth);
        Location location = new Location();
        location.setPolygon(polygon);
        cellRepository.save(location);
    }

    public List<Location> findIntersects(){
        Coordinate[] coordinates = new Coordinate[4];
        coordinates[0] = new Coordinate(49.804999169702, 40.454423951264204);
        coordinates[1] = new Coordinate(49.83843628251353, 40.44169653089057);
        coordinates[2] = new Coordinate(49.8025267917659, 40.43723651693168);
        coordinates[3] = new Coordinate(49.804999169702, 40.454423951264204);
        GeometryFactory geometryFactory = new GeometryFactory();
        Polygon polygon = geometryFactory.createPolygon(coordinates);
        List<Location> locations = cellRepository.findContainingCell(polygon);
        return locations;
    }
}
