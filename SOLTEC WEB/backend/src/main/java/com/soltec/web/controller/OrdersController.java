package com.soltec.web.controller;

import com.soltec.web.entity.*;
import com.soltec.web.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired private PlaceRepository placeRepository;
    @Autowired private FloorRepository floorRepository;
    @Autowired private SharedTicketRepository sharedTicketRepository;

    @GetMapping("/tables")
    public List<Map<String, Object>> getTables() {
        List<Place> places = placeRepository.findAllByOrderByNameAsc();
        Set<String> occupiedTableIds = new HashSet<>();

        // Find tables with open tickets
        sharedTicketRepository.findAll().forEach(st -> {
            occupiedTableIds.add(st.getId());
        });

        return places.stream().map(p -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", p.getId());
            map.put("name", p.getName());
            map.put("x", p.getX());
            map.put("y", p.getY());
            map.put("floorId", p.getFloorId());
            map.put("occupied", occupiedTableIds.contains(p.getId()));
            map.put("waiter", p.getWaiter());
            map.put("ticketId", p.getTicketid());
            return map;
        }).collect(Collectors.toList());
    }

    @GetMapping("/floors")
    public List<Map<String, Object>> getFloors() {
        return floorRepository.findAll().stream().map(f -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", f.getId());
            map.put("name", f.getName());
            return map;
        }).collect(Collectors.toList());
    }
}
