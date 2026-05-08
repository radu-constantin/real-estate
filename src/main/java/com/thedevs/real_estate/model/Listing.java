package com.thedevs.real_estate.model;

import com.thedevs.real_estate.model.enums.ListingStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "listings")
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(name = "listed_at")
    private LocalDate listedAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ListingStatus status;

    public Listing() {
    }

    public Integer getId() {
        return id;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public LocalDate getListedAt() {
        return listedAt;
    }

    public void setListedAt(LocalDate listedAt) {
        this.listedAt = listedAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ListingStatus getStatus() {
        return status;
    }

    public void setStatus(ListingStatus status) {
        this.status = status;
    }
}