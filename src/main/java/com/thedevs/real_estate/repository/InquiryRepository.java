package com.thedevs.real_estate.repository;

import com.thedevs.real_estate.model.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    List<Inquiry> findByListing_Property_User_Username(String username);

    long countByListing_Property_User_UsernameAndIsReadFalse(String username);
}
