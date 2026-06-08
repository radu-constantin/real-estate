package com.thedevs.real_estate.controller;

import com.thedevs.real_estate.dto.CreateInquiryRequest;
import com.thedevs.real_estate.dto.InquiryResponse;
import com.thedevs.real_estate.service.InquiryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryController {

    private final InquiryService inquiryService;

    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @PostMapping
    public ResponseEntity<InquiryResponse> create(@RequestBody CreateInquiryRequest request,
                                                   Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inquiryService.createInquiry(request, authentication.getName()));
    }

    @GetMapping("/received")
    public ResponseEntity<List<InquiryResponse>> getReceived(Authentication authentication) {
        return ResponseEntity.ok(inquiryService.getReceivedInquiries(authentication.getName()));
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Map<String, Long>> getUnreadCount(Authentication authentication) {
        long count = inquiryService.getUnreadCount(authentication.getName());
        return ResponseEntity.ok(Map.of("count", count));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<InquiryResponse> markAsRead(@PathVariable Long id,
                                                       Authentication authentication) {
        return ResponseEntity.ok(inquiryService.markAsRead(id, authentication.getName()));
    }
}
