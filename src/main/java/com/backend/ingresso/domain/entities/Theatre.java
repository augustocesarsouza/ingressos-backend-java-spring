package com.backend.ingresso.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_theatres", schema = "public")
public class Theatre {
    @Id
    @Column(name = "theatre_id")
    @JsonProperty("id")
    private UUID Id;
    @Column(name = "title")
    @JsonProperty("title")
    private String Title;
    @Column(name = "description")
    @JsonProperty("description")
    private String Description;
    @Column(name = "data")
    @JsonProperty("data")
    private Timestamp Data;
    @Column(name = "location")
    @JsonProperty("location")
    private String Location;
    @Column(name = "type_of_attraction")
    @JsonProperty("typeOfAttraction")
    private String TypeOfAttraction;
    @Column(name = "category")
    @JsonProperty("category")
    private String Category;
    @Column(name = "public_id")
    @JsonProperty("publicId")
    private String PublicId;
    @Column(name = "img_url")
    @JsonProperty("imgUrl")
    private String ImgUrl;
    @OneToMany(mappedBy = "theatre", cascade = CascadeType.REMOVE)
    private List<RegionTheatre> regionTheatresList;

    public Theatre(UUID id, String title, String description, Timestamp data,
                   String location, String typeOfAttraction, String category,
                   String publicId, String imgUrl) {
        Id = id;
        Title = title;
        Description = description;
        Data = data;
        Location = location;
        TypeOfAttraction = typeOfAttraction;
        Category = category;
        PublicId = publicId;
        ImgUrl = imgUrl;
    }

    public Theatre() {
    }

    public UUID getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public Timestamp getData() {
        return Data;
    }

    public String getLocation() {
        return Location;
    }

    public String getTypeOfAttraction() {
        return TypeOfAttraction;
    }

    public String getCategory() {
        return Category;
    }

    public String getPublicId() {
        return PublicId;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUpdate(String imgUrl, String publicId){
        ImgUrl = imgUrl;
        PublicId = publicId;
    }
}
