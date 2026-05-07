package com.thedevs.real_estate.repository;

import com.thedevs.real_estate.model.House;
import org.springframework.data.repository.CrudRepository;

public interface HouseRepository extends CrudRepository<House, Long> {
}
