package com.thedevs.real_estate.dto;

import com.thedevs.real_estate.model.enums.ListingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateRentalRequest {
    private BigDecimal monthlyRent;
    private LocalDate availableFrom;
    private String description;
    private ListingStatus status;

    public UpdateRentalRequest() {}

    public BigDecimal getMonthlyRent() { return monthlyRent; }
    public void setMonthlyRent(BigDecimal monthlyRent) { this.monthlyRent = monthlyRent; }

    public LocalDate getAvailableFrom() { return availableFrom; }
    public void setAvailableFrom(LocalDate availableFrom) { this.availableFrom = availableFrom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ListingStatus getStatus() { return status; }
    public void setStatus(ListingStatus status) { this.status = status; }
}