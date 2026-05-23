package com.thedevs.real_estate.controller;

import com.thedevs.real_estate.model.PropertyPhoto;
import com.thedevs.real_estate.service.PropertyPhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/properties/{propertyId}/photos")
public class PropertyPhotoController {

    private final PropertyPhotoService photoService;

    public PropertyPhotoController(PropertyPhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PropertyPhoto> upload(
            @PathVariable Long propertyId,
            @RequestParam("file") MultipartFile file) {
        PropertyPhoto photo = photoService.uploadPhoto(propertyId, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(photo);
    }

    @DeleteMapping("/{photoId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long propertyId,
            @PathVariable Long photoId) {
        photoService.deletePhoto(photoId);
        return ResponseEntity.noContent().build();
    }
}
