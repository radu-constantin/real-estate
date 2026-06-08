package com.thedevs.real_estate.service;

import com.thedevs.real_estate.dto.CreateInquiryRequest;
import com.thedevs.real_estate.dto.InquiryResponse;
import com.thedevs.real_estate.model.Inquiry;
import com.thedevs.real_estate.model.Listing;
import com.thedevs.real_estate.model.Sale;
import com.thedevs.real_estate.model.User;
import com.thedevs.real_estate.repository.InquiryRepository;
import com.thedevs.real_estate.repository.ListingRepository;
import com.thedevs.real_estate.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public InquiryService(InquiryRepository inquiryRepository,
                          ListingRepository listingRepository,
                          UserRepository userRepository) {
        this.inquiryRepository = inquiryRepository;
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }

    public InquiryResponse createInquiry(CreateInquiryRequest request, String senderUsername) {
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Listing listing = listingRepository.findById(request.getListingId())
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        Inquiry inquiry = new Inquiry();
        inquiry.setSender(sender);
        inquiry.setListing(listing);
        inquiry.setMessage(request.getMessage());
        inquiry.setSentAt(LocalDateTime.now());
        inquiry.setRead(false);

        return toResponse(inquiryRepository.save(inquiry));
    }

    public List<InquiryResponse> getReceivedInquiries(String ownerUsername) {
        return inquiryRepository.findByListing_Property_User_Username(ownerUsername)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public long getUnreadCount(String ownerUsername) {
        return inquiryRepository.countByListing_Property_User_UsernameAndIsReadFalse(ownerUsername);
    }

    public InquiryResponse markAsRead(Long inquiryId, String ownerUsername) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));

        String actualOwner = inquiry.getListing().getProperty().getUser().getUsername();
        if (!actualOwner.equals(ownerUsername)) {
            throw new RuntimeException("Forbidden");
        }

        inquiry.setRead(true);
        return toResponse(inquiryRepository.save(inquiry));
    }

    private InquiryResponse toResponse(Inquiry inquiry) {
        String type = inquiry.getListing() instanceof Sale ? "sale" : "rental";
        return new InquiryResponse(
                inquiry.getId(),
                inquiry.getListing().getId().longValue(),
                inquiry.getListing().getProperty().getAddress(),
                type,
                inquiry.getListing().getStatus(),
                inquiry.getSender().getUsername(),
                inquiry.getMessage(),
                inquiry.getSentAt(),
                inquiry.isRead()
        );
    }
}
