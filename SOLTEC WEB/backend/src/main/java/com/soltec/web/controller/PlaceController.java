package com.soltec.web.controller;

import com.soltec.web.entity.Floor;
import com.soltec.web.entity.Place;
import com.soltec.web.repository.FloorRepository;
import com.soltec.web.repository.PlaceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlaceController {

    private final FloorRepository floorRepository;
    private final PlaceRepository placeRepository;

    public PlaceController(FloorRepository floorRepository, PlaceRepository placeRepository) {
        this.floorRepository = floorRepository;
        this.placeRepository = placeRepository;
    }

    @GetMapping("/floors")
    public ResponseEntity<List<Floor>> getFloors() {
        return ResponseEntity.ok(floorRepository.findAll());
    }

    @GetMapping("/tables")
    public ResponseEntity<List<Place>> getTables() {
        return ResponseEntity.ok(placeRepository.findAll());
    }

    @GetMapping("/tables/floor/{floorId}")
    public ResponseEntity<List<Place>> getTablesByFloor(@PathVariable String floorId) {
        return ResponseEntity.ok(placeRepository.findByFloorId(floorId));
    }
}
