package com.thedevs.real_estate.service;

import com.thedevs.real_estate.model.Apartment;
import com.thedevs.real_estate.repository.ApartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;

    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    public List<Apartment> getAllApartments() {
        return (List<Apartment>) apartmentRepository.findAll();
    }

    public Optional<Apartment> getApartmentById(Long id) {
        return apartmentRepository.findById(id);
    }

    public Apartment createApartment(Apartment apartment) {
        return apartmentRepository.save(apartment);
    }

    public Apartment updateApartment(Long id, Apartment updated) {
        return apartmentRepository.findById(id).map(apartment -> {
            apartment.setAddress(updated.getAddress());
            apartment.setDateOfConstruction(updated.getDateOfConstruction());
            apartment.setNumberOfRooms(updated.getNumberOfRooms());
            apartment.setFloorArea(updated.getFloorArea());
            apartment.setFloorNumber(updated.getFloorNumber());
            return apartmentRepository.save(apartment);
        }).orElseThrow(() -> new RuntimeException("Apartment not found"));
    }

    public Apartment deleteApartment(Long id) {
        apartmentRepository.deleteById(id);
        return null;
    }
}