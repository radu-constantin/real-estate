package com.thedevs.real_estate.service;

import com.thedevs.real_estate.dto.CreateRentalRequest;
import com.thedevs.real_estate.dto.UpdateRentalRequest;
import com.thedevs.real_estate.model.Rental;
import com.thedevs.real_estate.model.enums.ListingStatus;
import com.thedevs.real_estate.repository.PropertyRepository;
import com.thedevs.real_estate.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final PropertyRepository propertyRepository;

    public RentalService(RentalRepository rentalRepository, PropertyRepository propertyRepository) {
        this.rentalRepository = rentalRepository;
        this.propertyRepository = propertyRepository;
    }

    public List<Rental> getAllRentals() {
        return (List<Rental>) rentalRepository.findAll();
    }

    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    public Rental createRental(CreateRentalRequest request) {
        return propertyRepository.findById(request.getPropertyId()).map(property -> {
            Rental rental = new Rental();
            rental.setProperty(property);
            rental.setMonthlyRent(request.getMonthlyRent());
            rental.setAvailableFrom(request.getAvailableFrom());
            rental.setDescription(request.getDescription());
            rental.setListedAt(LocalDate.now());
            rental.setStatus(ListingStatus.active);
            return rentalRepository.save(rental);
        }).orElseThrow(() -> new RuntimeException("Property not found"));
    }

    public Rental updateRental(Long id, UpdateRentalRequest request) {
        return rentalRepository.findById(id).map(rental -> {
            rental.setMonthlyRent(request.getMonthlyRent());
            rental.setAvailableFrom(request.getAvailableFrom());
            rental.setStatus(request.getStatus());
            rental.setUpdatedAt(LocalDate.now());
            return rentalRepository.save(rental);
        }).orElseThrow(() -> new RuntimeException("Rental not found"));
    }

    public void deleteRental(Long id) {
        if (!rentalRepository.existsById(id)) {
            throw new RuntimeException("Rental not found");
        }
        rentalRepository.deleteById(id);
    }
}