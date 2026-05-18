package com.thedevs.real_estate.dto;

import com.thedevs.real_estate.model.enums.ListingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateSaleRequest {
    private BigDecimal askingPrice;
    private String description;
    private ListingStatus status;

    public UpdateSaleRequest() {}

    public BigDecimal getAskingPrice() { return askingPrice; }
    public void setAskingPrice(BigDecimal askingPrice) { this.askingPrice = askingPrice; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ListingStatus getStatus() { return status; }
    public void setStatus(ListingStatus status) { this.status = status; }
}