package com.thedevs.real_estate.repository;

import com.thedevs.real_estate.model.Rental;
import org.springframework.data.repository.CrudRepository;

public interface RentalRepository extends CrudRepository<Rental, Long> {
}
