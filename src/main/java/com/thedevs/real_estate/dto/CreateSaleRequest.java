package com.thedevs.real_estate.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateSaleRequest {
    private Long propertyId;
    private BigDecimal askingPrice;

    public CreateSaleRequest() {}

    public Long getPropertyId() { return propertyId; }
    public void setPropertyId(Long propertyId) { this.propertyId = propertyId; }

    public BigDecimal getAskingPrice() { return askingPrice; }
    public void setAskingPrice(BigDecimal askingPrice) { this.askingPrice = askingPrice; }
}