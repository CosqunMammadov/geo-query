package com.test.geoquery.service;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

import java.util.List;

public class PolygonGenerator {

    private static final double EARTH_RADIUS = 6371e3;

    private static double toRadians(double degree) {
        return degree * Math.PI / 180;
    }

    private static double toDegrees(double radian) {
        return radian * 180 / Math.PI;
    }

    private static Coordinate calculateNewCoordinate(double latitude, double longitude, double distance, double azimuth) {
        double angularDistance = distance / EARTH_RADIUS;
        double azimuthRad = toRadians(azimuth);

        double latRad = toRadians(latitude);
        double lonRad = toRadians(longitude);

        double newLatRad = Math.asin(Math.sin(latRad) * Math.cos(angularDistance) +
                Math.cos(latRad) * Math.sin(angularDistance) * Math.cos(azimuthRad));
        double newLonRad = lonRad + Math.atan2(Math.sin(azimuthRad) * Math.sin(angularDistance) * Math.cos(latRad),
                Math.cos(angularDistance) - Math.sin(latRad) * Math.sin(newLatRad));

        return new Coordinate(toDegrees(newLonRad), toDegrees(newLatRad));
    }

    public static Polygon createPolygon(double startLat, double startLon, double distance, double azimuth) {
        GeometryFactory geometryFactory = new GeometryFactory();
        double angleWidth = 120;
        int numPoints = 20;
        Coordinate[] coordinates = new Coordinate[numPoints + 2];

        coordinates[0] = new Coordinate(startLon, startLat);

        double halfAngle = angleWidth / 2.0;
        double angleStep = angleWidth / numPoints;

        for (int i = 0; i < numPoints; i++) {
            double currentAzimuth = azimuth - halfAngle + (i * angleStep);
            coordinates[i + 1] = calculateNewCoordinate(startLat, startLon, distance, currentAzimuth);
        }

        coordinates[numPoints + 1] = coordinates[0];

        return geometryFactory.createPolygon(coordinates);
    }

    public static void main(String[] args) {

        double startLatitude = 40.425489; //y
        double startLongitude = 49.860737; //x

        double distance = 3500;
        double azimuth = 330;

        Polygon polygon = createPolygon(startLatitude, startLongitude, distance, azimuth);

        for (Coordinate coordinate : polygon.getCoordinates()) {
            System.out.print(List.of(coordinate.getX(), coordinate.getY()) + ",");
        }
    }
}
