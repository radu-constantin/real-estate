package com.thedevs.real_estate.service;

import com.thedevs.real_estate.model.Property;
import com.thedevs.real_estate.model.PropertyPhoto;
import com.thedevs.real_estate.repository.PropertyPhotoRepository;
import com.thedevs.real_estate.repository.PropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PropertyPhotoService {

    private final PropertyPhotoRepository photoRepository;
    private final PropertyRepository propertyRepository;
    private final CloudinaryService cloudinaryService;

    public PropertyPhotoService(PropertyPhotoRepository photoRepository,
                                PropertyRepository propertyRepository,
                                CloudinaryService cloudinaryService) {
        this.photoRepository = photoRepository;
        this.propertyRepository = propertyRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public PropertyPhoto uploadPhoto(Long propertyId, MultipartFile file) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found: " + propertyId));

        CloudinaryService.UploadResult result = cloudinaryService.uploadImageWithId(file);

        PropertyPhoto photo = new PropertyPhoto();
        photo.setProperty(property);
        photo.setUrl(result.url());
        photo.setPublicId(result.publicId());
        return photoRepository.save(photo);
    }

    public void deletePhoto(Long photoId) {
        PropertyPhoto photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Photo not found: " + photoId));
        cloudinaryService.deleteImage(photo.getPublicId());
        photoRepository.delete(photo);
    }
}
