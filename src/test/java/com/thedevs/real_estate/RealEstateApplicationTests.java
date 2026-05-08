package com.thedevs.real_estate;

import com.thedevs.real_estate.model.Apartment;
import com.thedevs.real_estate.model.House;
import com.thedevs.real_estate.repository.ApartmentRepository;
import com.thedevs.real_estate.repository.HouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApartmentRepository apartmentRepository;

    private Apartment savedApartment;

    @BeforeEach
    void setUp() {
        apartmentRepository.deleteAll();

        Apartment apartment = new Apartment();
        apartment.setAddress("123 Main St");
        apartment.setFloorNumber(3);
        apartment.setNumberOfRooms(2);

        savedApartment = apartmentRepository.save(apartment);
    }

    @Test
    void shouldReturnAllApartments() throws Exception {
        mockMvc.perform(get("/api/apartments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldCreateApartment() throws Exception {
        Apartment apartment = new Apartment();
        apartment.setAddress("123 Main St");
        apartment.setFloorNumber(3);
        apartment.setNumberOfRooms(2);

        mockMvc.perform(post("/api/apartments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(apartment)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.floorNumber").value(3));
    }

    @Test
    void shouldReturnNotFoundForInvalidId() throws Exception {
        mockMvc.perform(get("/api/apartments/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateApartment() throws Exception {
        Apartment updated = new Apartment();
        updated.setAddress("456 New St");
        updated.setFloorNumber(5);
        updated.setNumberOfRooms(5);

        mockMvc.perform(put("/api/apartments/" + savedApartment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("456 New St"));
    }

    @Test
    void shouldDeleteApartment() throws Exception {
        mockMvc.perform(delete("/api/apartments/1"))
                .andExpect(status().isNoContent());
    }
}

@SpringBootTest
@AutoConfigureMockMvc
class HouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HouseRepository houseRepository;

    private House savedHouse;

    @BeforeEach
    void setUp() {
        houseRepository.deleteAll();

        House house = new House();
        house.setAddress("789 Maple Ave");
        house.setNumberOfFloors(2);
        house.setNumberOfRooms(4);

        savedHouse = houseRepository.save(house);
    }

    @Test
    void shouldReturnAllHouses() throws Exception {
        mockMvc.perform(get("/api/houses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldCreateHouse() throws Exception {
        House house = new House();
        house.setAddress("321 Oak St");
        house.setNumberOfFloors(3);
        house.setNumberOfRooms(3);

        mockMvc.perform(post("/api/houses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(house)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value("321 Oak St"))
                .andExpect(jsonPath("$.numberOfFloors").value(3));
    }

    @Test
    void shouldReturnNotFoundForInvalidHouseId() throws Exception {
        mockMvc.perform(get("/api/houses/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateHouse() throws Exception {
        House updated = new House();
        updated.setAddress("555 Revised Lane");
        updated.setNumberOfFloors(5);
        updated.setNumberOfRooms(6);

        mockMvc.perform(put("/api/houses/" + savedHouse.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("555 Revised Lane"));
    }

    @Test
    void shouldDeleteHouse() throws Exception {
        mockMvc.perform(delete("/api/houses/" + savedHouse.getId()))
                .andExpect(status().isNoContent());
    }
}
