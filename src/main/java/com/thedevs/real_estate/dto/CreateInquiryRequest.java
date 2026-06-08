package com.thedevs.real_estate.dto;

public class CreateInquiryRequest {

    private Long listingId;
    private String message;

    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
