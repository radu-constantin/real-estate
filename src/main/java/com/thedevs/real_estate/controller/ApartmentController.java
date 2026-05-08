package com.thedevs.real_estate.controller;

import com.thedevs.real_estate.model.Apartment;
import com.thedevs.real_estate.model.House;
import com.thedevs.real_estate.service.ApartmentService;
import com.thedevs.real_estate.service.HouseService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apartments")
public class ApartmentController {
    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @GetMapping()
    List<Apartment> getAll() {
        return apartmentService.getAllApartments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apartment> getById(@PathVariable Long id) {
        return apartmentService.getApartmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<Apartment> create(@RequestBody Apartment apartment) {
        Apartment createdApartment = apartmentService.createApartment(apartment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdApartment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Apartment> update(@PathVariable Long id, @RequestBody Apartment apartment) {
        Apartment updatedApartment = apartmentService.updateApartment(id, apartment);
        return ResponseEntity.ok(updatedApartment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        apartmentService.deleteApartment(id);
        return ResponseEntity.noContent().build();
    }

}
