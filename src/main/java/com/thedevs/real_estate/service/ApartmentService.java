package com.thedevs.real_estate.service;

import com.thedevs.real_estate.model.Apartment;
import com.thedevs.real_estate.model.House;
import com.thedevs.real_estate.repository.ApartmentRepository;
import com.thedevs.real_estate.repository.HouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;

    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    public List<Apartment> getAllApartments() {
        return (List<Apartment>) apartmentRepository.findAll();
    }

    public Apartment getApartmentById(Long id) {
        return apartmentRepository.findById(id).orElse(null);
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

    public void deleteHouse(Long id) {
        apartmentRepository.deleteById(id);
    }
}