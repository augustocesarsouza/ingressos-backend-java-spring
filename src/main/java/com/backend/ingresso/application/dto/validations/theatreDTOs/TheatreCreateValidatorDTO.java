package com.backend.ingresso.application.dto.validations.theatreDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

import java.sql.Timestamp;
import java.util.UUID;

public class TheatreCreateValidatorDTO {
    @JsonProperty("id")
    private UUID Id;
    @NotEmpty(message = "title should not be empty")
    @JsonProperty("title")
    private String Title;
    @NotEmpty(message = "description should not be empty")
    @JsonProperty("description")
    private String Description;
    @JsonProperty("data")
    private Timestamp Data;
    @NotEmpty(message = "location should not be empty")
    @JsonProperty("location")
    private String Location;
    @NotEmpty(message = "typeOfAttraction should not be empty")
    @JsonProperty("typeOfAttraction")
    private String TypeOfAttraction;
    @NotEmpty(message = "category should not be empty")
    @JsonProperty("category")
    private String Category;
    @JsonProperty("publicId")
    private String PublicId;
    @JsonProperty("imgUrl")
    private String ImgUrl;
    @NotEmpty(message = "dataString should not be empty")
    @JsonProperty("dataString")
    private String DataString;
    @NotEmpty(message = "base64Img should not be empty")
    @JsonProperty("base64Img")
    private String Base64Img;

    public TheatreCreateValidatorDTO(UUID id, String title, String description, Timestamp data,
                                     String location, String typeOfAttraction, String category,
                                     String publicId, String imgUrl, String dataString, String base64Img) {
        Id = id;
        Title = title;
        Description = description;
        Data = data;
        Location = location;
        TypeOfAttraction = typeOfAttraction;
        Category = category;
        PublicId = publicId;
        ImgUrl = imgUrl;
        DataString = dataString;
        Base64Img = base64Img;
    }

    public TheatreCreateValidatorDTO() {
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

    public String getDataString() {
        return DataString;
    }

    public String getBase64Img() {
        return Base64Img;
    }

    public void setIdImgUrlPublicId(UUID id, String imgUrl, String publicId){
        Id = id;
        ImgUrl = imgUrl;
        PublicId = publicId;
    }

    public void setData(Timestamp data) {
        Data = data;
    }
}
