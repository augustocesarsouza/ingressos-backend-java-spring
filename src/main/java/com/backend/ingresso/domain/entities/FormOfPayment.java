package com.backend.ingresso.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_form_of_payments", schema = "public")
public class FormOfPayment {
    @Id
    @Column(name = "form_of_payment_id")
    @JsonProperty("id")
    private UUID Id;
    @Column(name = "form_name")
    @JsonProperty("formName")
    private String FormName;
    @Column(name = "price")
    @JsonProperty("price")
    private String Price;
    @Column(name = "movie_id")
    @JsonProperty("movieId")
    private UUID MovieId;
    @ManyToOne
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    private Movie movie;

    public FormOfPayment(UUID id, String formName, String price, UUID movieId, Movie movie) {
        Id = id;
        FormName = formName;
        Price = price;
        MovieId = movieId;
        this.movie = movie;
    }

    public FormOfPayment() {
    }

    public UUID getId() {
        return Id;
    }

    public String getFormName() {
        return FormName;
    }

    public String getPrice() {
        return Price;
    }

    public UUID getMovieId() {
        return MovieId;
    }

    public Movie getMovie() {
        return movie;
    }
}
