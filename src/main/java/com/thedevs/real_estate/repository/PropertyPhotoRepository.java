package com.thedevs.real_estate.repository;

import com.thedevs.real_estate.model.PropertyPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyPhotoRepository extends JpaRepository<PropertyPhoto, Long> {
}
