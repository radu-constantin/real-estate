package com.thedevs.real_estate.service;

import com.thedevs.real_estate.model.House;
import com.thedevs.real_estate.repository.HouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {

    private final HouseRepository houseRepository;

    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public List<House> getAllHouses() {
        return (List<House>) houseRepository.findAll();
    }

    public House getHouseById(Long id) {
        return houseRepository.findById(id).orElse(null);
    }

    public House createHouse(House house) {
        return houseRepository.save(house);
    }

    public House updateHouse(Long id, House updated) {
        return houseRepository.findById(id).map(house -> {
            house.setAddress(updated.getAddress());
            house.setDateOfConstruction(updated.getDateOfConstruction());
            house.setNumberOfRooms(updated.getNumberOfRooms());
            house.setFloorArea(updated.getFloorArea());
            house.setNumberOfFloors(updated.getNumberOfFloors());
            house.setPlotArea(updated.getPlotArea());
            return houseRepository.save(house);
        }).orElseThrow(() -> new RuntimeException("House not found"));
    }

    public void deleteHouse(Long id) {
        houseRepository.deleteById(id);
    }
}