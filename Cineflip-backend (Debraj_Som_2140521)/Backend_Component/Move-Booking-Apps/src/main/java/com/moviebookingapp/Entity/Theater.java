package com.moviebookingapp.Entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Theater")
public class Theater {
    @Id
    private String theaterName;
    private int noOfTickets;
    private String status;
    private List<String> languages;
    private List<ShowTiming> showTimings;

    public Theater() {
        super();
    }

    public Theater(String theaterName, int noOfTickets, String status, List<String> languages, List<ShowTiming> showTimings) {
        this.theaterName = theaterName;
        this.noOfTickets = noOfTickets;
        this.status = status;
        this.languages = languages;
        this.showTimings = showTimings;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public void setNoOfTickets(int noOfTickets) {
        this.noOfTickets = noOfTickets;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<ShowTiming> getShowTimings() {
        return showTimings;
    }

    public void setShowTimings(List<ShowTiming> showTimings) {
        this.showTimings = showTimings;
    }
}
