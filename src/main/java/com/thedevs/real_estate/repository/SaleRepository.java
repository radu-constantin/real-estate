package com.thedevs.real_estate.repository;

import com.thedevs.real_estate.model.Rental;
import com.thedevs.real_estate.model.Sale;
import org.springframework.data.repository.CrudRepository;

public interface SaleRepository extends CrudRepository<Sale, Long> {
}
