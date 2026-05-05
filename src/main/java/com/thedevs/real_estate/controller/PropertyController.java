package com.thedevs.real_estate.controller;

import com.thedevs.real_estate.model.Property;
import com.thedevs.real_estate.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {
    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping()
    List<Property> getAll() {
        return propertyService.getAllProperties();
    }

    @GetMapping("/{id}")
    Property getById(@PathVariable Long id) {
        return propertyService.getPropertyById(id);
    }

    @PostMapping
    public ResponseEntity<Property> create(@RequestBody Property property) {
        System.out.println(property);
        Property createdProperty = propertyService.createProperty(property);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProperty);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> update(@PathVariable Long id, @RequestBody Property property) {
        Property updatedProperty = propertyService.updateProperty(id, property);
        return ResponseEntity.ok(updatedProperty);
    }
}
