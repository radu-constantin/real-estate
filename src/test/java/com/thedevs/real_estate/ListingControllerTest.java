package com.thedevs.real_estate;

import com.thedevs.real_estate.model.Apartment;
import com.thedevs.real_estate.model.Listing;
import com.thedevs.real_estate.model.Property;
import com.thedevs.real_estate.model.enums.ListingStatus;
import com.thedevs.real_estate.repository.ListingRepository;
import com.thedevs.real_estate.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ListingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    private Listing savedListing;

    @BeforeEach
    void setUp() {
        listingRepository.deleteAll();
        propertyRepository.deleteAll();

        Apartment property = new Apartment();
        property.setAddress("123 Main St");
        property.setDateOfConstruction(new Date(2024, Calendar.MARCH, 15));
        property.setFloorArea(100);
        property.setFloorNumber(3);
        property.setNumberOfRooms(2);
        Property savedProperty = propertyRepository.save(property);

        Listing listing = new Listing();
        listing.setProperty(savedProperty);
        listing.setListedAt(LocalDate.now());
        listing.setStatus(ListingStatus.active);

        savedListing = listingRepository.save(listing);
    }

    @Test
    void shouldReturnAllListings() throws Exception {
        mockMvc.perform(get("/api/listings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].status").value("active"));
    }

    @Test
    void shouldReturnListingById() throws Exception {
        mockMvc.perform(get("/api/listings/" + savedListing.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedListing.getId()))
                .andExpect(jsonPath("$.status").value("active"));
    }

    @Test
    void shouldReturnNotFoundForInvalidId() throws Exception {
        mockMvc.perform(get("/api/listings/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateListing() throws Exception {
        Listing newListing = new Listing();
        newListing.setProperty(savedListing.getProperty());

        mockMvc.perform(post("/api/listings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newListing)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("active"))  // set automatically
                .andExpect(jsonPath("$.listedAt").value(LocalDate.now().toString())); // set automatically
    }

    @Test
    void shouldUpdateListing() throws Exception {
        Listing updated = new Listing();
        updated.setProperty(savedListing.getProperty());
        updated.setStatus(ListingStatus.inactive);

        mockMvc.perform(put("/api/listings/" + savedListing.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("inactive"))
                .andExpect(jsonPath("$.updatedAt").value(LocalDate.now().toString())); // set automatically
    }

    @Test
    void shouldDeleteListing() throws Exception {
        mockMvc.perform(delete("/api/listings/" + savedListing.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingInvalidId() throws Exception {
        mockMvc.perform(delete("/api/listings/999"))
                .andExpect(status().isNotFound());
    }
}