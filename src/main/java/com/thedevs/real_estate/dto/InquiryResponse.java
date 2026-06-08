package com.thedevs.real_estate.dto;

import com.thedevs.real_estate.model.enums.ListingStatus;

import java.time.LocalDateTime;

public class InquiryResponse {

    private Long id;
    private Long listingId;
    private String listingAddress;
    private String listingType;
    private ListingStatus listingStatus;
    private String senderUsername;
    private String message;
    private LocalDateTime sentAt;
    private boolean isRead;

    public InquiryResponse(Long id, Long listingId, String listingAddress, String listingType,
                           ListingStatus listingStatus, String senderUsername, String message,
                           LocalDateTime sentAt, boolean isRead) {
        this.id = id;
        this.listingId = listingId;
        this.listingAddress = listingAddress;
        this.listingType = listingType;
        this.listingStatus = listingStatus;
        this.senderUsername = senderUsername;
        this.message = message;
        this.sentAt = sentAt;
        this.isRead = isRead;
    }

    public Long getId() {
        return id;
    }

    public Long getListingId() {
        return listingId;
    }

    public String getListingAddress() {
        return listingAddress;
    }

    public String getListingType() {
        return listingType;
    }

    public ListingStatus getListingStatus() {
        return listingStatus;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public boolean isRead() {
        return isRead;
    }
}
