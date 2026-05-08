package com.thedevs.real_estate.service;

import com.thedevs.real_estate.model.Listing;
import com.thedevs.real_estate.model.enums.ListingStatus;
import com.thedevs.real_estate.repository.ListingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ListingService {

    private final ListingRepository listingRepository;

    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public List<Listing> getAllListings() {
        return (List<Listing>) listingRepository.findAll();
    }

    public Optional<Listing> getListingById(Long id) {
        return listingRepository.findById(id);
    }

    public Listing createListing(Listing listing) {
        listing.setListedAt(LocalDate.now());
        listing.setStatus(ListingStatus.active);
        return listingRepository.save(listing);
    }

    public Listing updateListing(Long id, Listing updated) {
        return listingRepository.findById(id).map(listing -> {
            listing.setProperty(updated.getProperty());
            listing.setStatus(updated.getStatus());
            listing.setUpdatedAt(LocalDate.now());
            return listingRepository.save(listing);
        }).orElseThrow(() -> new RuntimeException("Listing not found"));
    }

    public void deleteListing(Long id) {
        if (!listingRepository.existsById(id)) {
            throw new RuntimeException("Listing not found");
        }
        listingRepository.deleteById(id);
    }
}