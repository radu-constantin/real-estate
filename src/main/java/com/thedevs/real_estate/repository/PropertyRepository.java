package com.thedevs.real_estate.repository;

import com.thedevs.real_estate.model.Property;
import org.springframework.data.repository.CrudRepository;

public interface PropertyRepository extends CrudRepository<Property, Long> {
}
