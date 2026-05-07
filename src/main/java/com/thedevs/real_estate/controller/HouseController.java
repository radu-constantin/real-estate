package com.thedevs.real_estate.controller;

import com.thedevs.real_estate.model.House;
import com.thedevs.real_estate.service.HouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/houses")
public class HouseController {
    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping()
    List<House> getAll() {
        return houseService.getAllHouses();
    }

    @GetMapping("/{id}")
    House getById(@PathVariable Long id) {
        return houseService.getHouseById(id);
    }

    @PostMapping
    ResponseEntity<House> create(@RequestBody House house) {
        House createdHouse = houseService.createHouse(house);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHouse);
    }

    @PutMapping("/{id}")
    ResponseEntity<House> update(@PathVariable Long id, @RequestBody House house) {
        House updatedHouse = houseService.updateHouse(id, house);
        return ResponseEntity.ok(updatedHouse);
    }

}
