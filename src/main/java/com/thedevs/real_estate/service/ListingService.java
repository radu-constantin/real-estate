package com.thedevs.real_estate.service;

import com.thedevs.real_estate.model.Listing;
import com.thedevs.real_estate.model.enums.ListingStatus;
import com.thedevs.real_estate.repository.ListingRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;


@Service
public class ListingService {

    private final ListingRepository listingRepository;

    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public List<Listing> getAllListings(Long userId, String address) {
        return listingRepository.findAll((Specification<Listing>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (userId != null) {
                predicates.add(cb.equal(root.get("property").get("user").get("id"), userId));
            }
            if (address != null && !address.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("property").get("address")),
                        "%" + address.toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
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