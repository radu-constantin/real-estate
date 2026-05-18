package com.thedevs.real_estate.service;

import com.thedevs.real_estate.model.Apartment;
import com.thedevs.real_estate.model.House;
import com.thedevs.real_estate.model.Property;
import com.thedevs.real_estate.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<Property> getAllProperties() {
        return (List<Property>) propertyRepository.findAll();
    }

    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id).orElse(null);
    }

    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    public Property updateProperty(Long id, Property updated) {
        return propertyRepository.findById(id).map(property -> {
            property.setAddress(updated.getAddress());
            property.setDateOfConstruction(updated.getDateOfConstruction());
            property.setNumberOfRooms(updated.getNumberOfRooms());
            property.setFloorArea(updated.getFloorArea());
            if (property instanceof House house && updated instanceof House updatedHouse) {
                house.setNumberOfFloors(updatedHouse.getNumberOfFloors());
                house.setPlotArea(updatedHouse.getPlotArea());
            } else if (property instanceof Apartment apartment && updated instanceof Apartment updatedApartment) {
                apartment.setFloorNumber(updatedApartment.getFloorNumber());
            }
            return propertyRepository.save(property);
        }).orElseThrow(() -> new RuntimeException("Property not found"));
    }

    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
}
