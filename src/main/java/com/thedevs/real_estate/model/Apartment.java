package com.thedevs.real_estate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "apartments")
public class Apartment extends Property{
    @Column(name = "floor_num")
    private Integer floorNumber;

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer numberOfFloors) {
        this.floorNumber = numberOfFloors;
    }
}
