package com.thedevs.real_estate.repository;

import com.thedevs.real_estate.model.Apartment;
import com.thedevs.real_estate.model.House;
import org.springframework.data.repository.CrudRepository;

public interface ApartmentRepository extends CrudRepository<Apartment, Long> {
}
