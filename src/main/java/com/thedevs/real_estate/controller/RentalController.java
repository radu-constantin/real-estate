package com.thedevs.real_estate.controller;

import com.thedevs.real_estate.dto.CreateRentalRequest;
import com.thedevs.real_estate.dto.UpdateRentalRequest;
import com.thedevs.real_estate.model.Rental;
import com.thedevs.real_estate.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<List<Rental>> getAll() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getById(@PathVariable Long id) {
        return rentalService.getRentalById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Rental> create(@RequestBody CreateRentalRequest request) {
        Rental createdRental = rentalService.createRental(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRental);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rental> update(@PathVariable Long id, @RequestBody UpdateRentalRequest request) {
        return ResponseEntity.ok(rentalService.updateRental(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            rentalService.deleteRental(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}