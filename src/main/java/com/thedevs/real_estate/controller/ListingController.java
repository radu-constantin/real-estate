package com.thedevs.real_estate.controller;

import com.thedevs.real_estate.model.Listing;
import com.thedevs.real_estate.service.ListingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingService listingService;

    private static final Logger log = LoggerFactory.getLogger(ListingController.class);

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping
    public ResponseEntity<List<Listing>> getAll(@RequestParam(required = false) Long userId,
                                                @RequestParam(required = false) String address) {

        return ResponseEntity.ok(listingService.getAllListings(userId, address));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Listing> getById(@PathVariable Long id) {
        return listingService.getListingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Listing> create(@RequestBody Listing listing) {
        Listing createdListing = listingService.createListing(listing);
        log.info("Listing created: id={}", createdListing.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdListing);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Listing> update(@PathVariable Long id, @RequestBody Listing listing) {
        return ResponseEntity.ok(listingService.updateListing(id, listing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            listingService.deleteListing(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}