package com.thedevs.real_estate.model;

import jakarta.persistence.*;

@Entity
@Table(name = "houses")
public class House extends Property{
    @Column(name = "num_of_floors")
    private Integer numberOfFloors;
    @Column(name = "plot_area")
    private Integer plotArea;

    public Integer getNumberOfFloors() {
        return numberOfFloors;
    }

    public Integer getPlotArea() {
        return plotArea;
    }

    public void setNumberOfFloors(Integer numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public void setPlotArea(Integer plotArea) {
        this.plotArea = plotArea;
    }
}
