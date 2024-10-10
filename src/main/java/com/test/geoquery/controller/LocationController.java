package com.test.geoquery.controller;

import com.test.geoquery.entity.Location;
import com.test.geoquery.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/create")
    public void save(@RequestParam double startLat, @RequestParam double startLon, @RequestParam double distance, @RequestParam double azimuth){
        locationService.save(startLat, startLon, distance, azimuth);
    }

    @GetMapping("/all")
    public List<Location> getAll(){
        return locationService.findIntersects();
    }
}
