package com.thedevs.real_estate.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    @Column(name = "date_of_construction")
    private Date dateOfConstruction;
    @Column(name = "num_of_rooms")
    private Integer numberOfRooms;
    @Column(name = "floor_area")
    private Integer floorArea;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public Date getDateOfConstruction() {
        return dateOfConstruction;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public Integer getFloorArea() {
        return floorArea;
    }

    public User getUser() {
        return user;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDateOfConstruction(Date dateOfConstruction) {
        this.dateOfConstruction = dateOfConstruction;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public void setFloorArea(Integer floorArea) {
        this.floorArea = floorArea;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
