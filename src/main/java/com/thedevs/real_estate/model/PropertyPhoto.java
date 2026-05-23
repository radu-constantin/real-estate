package com.thedevs.real_estate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "property_photos")
public class PropertyPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    @JsonIgnore
    private Property property;

    @Column(nullable = false)
    private String url;

    @Column(name = "public_id", nullable = false)
    private String publicId;

    public Long getId() { return id; }
    public Property getProperty() { return property; }
    public String getUrl() { return url; }
    public String getPublicId() { return publicId; }

    public void setProperty(Property property) { this.property = property; }
    public void setUrl(String url) { this.url = url; }
    public void setPublicId(String publicId) { this.publicId = publicId; }
}
