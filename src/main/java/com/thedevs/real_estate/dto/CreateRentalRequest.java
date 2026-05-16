package com.thedevs.real_estate.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateRentalRequest {
    private Long propertyId;
    private BigDecimal monthlyRent;
    private LocalDate availableFrom;
    private String description;

    public CreateRentalRequest() {}

    public Long getPropertyId() { return propertyId; }
    public void setPropertyId(Long propertyId) { this.propertyId = propertyId; }

    public BigDecimal getMonthlyRent() { return monthlyRent; }
    public void setMonthlyRent(BigDecimal monthlyRent) { this.monthlyRent = monthlyRent; }

    public LocalDate getAvailableFrom() { return availableFrom; }
    public void setAvailableFrom(LocalDate availableFrom) { this.availableFrom = availableFrom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}