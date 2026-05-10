package com.thedevs.real_estate.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "rentals")
public class Rental extends Listing {
    @Column(name = "monthly_rent", precision = 15, scale = 2)
    private BigDecimal monthlyRent;

    @Column(name = "available_from")
    private LocalDate availableFrom;

    public Rental() {
    }

    public BigDecimal getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(BigDecimal monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public LocalDate getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalDate availableFrom) {
        this.availableFrom = availableFrom;
    }
}