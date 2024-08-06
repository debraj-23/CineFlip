package com.moviebookingapp.Entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Movies")
public class Moves {
    @Id
    private String movename;
    private List<Theater> theaters;
    private String posterUrl;

    // Default constructor
    public Moves() {
        super();
    }

    // Constructor with parameters
    public Moves(String movename, List<Theater> theaters, String posterUrl) {
        this.movename = movename;
        this.theaters = theaters;
        this.posterUrl = posterUrl;
    }

    // Getters and setters
    public String getMovename() {
        return movename;
    }

    public void setMovename(String movename) {
        this.movename = movename;
    }

    public List<Theater> getTheaters() {
        return theaters;
    }

    public void setTheaters(List<Theater> theaters) {
        this.theaters = theaters;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}