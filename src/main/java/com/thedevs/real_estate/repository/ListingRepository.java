package com.thedevs.real_estate.repository;

import com.thedevs.real_estate.model.Listing;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ListingRepository extends CrudRepository<Listing, Long>, JpaSpecificationExecutor<Listing> {

}
