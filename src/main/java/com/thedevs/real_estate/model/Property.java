package com.thedevs.real_estate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "properties")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "propertyType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = House.class, name = "house"),
        @JsonSubTypes.Type(value = Apartment.class, name = "apartment")
})
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "An address is required!")
    private String address;

    @NotNull(message = "A date of construction is required!")
    @Column(name = "date_of_construction")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfConstruction;

    @Column(name = "num_of_rooms")
    @NotNull(message = "Number of rooms is required!")
    @Min(value = 1, message = "Number of rooms must be at least 1!")
    private Integer numberOfRooms;

    @NotNull(message = "Floor area is required!")
    @Column(name = "floor_area")
    private Integer floorArea;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PropertyPhoto> photos = new ArrayList<>();

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

    public List<PropertyPhoto> getPhotos() {
        return photos;
    }
}
