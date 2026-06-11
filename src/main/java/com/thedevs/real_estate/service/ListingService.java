package com.thedevs.real_estate.service;

import com.thedevs.real_estate.model.Listing;
import com.thedevs.real_estate.model.Rental;
import com.thedevs.real_estate.model.Sale;
import com.thedevs.real_estate.model.enums.ListingStatus;
import com.thedevs.real_estate.repository.ListingRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
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

    public Page<Listing> getAllListings(Long userId, String address, BigDecimal maxPrice,
                                        Integer minRooms, String listingType, Pageable pageable) {
        return listingRepository.findAll((Specification<Listing>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (userId != null) {
                predicates.add(cb.equal(root.get("property").get("user").get("id"), userId));
            }
            if (address != null && !address.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("property").get("address")),
                        "%" + address.toLowerCase() + "%"));
            }
            if (listingType != null && !listingType.isBlank()) {
                if ("sale".equalsIgnoreCase(listingType)) {
                    predicates.add(cb.equal(root.type(), Sale.class));
                    if (maxPrice != null) {
                        Root<Sale> saleRoot = cb.treat(root, Sale.class);
                        predicates.add(cb.lessThanOrEqualTo(saleRoot.get("askingPrice"), maxPrice));
                    }
                } else if ("rental".equalsIgnoreCase(listingType)) {
                    predicates.add(cb.equal(root.type(), Rental.class));
                    if (maxPrice != null) {
                        Root<Rental> rentalRoot = cb.treat(root, Rental.class);
                        predicates.add(cb.lessThanOrEqualTo(rentalRoot.get("monthlyRent"), maxPrice));
                    }
                }
            }
            if (minRooms != null && minRooms > 0) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("property").get("numberOfRooms"), minRooms));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }

    public Optional<Listing> getListingById(Integer id) {
        return listingRepository.findById(id);
    }

    public Listing createListing(Listing listing) {
        listing.setListedAt(LocalDate.now());
        listing.setStatus(ListingStatus.active);
        return listingRepository.save(listing);
    }

    public Listing updateListing(Integer id, Listing updated) {
        return listingRepository.findById(id).map(listing -> {
            listing.setProperty(updated.getProperty());
            listing.setStatus(updated.getStatus());
            listing.setUpdatedAt(LocalDate.now());
            return listingRepository.save(listing);
        }).orElseThrow(() -> new RuntimeException("Listing not found"));
    }

    public void deleteListing(Integer id) {
        if (!listingRepository.existsById(id)) {
            throw new RuntimeException("Listing not found");
        }
        listingRepository.deleteById(id);
    }
}